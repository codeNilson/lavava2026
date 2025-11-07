package io.github.codenilson.lavava2026.domain.valorant.dto.matches

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValorantMatchDTO(
    @JsonProperty("metadata") val matchInfo: MatchInfoDTO,
    val players: List<PlayerDTO>,
    val teams: List<TeamDTO>,
    @JsonProperty("rounds") val roundResults: List<RoundResultDTO>
) // TODO: kills on henrik

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfoDTO(
    val matchId: String,
    val map: Map,
    val gameVersion: String,
    val region: String,
    @JsonProperty("game_length_in_ms") val gameLengthMillis: Long,
    val provisioningFlowId: String,
    @JsonProperty("started_at") val gameStartMillis: Long, // TODO: Date-time
    // queue
    val isCompleted: Boolean,
    val customGameName: String,
    val gameMode: String,
    val isRanked: Boolean,
    val seasonId: String
)

data class Map(
    val id: UUID,
    val name: String,
)