package io.github.codenilson.lavava2026.domain.matches

import io.github.codenilson.lavava2026.domain.performances.Performance
import io.github.codenilson.lavava2026.domain.rounds.Round
import io.github.codenilson.lavava2026.domain.teams.Team
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
import java.time.Instant

@Entity
@Table(name = "matches")
@EntityListeners(AuditingEntityListener::class)
class Match(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,
    @Column(nullable = false, unique = true) var matchRiotId: String,
    var gameLength: Long,
    var map: String,
    var gameStartMillis: Long,
    var startedAt: Instant,
    var isCompleted: Boolean,
    @Column(nullable = false) var season: String,

    @OneToMany(mappedBy = "match") val teams: MutableList<Team> = mutableListOf(),

    @OneToMany(mappedBy = "match") val rounds: MutableList<Round> = mutableListOf(),

    // montado a partir de match >>> playerInfo (incluido) >> playerStats (incluido)
    @OneToMany(mappedBy = "match") val performances: MutableList<Performance> = mutableListOf(),

    @LastModifiedDate @Column(nullable = false) var updatedAt: LocalDateTime? = null,
    @CreatedDate @Column(nullable = false, updatable = false) var createdAt: LocalDateTime? = null,
)
