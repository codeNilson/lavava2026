package io.github.codenilson.lavava2026.application.services

//import io.github.codenilson.lavava2026.domain.players.Player
//import io.github.codenilson.lavava2026.domain.performances.PlayerPerformance
//import io.github.codenilson.lavava2026.domain.performances.PerformanceRepository
//import io.github.codenilson.lavava2026.domain.teams.Team
//import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
//import org.springframework.stereotype.Service
//
//@Service
//class PlayerPerformanceService(
//    private val performanceRepository: PerformanceRepository,
//    private val performanceMapper: PerformanceMapper,
//) {
//
//    fun save(performance: PlayerPerformance): PlayerPerformance {
//        return performanceRepository.save(performance)
//    }
//
//    fun saveAll(performances: List<PlayerPerformance>): List<PlayerPerformance> {
//        return performanceRepository.saveAll(performances)
//    }
//
//    fun createPerformanceFromDTO(playerDTO: PlayerDTO, player: Player, team: Team): PlayerPerformance {
//        return performanceMapper.fromPlayerDTO(playerDTO, player, team)
//    }
//}