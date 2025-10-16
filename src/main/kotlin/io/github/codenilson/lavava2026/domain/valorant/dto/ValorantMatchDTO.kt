package io.github.codenilson.lavava2026.domain.valorant.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayersDto
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDto
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValorantMatchDTO(
    val matchInfo: MatchInfoDto,
    val players: List<PlayersDto>,
    val teams: List<TeamDto>,
    val roundResults: List<RoundResultDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfoDto(
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
