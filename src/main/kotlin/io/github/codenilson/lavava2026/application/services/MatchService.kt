package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.MatchInfoDTO
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class MatchService(private val matchRepository: MatchRepository) {

    fun save(match: Match): Match {
        return matchRepository.save(match)
    }

    fun matchAlreadyExists(matchId: UUID): Boolean {
        return matchRepository.existsByMatchRiotId(matchId)
    }

    fun createMatchFromDTO(valorantMatch: MatchInfoDTO): Match {

        return Match(
                matchRiotId = valorantMatch.matchId,
                gameLength = valorantMatch.gameLengthMillis,
                map = valorantMatch.map.name,
                startedAt = valorantMatch.startedAt,
                isCompleted = valorantMatch.isCompleted,
                season = "2026", // TODO: Considerar pegar isso dinamicamente
        )
    }
}
