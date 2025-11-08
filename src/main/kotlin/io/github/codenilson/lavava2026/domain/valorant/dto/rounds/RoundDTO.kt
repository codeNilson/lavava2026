package io.github.codenilson.lavava2026.domain.valorant.dto.rounds

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerRoundStatsDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class RoundResultDTO(
    @param:JsonProperty("id") val roundNum: Int,
    val result: String, //(ex: detonate)
    val winningTeam: String, //(ex: blue, red)
    val playerStats: List<PlayerRoundStatsDto>,
    // val roundCeremony: String,
    // val bombPlanter: String, //
    // val bombDefuser: String, //
)
