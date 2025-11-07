package io.github.codenilson.lavava2026.domain.valorant.dto.matches

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValorantMatchDTO(
    val matchInfo: MatchInfoDTO,
    val players: List<PlayerDTO>,
    val teams: List<TeamDTO>,
    val roundResults: List<RoundResultDTO>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfoDTO(
    val matchId: String,
    val mapId: String,
    val gameVersion: String,
    val region: String,
    val gameLengthMillis: Int,
    val provisioningFlowId: String,
    val gameStartMillis: Long,
    val isCompleted: Boolean,
    val customGameName: String,
    val gameMode: String,
    val isRanked: Boolean,
    val seasonId: String
)
