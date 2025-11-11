package io.github.codenilson.lavava2026.domain.players

import io.github.codenilson.lavava2026.domain.performances.Performance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
        name = "players",
        uniqueConstraints = [UniqueConstraint(columnNames = ["game_name", "tag_name"])]
)
class Player(
        @Id var puuid: UUID,

//        @Column(nullable = false, unique = true)
//        var puuid: UUID,

        @Column(name = "game_name", nullable = false)
        var gameName: String,

        @Column(name = "tag_name", nullable = false)
        var tagName: String,


        // TODO: Verify constraints after getting Riot API
        var competitiveTier: String? = null,
        // var playerCard: String? = null,
        // var playerTitle: String? = null,
        var accountLevel: Int,

        var active: Boolean = true,

        @OneToMany(mappedBy = "player")
        val performances: MutableList<Performance> = mutableListOf(),

        @LastModifiedDate @Column(nullable = false)
        var updatedAt: LocalDateTime? = null,

        @CreatedDate @Column(nullable = false, updatable = false)
        var createdAt: LocalDateTime? = null
)
