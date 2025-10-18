package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Component

@Component
class PerformanceMapper {
    fun fromPlayerDTO(playersDto: PlayerDTO): PlayerPerformance {
        return PlayerPerformance(
            characterId = playersDto.characterId,
            score = playersDto.stats.score,
            roundsPlayed = playersDto.stats.roundsPlayed,
            kills = playersDto.stats.kills,
            deaths = playersDto.stats.deaths,
            assists = playersDto.stats.assists,
            player = TODO(),
            match = TODO(),
            team = TODO(),
        )
    }
}