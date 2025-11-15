package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.ranking.RankingRepository
import io.github.codenilson.lavava2026.domain.ranking.dto.PlayerRankingDTO
import org.springframework.stereotype.Service
import java.time.Year // Importar

@Service
class RankingService(
    private val rankingRepository: RankingRepository
) {
    private val CURRENT_SEASON = Year.now().toString()

    /**
     * Busca a lista de ranking de jogadores.
     * Se 'season' for nulo, usa a temporada atual como padrão.
     */
    fun getPlayerRanking(season: String?): List<PlayerRankingDTO> {
        val effectiveSeason = season ?: CURRENT_SEASON

        return rankingRepository.getRankedPlayers(effectiveSeason)
    }
}