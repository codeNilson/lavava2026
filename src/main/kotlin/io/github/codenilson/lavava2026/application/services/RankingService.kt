package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.ranking.RankingRepository
import io.github.codenilson.lavava2026.domain.ranking.dto.PlayerRankingDTO
import org.springframework.stereotype.Service

@Service
class RankingService(
    private val rankingRepository: RankingRepository
) {

    fun getPlayerRanking(): List<PlayerRankingDTO> {
        return rankingRepository.getRankedPlayers()
    }
}