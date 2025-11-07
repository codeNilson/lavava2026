package io.github.codenilson.lavava2026.domain.valorant.dto.players

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerInfoDTO(
    val puuid: UUID,
    val name: String,
    val tag: String,
    val teamId: String,
    val partyId: UUID,
    val agent: Agent,
    val stats: PlayerStatsDto,
    @JsonProperty("tier") val competitiveTier: Tier,
    // val isObserver: Boolean,
    val cardId: UUID,
    val titleId: UUID,
    val accountLevel: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerStatsDto(
    val score: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val headshots: Int,
    val legshots: Int,
    val bodyshots: Int,
    // val roundsPlayed: Int,
    // val playerTimeMillis: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerRoundStatsDto(
    val puuid: String,
    // val kills: List<KillDto>,
    val player: PlayerInfoDTO,
    val team: String, // ex: red
    // val score: Int,
    val economy: EconomyDto,
)

data class PlayerRoundInfoDTO(
    val name: String,
    val tag: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KillDTO(
    val killer: String, // puuid of the killer
    val victim: String, // puuid of the victim
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EconomyDto(
    val weapon: Weapon,
)

data class Weapon(
    val id: UUID?,
    val name: String?,
    val type: String?,
)

data class Agent(
    val id: UUID,
    val name: String,
)

data class Tier(
    val id: Int,
    val name: String,
)