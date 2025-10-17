package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.valorant.dto.MatchInfoDto
import org.springframework.stereotype.Component

@Component
class MatchMapper {

    fun fromValorantMatch(valorantMatchInfo: MatchInfoDto) : Match {
        return Match(
            matchRiotId = valorantMatchInfo.matchId,
            gameLength = valorantMatchInfo.gameLengthMillis,
            map = valorantMatchInfo.mapId,
            gameStartMillis = valorantMatchInfo.gameStartMillis,
            isCompleted = valorantMatchInfo.isCompleted,
            season = "2026", // todo: verify how to get the season
        )
    }
}