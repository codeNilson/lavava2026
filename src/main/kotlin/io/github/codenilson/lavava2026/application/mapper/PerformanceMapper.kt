package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.application.services.PlayerService
import io.github.codenilson.lavava2026.application.services.TeamService
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.teams.Team
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Component

@Component
class PerformanceMapper {
    fun fromPlayerDTO(playersDTO: PlayerDTO, player: Player, team: Team): PlayerPerformance {
        val performance = PlayerPerformance(
            characterId = playersDTO.characterId,
            score = playersDTO.stats.score,
            roundsPlayed = playersDTO.stats.roundsPlayed,
            kills = playersDTO.stats.kills,
            deaths = playersDTO.stats.deaths,
            assists = playersDTO.stats.assists,
            player = player,
            team = team,
        )
        return performance
    }
}