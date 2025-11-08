package io.github.codenilson.lavava2026.domain.performances

import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.teams.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "performances",
    uniqueConstraints = [UniqueConstraint(columnNames = ["player_id", "match_id", "team_id"])]
)
class Performance(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,
    var agent: String,
    var score: Int,
    var kills: Int,
    var deaths: Int,
    var assists: Int,
    val headshots: Int,
    val legshots: Int,
    val bodyshots: Int,
    // var roundsPlayed: Int,

    @ManyToOne @JoinColumn(name = "player_id") var player: Player,
    @ManyToOne @JoinColumn(name = "match_id", nullable = false) var match: Match? = null,
    @ManyToOne @JoinColumn(name = "team_id") var team: Team,
    @LastModifiedDate @Column(nullable = false) var updatedAt: LocalDateTime? = null,
    @CreatedDate @Column(nullable = false, updatable = false) var createdAt: LocalDateTime? = null
) {
    /**
     * Calculates the Kill/Death/Assist ratio
     * If deaths is 0, returns the sum of kills and assists
     * Otherwise, returns (kills + assists) / deaths rounded to 2 decimal places
     */
    val kda: Double
        get() = if (deaths == 0) {
            (kills + assists).toDouble()
        } else {
            kotlin.math.round((kills + assists).toDouble() / deaths * 100) / 100.0
        }

    /**
     * Calculates the percentage of headshots over total shots
     * Returns 0.0 if no shots were fired
     */
    val headshotPercentage: Double
        get() {
            val totalShots = headshots + legshots + bodyshots
            return if (totalShots == 0) {
                0.0
            } else {
                kotlin.math.round((headshots.toDouble() / totalShots) * 10000) / 100.0
            }
        }
}
