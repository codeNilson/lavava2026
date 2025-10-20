package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
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

        if (matchService.matchAlreadyExists(matchId)) {
            throw ResourceAlreadyExists("Match with id $matchId already exists")
        }

        val valorantMatch = riotApiService.fetchMatch(matchId).block()
            ?: throw IllegalStateException("Could not fetch match with id: $matchId")

        val playerPuuids = valorantMatch.players.map { it.puuid }
        val playersMap = playerService.findAllByPuuidIn(playerPuuids).associateBy { it.puuid }

        val teamRiotIds = valorantMatch.teams.map { it.teamId }
        val teamsMap = teamService.findAllByTeamRiotIdIn(teamRiotIds).associateBy { it.teamRiotId }

        val savedMatch = matchService.saveFromValorantMatch(valorantMatch)

        valorantMatch.teams.forEach(teamService::saveValorantTeam)

        val performances = valorantMatch.players.map { playerDTO ->
            val player = playersMap[playerDTO.puuid] ?: throw ResourceNotFoundException("Player not found for puuid: ${playerDTO.puuid}")
            val team = teamsMap[playerDTO.teamId] ?: throw ResourceNotFoundException("Team not found for riotId: ${playerDTO.teamId}")

            playerPerformanceService.savefromValorantAPI(playerDTO, player, team).apply {
                this.match = savedMatch
            }
        }
        playerPerformanceService.saveAll(performances)

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