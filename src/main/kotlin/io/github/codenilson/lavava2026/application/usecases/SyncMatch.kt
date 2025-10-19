package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.MatchService
import io.github.codenilson.lavava2026.application.services.PlayerService
import io.github.codenilson.lavava2026.application.services.RiotApiService
import io.github.codenilson.lavava2026.application.services.PlayerPerformanceService
import io.github.codenilson.lavava2026.application.services.RoundService
import io.github.codenilson.lavava2026.application.services.TeamService
import io.github.codenilson.lavava2026.domain.rounds.RoundKill
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SyncMatch(
    private val riotApiService: RiotApiService,
    private val playerService: PlayerService,
    private val matchService: MatchService,
    private val teamService: TeamService,
    private val playerPerformanceService: PlayerPerformanceService,
    private val roundService: RoundService
) {
    @Transactional
    fun syncMatch(matchId: String) {
        val valorantMatch = riotApiService.fetchMatch(matchId).block()
            ?: throw IllegalStateException("Could not fetch match with id: $matchId")

        val savedMatch = matchService.saveFromValorantMatch(valorantMatch)
        valorantMatch.teams.forEach(teamService::saveValorantTeam)

        val playersPerformances = valorantMatch.players.map { player ->
            playerPerformanceService.savefromValorantAPI(player)
                .apply {
                    this.match = savedMatch
                }
        }

        val rounds = valorantMatch.roundResults.map { roundResultDTO ->
            roundService.saveFromRoundStatusDTO(roundResultDTO).apply {
                this.match = savedMatch
                roundResultDTO.playerStats.forEach {
                        playerStat -> playerStat.kills.forEach {
                        kill -> RoundKill(
                    round = this,
                    killer = playerService.findByPuuid(kill.killer),
                    victim = playerService.findByPuuid(kill.victim),
                )
                }}
            }
        }


    }
}