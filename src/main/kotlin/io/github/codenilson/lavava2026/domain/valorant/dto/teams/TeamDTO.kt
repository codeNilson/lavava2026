package io.github.codenilson.lavava2026.domain.valorant.dto.teams

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeamDTO(
    val teamId: String,
    val rounds: Round,
    val won: Boolean,
)

data class Round(
    val won: Int,
    val lost: Int,
)