package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.*
import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
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

        val playersTeam = valorantMatch.players.groupBy { it.teamId }

        val match = matchService.createMatchFromDTO(valorantMatch.matchInfo)
        val performances = performanceService.createPerformancesFromDTO(valorantMatch.players).onEach { it.match = match }
        val teams = teamService.createTeamsFromDTO(valorantMatch.teams).onEach { it.match = match }
        val rounds = roundService.createRoundsFromDTO(valorantMatch.roundResults).onEach {
            it.match = match
            val team = teams.first { team -> team.color == it.winningTeamColor }
            it.winningTeam = team
        }
        val kills = roundKillService.createKillsFromDTO(valorantMatch.kills).onEach {
            val round = rounds.first { round -> round.roundNumber == it.roundNum }
            it.round = round
        }
        val players = playerService.createPlayersFromDTO(valorantMatch.players).onEach {
            for ((teamId, playersInfo) in playersTeam) {
                val puuids = playersInfo.map { it.puuid }

                if (it.puuid in puuids) {
                    val team = teams.firstOrNull { team -> team.color == teamId }
                    val performance = performances.firstOrNull { perf -> perf.player?.puuid == it.puuid }

                    if (team != null && performance != null) {
                        performance.player = it
                        performance.team = team
                        break
                    } else {
                        // Log para debug
                        println("Team ou Performance não encontrado para player: ${it.puuid}")
                    }
                }
            }
        }

        matchService.save(match)
        playerService.saveAll(players)
        teamService.saveAll(teams)
        roundService.saveAll(rounds)
        performanceService.saveAll(performances)
        roundKillService.saveAll(kills)

        return valorantMatch
    }
}