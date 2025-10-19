package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.mapper.PerformanceMapper
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformanceRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Service

@Service
class PlayerPerformanceService(
    private val playerPerformanceRepository: PlayerPerformanceRepository,
    private val performanceMapper: PerformanceMapper,
) {
    fun savefromValorantAPI(playerDTO: PlayerDTO): PlayerPerformance {
        val performance = performanceMapper.fromPlayerDTO(playerDTO)
        return save(performance)
    }

    fun save(performance: PlayerPerformance): PlayerPerformance {
        return playerPerformanceRepository.save(performance)
    }
}