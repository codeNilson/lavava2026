package io.github.codenilson.lavava2026.domain.valorant.dto.teams

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamDto(
    val teamId: String,
    val won: Boolean,
    val roundsPlayed: Integer,
    val roundsWon: Integer,
    val numPoints: Integer,
)
