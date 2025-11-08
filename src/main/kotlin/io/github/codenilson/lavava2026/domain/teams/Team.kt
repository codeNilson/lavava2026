package io.github.codenilson.lavava2026.domain.teams

import io.github.codenilson.lavava2026.domain.matches.Match
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "teams")
class Team(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,

    var teamRiotId: String,

    // TODO: Maybe accept null and have a default value
    var won: Boolean,
    var roundsPlayed: Int,
    var roundsWon: Int,

    @ManyToOne var match: Match,

    @LastModifiedDate @Column(nullable = false) var updatedAt: LocalDateTime? = null,

    @CreatedDate @Column(nullable = false, updatable = false) var createdAt: LocalDateTime? = null,

    // @OneToMany(mappedBy = "team")
    // var performances: MutableList<Performance> = mutableListOf(),
)
