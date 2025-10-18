package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.application.services.PlayerService
import io.github.codenilson.lavava2026.application.services.TeamService
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Component

@Component
class PerformanceMapper(
    private val teamService: TeamService,
    private val playerService: PlayerService,
) {
    fun fromPlayerDTO(playersDTO: PlayerDTO): PlayerPerformance {
        val performance = PlayerPerformance(
            characterId = playersDTO.characterId,
            score = playersDTO.stats.score,
            roundsPlayed = playersDTO.stats.roundsPlayed,
            kills = playersDTO.stats.kills,
            deaths = playersDTO.stats.deaths,
            assists = playersDTO.stats.assists,
            player = playerService.findByPuuid(playersDTO.puuid),
            team = teamService.getTeamByRiotId(playersDTO.teamId),
        )
        return performance
    }
}