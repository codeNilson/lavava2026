package io.github.codenilson.lavava2026.domain.players.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

/**
 * Versus stats against a specific opponent in the given season
 */
data class PlayerVersusDTO(
    @field:Schema(example = "c56a4180-65aa-42ec-a945-5fd21dec0538") val opponentId: UUID,
    @field:Schema(example = "Xaheen") val gameName: String,
    @field:Schema(example = "BR1") val tagName: String,
    @field:Schema(example = "6") val killed: Long,
    @field:Schema(example = "4") val died: Long
)

