package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.*
import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import org.springframework.stereotype.Service
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
//
//@Service
//class SyncMatchUseCase(
//    private val valorantIntegrationService: ValorantIntegrationService,
//    private val playerService: PlayerService,
//    private val matchService: MatchService,
//    private val teamService: TeamService,
//    private val playerPerformanceService: PlayerPerformanceService,
//    private val roundService: RoundService,
//    private val roundKillService: RoundKillService
//) {
//    @Transactional
//    fun syncMatch(matchId: String) {
//
//        if (matchService.matchAlreadyExists(matchId)) {
//            throw ResourceAlreadyExists("Match with id $matchId already exists")
//        }
//
//        val valorantMatch = valorantIntegrationService.fetchMatch(matchId).block()
//            ?: throw IllegalStateException("Could not fetch match with id: $matchId")
//
//        val playerPuuids = valorantMatch.players.map { it.puuid }
//        val playersMap = playerService.findAllByPuuidIn(playerPuuids).associateBy { it.puuid }
//
//        val teamRiotIds = valorantMatch.teams.map { it.teamId }
//        val teamsMap = teamService.findAllByTeamRiotIdIn(teamRiotIds).associateBy { it.teamRiotId }
//
//        val savedMatch = matchService.saveFromValorantMatch(valorantMatch)
//
//        valorantMatch.teams.forEach(teamService::saveValorantTeam)
//
//        val performances = valorantMatch.players.map { playerDTO ->
//            val player = playersMap[playerDTO.puuid]
//                ?: throw ResourceNotFoundException("Player not found for puuid: ${playerDTO.puuid}")
//            val team = teamsMap[playerDTO.teamId]
//                ?: throw ResourceNotFoundException("Team not found for riotId: ${playerDTO.teamId}")
//
//            playerPerformanceService.createPerformanceFromDTO(playerDTO, player, team).apply {
//                this.match = savedMatch
//            }
//        }
//
//        playerPerformanceService.saveAll(performances)
//
//        val allKills = mutableListOf<RoundKill>()
//        val rounds = valorantMatch.roundResults.map { roundResultDTO ->
//            val round = roundService.createRoundFromDTO(roundResultDTO, playersMap).apply {
//                this.match = savedMatch
//            }
//
//            val killsInRound = roundResultDTO.playerStats.flatMap { playerStat ->
//                playerStat.kills.map { killDTO ->
//                    val killer = playersMap[killDTO.killer]
//                        ?: throw ResourceNotFoundException("Killer not found for puuid: ${killDTO.killer}")
//                    val victim = playersMap[killDTO.victim]
//                        ?: throw ResourceNotFoundException("Victim not found for puuid: ${killDTO.victim}")
//                    RoundKill(
//                        round = round,
//                        killer = killer,
//                        victim = victim
//                    )
//                }
//            }
//            allKills.addAll(killsInRound)
//            round
//        }
//
//        // 5. Salvar todos os rounds e todas as mortes em lote
//        roundService.saveAll(rounds)
//        roundKillService.saveAll(allKills)
//
//
//    }
//}

@Service
class SyncMatchUseCase(
    private val playerService: PlayerService,
    private val matchService: MatchService,
    private val teamService: TeamService,
    private val roundService: RoundService,
    private val performanceService: PerformanceService,
    private val valorantIntegrationService: ValorantIntegrationService
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

        val players = playerService.createOrUpdatePlayers(valorantMatch.players)
        val performances = performanceService.getPerformance(valorantMatch.players)
        val match = matchService.saveValorantMatch(valorantMatch.matchInfo)
        val teams = teamService.saveValorantTeam(valorantMatch.teams) // anexar match
        val rounds = roundService.createRounds(valorantMatch.roundResults) // anexar match e team


        return valorantMatch
    }
}