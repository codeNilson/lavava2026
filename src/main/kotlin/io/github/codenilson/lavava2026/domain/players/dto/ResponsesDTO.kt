package io.github.codenilson.lavava2026.domain.players.dto

import io.github.codenilson.lavava2026.domain.players.Player
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class PlayerResponseDTO(
    @field:Schema(example = "c56a4180-65aa-42ec-a945-5fd21dec0538")
    val id: UUID,
    @field:Schema(example = "AroMight")
    val gameName: String,
    @field:Schema(example = "BNHA")
    val tagName: String,
    @field:Schema(example = "10")
    val competitiveTier: Int,
    @field:Schema(example = "card123")
    val playerCard: String,
    @field:Schema(example = "title123")
    val playerTitle: String,
    @field:Schema(example = "254")
    val accountLevel: Int,
    @field:Schema(example = "true")
    val active: Boolean,
    @field:Schema(example = "2025-10-13T12:34:56")
    val updatedAt: LocalDateTime,
    @field:Schema(example = "2025-10-01T08:00:00")
    var createdAt: LocalDateTime,
) {
    constructor(player: Player) : this(
        id = player.id!!,
        gameName = player.gameName,
        tagName = player.tagName,
        competitiveTier = player.competitiveTier ?: 0,
        playerCard = player.playerCard ?: "",
        playerTitle = player.playerTitle ?: "",
        accountLevel = player.accountLevel ?: 0,
        active = player.active,
        updatedAt = player.updatedAt!!,
        createdAt = player.createdAt!!
    )
}
