package io.github.codenilson.lavava2026.domain.valorant.dto.rounds

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerRoundStatsDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class RoundResultDTO(
    @param:JsonProperty("id") val roundNum: Int,
    val result: String,
    val winningTeam: String,
    val stats: List<PlayerRoundStatsDto>,
)
