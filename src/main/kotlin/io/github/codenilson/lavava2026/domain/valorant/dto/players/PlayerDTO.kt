package io.github.codenilson.lavava2026.domain.valorant.dto.players

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayersDto(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
    val teamId: String,
    val partyId: String,
    val characterId: String,
    val stats: PlayerStatsDto,
    val competitiveTier: Int,
    val isObserver: Boolean,
    val playerCard: String,
    val playerTitle: String,
    val accountLevel: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerStatsDto(
    val score: Integer,
    val roundsPlayed: Integer,
    val kills: Integer,
    val deaths: Integer,
    val assists: Integer,
    val playerTimeMillis: Integer,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerRoundStatsDto(
    val puuid: String,
    val kills: List<KillDto>,
    val score: Integer,
    val economy: EconomyDto,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KillDto(
    val killer: String, // puuid of the killer
    val victim: String, // puuid of the victim
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EconomyDto(
    val weapon: String,
)
