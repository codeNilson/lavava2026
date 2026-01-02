package io.github.codenilson.lavava2026.domain.valorant.dto.players

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerInfoDTO(
    val puuid: UUID,
    val agent: Agent,
    val name: String,
    val tag: String,
    val teamId: String,
    val stats: PlayerStatsDto,
    val accountLevel: Int,
    @param:JsonProperty("tier") val competitiveTier: Tier,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerRoundStatsDto(
    val player: PlayerRoundInfoDTO,
    val economy: EconomyDto,
    val stats: PlayerStatsDto,
)

data class PlayerRoundInfoDTO(
    val puuid: UUID,
    val name: String,
    val tag: String,
    val team: String,
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
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KillDTO(
    val round: Int,
    val killer: PlayerRoundInfoDTO,
    val victim: PlayerRoundInfoDTO,
    val weapon: Weapon,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EconomyDto(
    val weapon: Weapon?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weapon(
    val name: String?,
    val type: String,
)

data class Agent(
    val id: UUID,
    val name: String,
)

data class Tier(
    val id: Int,
    val name: String,
)