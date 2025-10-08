package io.github.codenilson.lavava2026.matches

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
@Table(name = "matches")
@EntityListeners(AuditingEntityListener::class)
class Match(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,
        @Column(nullable = false, unique = true) var matchRiotId: String,
        //TODO: Verify '?'
        var gameLength: Int,
        var map: String,
        var gameStartMillis: Long,
        var isCompleted: Boolean,
        // customGameName,
        @OneToMany(mappedBy = "match") var performances: MutableList<PlayerPerformance> = mutableListOf(),
        @LastModifiedDate @Column(nullable = false) var updatedAt: ZonedDateTime,
        @CreatedDate @Column(nullable = false, updatable = false) var createdAt: ZonedDateTime
)
