package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.mapper.PerformanceMapper
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformanceRepository
import io.github.codenilson.lavava2026.domain.teams.Team
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Service

@Service
class PlayerPerformanceService(
    private val playerPerformanceRepository: PlayerPerformanceRepository,
    private val performanceMapper: PerformanceMapper,
) {

    fun save(performance: PlayerPerformance): PlayerPerformance {
        return playerPerformanceRepository.save(performance)
    }

    fun saveAll(performances: List<PlayerPerformance>): List<PlayerPerformance> {
        return playerPerformanceRepository.saveAll(performances)
    }

    fun createPerformanceFromDTO(playerDTO: PlayerDTO, player: Player, team: Team): PlayerPerformance {
        return performanceMapper.fromPlayerDTO(playerDTO, player, team)
    }
}