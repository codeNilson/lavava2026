package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.playersPerformances.PlayerPerformance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.ZonedDateTime
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
        name = "players",
        uniqueConstraints = [UniqueConstraint(columnNames = ["game_name", "tag_name"])]
)
class Player(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,

        @Column(nullable = false, unique = true)
        var puuid: String,

        @Column(name = "game_name", nullable = false)
        var gameName: String,

        @Column(name = "tag_name", nullable = false)
        var tagName: String,


        // TODO: Verify constraints after getting Riot API
        var competitiveTier: Int? = null,
        var playerCard: String? = null,
        var playerTitle: String? = null,
        var accountLevel: Int? = null,

        var active: Boolean = true,

        @OneToMany(mappedBy = "player")
        var performances: MutableList<PlayerPerformance> = mutableListOf(),

        @LastModifiedDate @Column(nullable = false)
        var updatedAt: ZonedDateTime,

        @CreatedDate @Column(nullable = false, updatable = false)
        var createdAt: ZonedDateTime
)
