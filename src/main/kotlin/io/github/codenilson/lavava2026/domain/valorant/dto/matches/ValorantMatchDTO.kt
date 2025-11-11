package io.github.codenilson.lavava2026.domain.valorant.dto.matches

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.KillDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import java.util.UUID
import java.time.Instant

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
    val isCompleted: Boolean,
    val startedAt: Instant,
)

data class MapDTO(
    val id: UUID,
    val name: String,
)