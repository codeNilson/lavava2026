package io.github.codenilson.lavava2026.domain.players.dto

import io.github.codenilson.lavava2026.domain.players.Player
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class PlayerResponseDTO(
    @field:Schema(example = "c56a4180-65aa-42ec-a945-5fd21dec0538") val id: UUID,
    @field:Schema(example = "AroMight") val gameName: String,
    @field:Schema(example = "BNHA") val tagName: String,
    @field:Schema(example = "10") val competitiveTier: String?,
    @field:Schema(example = "254") val accountLevel: Int,
    @field:Schema(example = "2025-10-13T12:34:56") val updatedAt: LocalDateTime,
    @field:Schema(example = "2025-10-01T08:00:00") var createdAt: LocalDateTime,

    // Added season-based stats
    @field:Schema(example = "3", description = "Current rank position for the season") val rankPosition: Int? = null,
    @field:Schema(example = "62.5", description = "Win rate (%) for the season") val winRate: Double? = null,
    @field:Schema(example = "27.5", description = "Headshot percentage for the season") val headshotPercentage: Double? = null,
    @field:Schema(example = "4", description = "MVPs in the season") val mvpCount: Long? = null,
    @field:Schema(example = "1", description = "Aces in the season") val aceCount: Long? = null,
    @field:Schema(example = "2", description = "Knife deaths in the season") val knifeDeaths: Long? = null,
    @field:Schema(description = "Per-opponent kills/deaths in the season") val versus: List<PlayerVersusDTO> = emptyList(),
) {
    constructor(player: Player) : this(
        id = player.puuid,
        gameName = player.gameName,
        tagName = player.tagName,
        competitiveTier = player.competitiveTier,
        accountLevel = player.accountLevel,
        updatedAt = player.updatedAt!!,
        createdAt = player.createdAt!!
    )

    constructor(
        player: Player,
        rankPosition: Int?,
        winRate: Double?,
        headshotPercentage: Double?,
        mvpCount: Long?,
        aceCount: Long?,
        knifeDeaths: Long?,
        versus: List<PlayerVersusDTO>
    ) : this(
        id = player.puuid,
        gameName = player.gameName,
        tagName = player.tagName,
        competitiveTier = player.competitiveTier,
        accountLevel = player.accountLevel,
        updatedAt = player.updatedAt!!,
        createdAt = player.createdAt!!,
        rankPosition = rankPosition,
        winRate = winRate,
        headshotPercentage = headshotPercentage,
        mvpCount = mvpCount,
        aceCount = aceCount,
        knifeDeaths = knifeDeaths,
        versus = versus
    )
}
