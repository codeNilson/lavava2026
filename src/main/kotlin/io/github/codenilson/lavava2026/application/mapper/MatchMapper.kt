package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import org.springframework.stereotype.Component

@Component
class MatchMapper {

    fun fromValorantMatch(valorantMatch: ValorantMatchDTO) : Match {
        return Match(
            matchRiotId = valorantMatch.matchInfo.matchId,
            gameLength = valorantMatch.matchInfo.gameLengthMillis,
            map = valorantMatch.matchInfo.mapId,
            gameStartMillis = valorantMatch.matchInfo.gameStartMillis,
            isCompleted = valorantMatch.matchInfo.isCompleted,
            season = "2026", // todo: verify how to get the season
        )
    }
}