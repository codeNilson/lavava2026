package io.github.codenilson.lavava2026.domain.players

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.UUID

@Entity
@Table(
    name = "player_stats",
    uniqueConstraints = [UniqueConstraint(columnNames = ["player_puuid", "season"])],
)
class PlayerStats(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,

    @ManyToOne @JoinColumn(nullable = false) var player: Player,

    val season: String,
    val winrate: Double,
    val headshotPercentage: Double,
    val preferredWeapon: String,
    val mvpCount: Int,
    val aceCount: Int,
    val knifeKills: Int,
    @ElementCollection
    val versus: MutableList<Versus> = mutableListOf(),
)

@Embeddable
data class Versus(
    val opponentPuuid: UUID,
    val game_name: String,
    val tag_name: String,
    val kills: Int,
    val deaths: Int,
)