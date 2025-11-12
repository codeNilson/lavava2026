// Arquivo: .../services/MatchService.kt
package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.MatchInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MatchService(
    private val matchRepository: MatchRepository
) {

    fun matchAlreadyExists(matchId: UUID): Boolean {
        return matchRepository.existsByMatchRiotId(matchId)
    }

    fun saveValorantMatch(valorantMatch: MatchInfoDTO): Match {

        return matchRepository.save(
            Match(
                matchRiotId = valorantMatch.matchId,
                gameLength = valorantMatch.gameLengthMillis,
                map = valorantMatch.map.name,
                startedAt = valorantMatch.startedAt,
                isCompleted = valorantMatch.isCompleted,
                season = "2026", // TODO: Considerar pegar isso dinamicamente
            )
        )
    }
}