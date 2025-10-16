package io.github.codenilson.lavava2026.domain.valorant.dto.rounds

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerRoundStatsDto

@JsonIgnoreProperties(ignoreUnknown = true)
data class RoundResultDto(
    val roundNum: Integer,
    val roundResult: String,
    val roundCeremony: String,
    val winningTeam: String,
    val bombPlanter: String, // puuid of player who planted the spike
    val bombDefuser: String, // puuid of player who defused the spike
    val plantSite: String,
    val playerStats: List<PlayerRoundStatsDto>,
    val roundResultCode: String,
)
