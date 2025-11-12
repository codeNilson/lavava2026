package io.github.codenilson.lavava2026.application.services

//import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.performances.Performance
import io.github.codenilson.lavava2026.domain.performances.PerformanceRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
//import io.github.codenilson.lavava2026.domain.teams.Team
//import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import org.springframework.stereotype.Service
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

@Service
class PerformanceService(
    private val performanceRepository: PerformanceRepository,
) {
    fun getPerformance(playersStats: List<PlayerInfoDTO>) : List<Performance> {
        return playersStats.map {
            Performance(
                agent = it.agent.name,
                score = it.stats.score,
                kills = it.stats.kills,
                deaths = it.stats.deaths,
                assists = it.stats.assists,
                headshots = it.stats.headshots,
                legshots = it.stats.legshots,
                bodyshots = it.stats.bodyshots,
//                player = TODO(),
//                match = TODO(),
//                team = TODO(),
            )
        }
    }
}