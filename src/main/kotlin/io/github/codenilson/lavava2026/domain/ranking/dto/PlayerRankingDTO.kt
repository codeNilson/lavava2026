package io.github.codenilson.lavava2026.domain.ranking.dto

import java.math.BigDecimal
import java.util.UUID

/**
 * DTO (Data Transfer Object) para representar um jogador no ranking.
 */
data class PlayerRankingDTO(
    // Informações do Jogador
    val playerId: String,
    val gameName: String,
    val tagName: String,

    // Estatísticas da Partida
    val matchesPlayed: Long,
    val matchesWon: Long,
    val matchesLost: Long,
    val winRate: BigDecimal,

    // Estatísticas de Pontuação
    val mvpCount: Long,
    val aceCount: Long,
    val knifeKillPoints: Long,

    // Pontuação
    val pointsFromWins: Long,
    val totalPoints: Long
)