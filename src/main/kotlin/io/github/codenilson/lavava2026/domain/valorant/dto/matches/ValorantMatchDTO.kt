package io.github.codenilson.lavava2026.domain.valorant.dto.matches

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.KillDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import java.time.LocalDateTime
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValorantMatchDTO(
    @param:JsonProperty("metadata") val matchInfo: MatchInfoDTO,
    val players: List<PlayerInfoDTO>,
    val teams: List<TeamDTO>,
    @param:JsonProperty("rounds") val roundResults: List<RoundResultDTO>,
    val kills: List<KillDTO>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfoDTO(
    val matchId: UUID,
    val map: MapDTO,
    @param:JsonProperty("game_length_in_ms") val gameLengthMillis: Long,
    val startedAt: LocalDateTime,
    val isCompleted: Boolean,
    // val region: String,
    // val gameVersion: String,
    // queue
    // val seasonId: Season,
    // val provisioningFlowId: String,
    // val customGameName: String,
    // val gameMode: String,
    // val isRanked: Boolean,
)

data class MapDTO(
    val id: UUID,
    val name: String,
)