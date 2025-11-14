package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.*
import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.domain.players.Player
import org.springframework.stereotype.Service
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class SyncMatchUseCase(
    private val playerService: PlayerService,
    private val matchService: MatchService,
    private val teamService: TeamService,
    private val roundService: RoundService,
    private val performanceService: PerformanceService,
    private val valorantIntegrationService: ValorantIntegrationService,
    private val roundKillService: RoundKillService
) {

    /**
     * Orquestra a sincronização completa de uma partida.
     * Busca na API externa, valida e salva no banco de dados.
     */
    @Transactional
    fun execute(matchId: UUID): ValorantMatchDTO {
        if (matchService.matchAlreadyExists(matchId)) {
            throw ResourceAlreadyExists("Match with id $matchId already exists")
        }

        val valorantMatch = valorantIntegrationService.fetchMatch(matchId).block()
            ?: throw Exception("Match not found")
        val match = matchService.createMatchFromDTO(valorantMatch.matchInfo)
        matchService.save(match)

        // 2. Criar e salvar os players
        val playersToSave = mutableListOf<Player>()
        val savedPlayers = mutableListOf<Player>()

        valorantMatch.players.forEach { playerDto ->
            try {
                // Tenta encontrar o player existente primeiro
                val existingPlayer = playerService.findByPuuid(playerDto.puuid)
                savedPlayers.add(existingPlayer)
            } catch (_: Exception) {
                // Se não existir, cria um novo
                val newPlayer = Player(
                    puuid = playerDto.puuid,
                    gameName = playerDto.name,
                    tagName = playerDto.tag,
                    competitiveTier = playerDto.competitiveTier.name,
                    accountLevel = playerDto.accountLevel,
                )
                playersToSave.add(newPlayer)
            }
        }

        // Salva apenas os players novos
        if (playersToSave.isNotEmpty()) {
            savedPlayers.addAll(playerService.saveAll(playersToSave))
        }

        // 3. Criar e salvar teams
        val teams = teamService.createTeamsFromDTO(valorantMatch.teams).onEach { it.match = match }
        val savedTeams = teamService.saveAll(teams)

        // 4. Criar performances e configurar relacionamentos
        val performances = performanceService.createPerformancesFromDTO(valorantMatch.players)

        // Mapear relacionamentos usando os dados do DTO
        for (i in performances.indices) {
            val performance = performances[i]
            val playerDto = valorantMatch.players[i]

            // Configurar match
            performance.match = match

            // Encontrar e configurar player - usar savedPlayers
            val player = savedPlayers.find { it.puuid == playerDto.puuid }
            if (player == null) {
                throw Exception("Player not found for puuid: ${playerDto.puuid}")
            }
            performance.player = player

            // Encontrar e configurar team - usar savedTeams
            val team = savedTeams.find { it.color == playerDto.teamId }
            if (team == null) {
                throw Exception("Team not found for teamId: ${playerDto.teamId}")
            }
            performance.team = team
        }

        // 5. Criar e salvar rounds
        val rounds = roundService.createRoundsFromDTO(valorantMatch.roundResults).onEach {
            it.match = match
            val team = savedTeams.first { team -> team.color == it.winningTeamColor }
            it.winningTeam = team
        }
        val savedRounds = roundService.saveAll(rounds)

        // 6. Criar kills
        val kills = roundKillService.createKillsFromDTO(valorantMatch.kills).onEach {
            val round = savedRounds.first { round -> round.roundNumber == it.roundNum }
            it.round = round
        }

        // 7. Salvar performances e kills
        performanceService.saveAll(performances)
        roundKillService.saveAll(kills)

        return valorantMatch
    }
}