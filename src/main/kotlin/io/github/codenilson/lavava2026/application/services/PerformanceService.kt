package io.github.codenilson.lavava2026.application.services

//import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.performances.Performance
import io.github.codenilson.lavava2026.domain.performances.PerformanceRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
import org.springframework.stereotype.Service


@Service
class PerformanceService(
    private val performanceRepository: PerformanceRepository,
) {

    fun save(performance: Performance): Performance {
        return performanceRepository.save(performance)
    }

    fun saveAll(performances: List<Performance>): List<Performance> {
        return performanceRepository.saveAll(performances)
    }

    fun createPerformancesFromDTO(playersStats: List<PlayerInfoDTO>) : List<Performance> {
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