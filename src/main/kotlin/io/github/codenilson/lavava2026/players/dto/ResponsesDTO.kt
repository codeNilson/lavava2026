package io.github.codenilson.lavava2026.players.dto

import io.github.codenilson.lavava2026.players.Player
import java.time.LocalDateTime
import java.util.UUID

data class PlayerResponseDTO(
    val id: UUID,
    val gameName: String,
    val tagName: String,
    val competitiveTier: Int,
    val playerCard: String,
    val playerTitle: String,
    val accountLevel: Int,
    val active: Boolean,
    val updatedAt: LocalDateTime,
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
