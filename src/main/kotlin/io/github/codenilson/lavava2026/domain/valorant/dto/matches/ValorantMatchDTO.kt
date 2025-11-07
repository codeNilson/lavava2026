package io.github.codenilson.lavava2026.domain.valorant.dto.matches

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.KillDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValorantMatchDTO(
    @JsonProperty("metadata") val matchInfo: MatchInfoDTO,
    val players: List<PlayerInfoDTO>,
    val teams: List<TeamDTO>,
    @JsonProperty("rounds") val roundResults: List<RoundResultDTO>,
    val kills: List<KillDTO>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfoDTO(
    val matchId: String,
    val map: Map,
    val gameVersion: String,
    @JsonProperty("game_length_in_ms") val gameLengthMillis: Long,
    @JsonProperty("started_at") val gameStartMillis: Long, // TODO: Date-time
    val isCompleted: Boolean,
    // queue
    val seasonId: String, // TODO: change to season object
    val region: String,
    // val provisioningFlowId: String,
    // val customGameName: String,
    // val gameMode: String,
    // val isRanked: Boolean,
)

data class Map(
    val id: UUID,
    val name: String,
)