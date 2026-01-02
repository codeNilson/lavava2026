package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.*
import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.domain.players.Player
import org.springframework.stereotype.Service
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.transaction.annotation.Transactional
import java.time.Year
import java.util.UUID

@Service
class SyncMatchUseCase(
    private val playerService: PlayerService,
    private val matchService: MatchService,
    private val teamService: TeamService,
    private val roundService: RoundService,
    private val performanceService: PerformanceService,
    private val valorantIntegrationService: ValorantIntegrationService,
    private val roundKillService: RoundKillService,
    private val playerStatsService: PlayerStatsService
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

        val playersToSave = mutableListOf<Player>()
        val savedPlayers = mutableListOf<Player>()

        valorantMatch.players.forEach { playerDto ->
            try {
                val existingPlayer = playerService.findByPuuid(playerDto.puuid)
                savedPlayers.add(existingPlayer)
            } catch (_: Exception) {
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

        if (playersToSave.isNotEmpty()) {
            savedPlayers.addAll(playerService.saveAll(playersToSave))
        }

        val teams = teamService.createTeamsFromDTO(valorantMatch.teams).onEach { it.match = match }
        val savedTeams = teamService.saveAll(teams)

        val performances = performanceService.createPerformancesFromDTO(valorantMatch.players)

        for (i in performances.indices) {
            val performance = performances[i]
            val playerDto = valorantMatch.players[i]

            performance.match = match

            val player = savedPlayers.find { it.puuid == playerDto.puuid }
            if (player == null) {
                throw Exception("Player not found for puuid: ${playerDto.puuid}")
            }
            performance.player = player

            val team = savedTeams.find { it.color == playerDto.teamId }
            if (team == null) {
                throw Exception("Team not found for teamId: ${playerDto.teamId}")
            }
            performance.team = team
        }

        val rounds = roundService.createRoundsFromDTO(valorantMatch.roundResults).onEach {
            it.match = match
            val team = savedTeams.first { team -> team.color == it.winningTeamColor }
            it.winningTeam = team
        }
        val savedRounds = roundService.saveAll(rounds)

        val kills = roundKillService.createKillsFromDTO(valorantMatch.kills).onEach {
            val round = savedRounds.first { round -> round.roundNumber == it.roundNum }
            it.round = round
        }

        performanceService.saveAll(performances)
        roundKillService.saveAll(kills)

        val currentSeason = Year.now().toString()
        savedPlayers.forEach { player ->
            playerStatsService.updatePlayerStats(player, currentSeason)
        }

        return valorantMatch
    }
}