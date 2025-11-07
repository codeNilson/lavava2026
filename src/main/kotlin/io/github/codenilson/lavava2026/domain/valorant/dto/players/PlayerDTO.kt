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
    @param:JsonProperty("tier") val competitiveTier: Tier,
    val cardId: UUID,
    val titleId: UUID,
    val accountLevel: Int,
    // val isObserver: Boolean,
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
    val player: PlayerRoundInfoDTO,
    val economy: EconomyDto,
    val stats: PlayerStatsDto,
    // val kills: List<KillDto>,
    // val score: Int,
)

data class PlayerRoundInfoDTO(
    val puuid: UUID,
    val name: String,
    val tag: String,
    val team: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KillDTO(
    val round: Int,
    val killer: PlayerRoundInfoDTO,
    val victim: PlayerRoundInfoDTO,
    val team: String, // ex: red
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