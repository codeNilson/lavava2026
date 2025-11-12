package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.*
import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import org.springframework.stereotype.Service
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
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

        val playersTeam = valorantMatch.players.groupBy { it.teamId } // {"red": [playerInfo1, playerInfo2], "blue": [playerInfo3, playerInfo4]}

        val match = matchService.saveValorantMatch(valorantMatch.matchInfo)
        val performances = performanceService.getPerformance(valorantMatch.players).onEach { it.match = match } // TODO: anexar player, match e team
        val teams = teamService.saveValorantTeam(valorantMatch.teams).onEach { it.match = match }
        val players = playerService.createOrUpdatePlayers(valorantMatch.players).onEach {

            for ((teamId, playersInfo) in playersTeam) {
                val puuids = playersInfo.map {it.puuid}

                if (it.puuid in puuids) {
                    val team = teams.first { team -> team.color == teamId } // Pega o time correto baseado na cor
                    val performance = performances.first { perf -> perf.player?.puuid == it.puuid } // Pega o performance correto baseado no puuid do player
                    performance.player = it
                    performance.team = team
                    break
                }

            }

        }
        val rounds = roundService.createRounds(valorantMatch.roundResults).onEach { it.match = match } // TODO: anexar match e team
        val kill = roundKillService.getKills(valorantMatch.kills) // TODO: anexar round


        return valorantMatch
    }
}