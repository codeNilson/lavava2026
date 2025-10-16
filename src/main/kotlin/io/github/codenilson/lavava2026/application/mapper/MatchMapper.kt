package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import org.springframework.stereotype.Component

@Component
class MatchMapper {

    fun fromValorantMatch(valorantMatch: ValorantMatchDTO) : Match {
        val match = Match(
            matchRiotId = valorantMatch.matchInfo.matchId,
            mapId = valorantMatch.matchInfo.mapId,
            gameVersion = valorantMatch.matchInfo.gameVersion,
            region = valorantMatch.matchInfo.region,
            gameLengthMillis = valorantMatch.matchInfo.gameLengthMillis,
            provisioningFlowId = valorantMatch.matchInfo.provisioningFlowId,
            gameStartMillis = valorantMatch.matchInfo.gameStartMillis,
            isCompleted = valorantMatch.matchInfo.isCompleted,
            customGameName = valorantMatch.matchInfo.customGameName,
            gameMode = valorantMatch.matchInfo.gameMode,
            isRanked = valorantMatch.matchInfo.isRanked,
            seasonId = valorantMatch.matchInfo.seasonId
        )
    }
}