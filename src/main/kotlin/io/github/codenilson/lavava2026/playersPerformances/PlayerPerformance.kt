
package io.github.codenilson.lavava2026.playersPerformances

import io.github.codenilson.lavava2026.matches.Match
import io.github.codenilson.lavava2026.players.Player
import io.github.codenilson.lavava2026.teams.Team
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
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
        name = "players_performances",
        uniqueConstraints = [UniqueConstraint(columnNames = ["player_id", "match_id", "team_id"])]
)
class PlayerPerformance(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,
        var score: Int,
        var roundsPlayed: Int,
        var kills: Int,
        var deaths: Int,
        var assists: Int,

        @ManyToOne @JoinColumn(name = "player_id") var player: Player,
        @ManyToOne @JoinColumn(name = "match_id") var match: Match,
        @ManyToOne @JoinColumn(name = "team_id") var team: Team,
        @LastModifiedDate @Column(nullable = false) var updatedAt: ZonedDateTime,
        @CreatedDate @Column(nullable = false, updatable = false) var createdAt: ZonedDateTime
) {
    val kda: Double
        get() = if (deaths == 0) {
            (kills + assists).toDouble()
        } else {
            kotlin.math.round((kills + assists).toDouble() / deaths * 100) / 100.0
        }
}

// REPRESENTA CAMPOS PlayerStatsDto DA API DA RIOT