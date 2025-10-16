package io.github.codenilson.lavava2026.domain.matches

import io.github.codenilson.lavava2026.domain.playersPerformances.PlayerPerformance
import io.github.codenilson.lavava2026.domain.rounds.Round
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
@EntityListeners(AuditingEntityListener::class)
class Match(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,
    @Column(nullable = false, unique = true) var matchRiotId: String,
    //TODO: Verify '?'
    var gameLength: Int,
    var map: String,
    var gameStartMillis: Long,
    var isCompleted: Boolean,
    // customGameName,
    @Column(nullable = false) var season: String,
    @OneToMany(mappedBy = "match") val performances: MutableList<PlayerPerformance> = mutableListOf(),
    @OneToMany(mappedBy = "match") val rounds: MutableList<Round> = mutableListOf(),
    @LastModifiedDate @Column(nullable = false) var updatedAt: LocalDateTime? = null,
    @CreatedDate @Column(nullable = false, updatable = false) var createdAt: LocalDateTime? = null,
)
