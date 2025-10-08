package io.github.codenilson.lavava2026.teams

import io.github.codenilson.lavava2026.playersPerformances.PlayerPerformance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "teams")
class Team(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID,

        // TODO: Maybe accept null and have a default value
        var won: Boolean,
        var roundsPlayed: Int,
        var roundsWon: Int,

        @OneToMany(mappedBy = "team")
        var performances: MutableList<PlayerPerformance> = mutableListOf(),

        @LastModifiedDate @Column(nullable = false)
        var updatedAt: ZonedDateTime,

        @CreatedDate @Column(nullable = false, updatable = false)
        var createdAt: ZonedDateTime
)
