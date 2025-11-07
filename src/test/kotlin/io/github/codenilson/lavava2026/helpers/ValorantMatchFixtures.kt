package io.github.codenilson.lavava2026.helpers

import io.github.codenilson.lavava2026.domain.valorant.dto.MatchInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO

object ValorantMatchFixtures {

    fun createMatchInfo(
        matchId: String = "default-match-123",
        mapId: String = "Ascent",
        gameVersion: String = "release-05.12",
        region: String = "na",
        gameLengthMillis: Int = 1800000,
        provisioningFlowId: String = "Matchmaking",
        gameStartMillis: Long = 1634567890000L,
        isCompleted: Boolean = true,
        customGameName: String = "",
        gameMode: String = "Competitive",
        isRanked: Boolean = true,
        seasonId: String = "e5a1"
    ): MatchInfoDTO {
        return MatchInfoDTO(
            matchId = matchId,
            mapId = mapId,
            gameVersion = gameVersion,
            region = region,
            gameLengthMillis = gameLengthMillis,
            provisioningFlowId = provisioningFlowId,
            gameStartMillis = gameStartMillis,
            isCompleted = isCompleted,
            customGameName = customGameName,
            gameMode = gameMode,
            isRanked = isRanked,
            seasonId = seasonId
        )
    }

    fun createValorantMatch(
        matchInfo: MatchInfoDTO = createMatchInfo(),
        players: List<PlayerDTO> = emptyList(),
        teams: List<TeamDTO> = emptyList(),
        roundResults: List<RoundResultDTO> = emptyList()
    ): ValorantMatchDTO {
        return ValorantMatchDTO(
            matchInfo = matchInfo,
            players = players,
            teams = teams,
            roundResults = roundResults
        )
    }

    fun createValorantMatchWithId(
        matchId: String,
        mapId: String = "Ascent",
        region: String = "na"
    ): ValorantMatchDTO {
        val matchInfo = createMatchInfo(
            matchId = matchId,
            mapId = mapId,
            region = region
        )
        return createValorantMatch(matchInfo = matchInfo)
    }
}

