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

    var color: String,

    var won: Boolean,
    var roundsWon: Int,
    var roundsLost: Int,

    @ManyToOne var match: Match? = null,

    @LastModifiedDate @Column(nullable = false) var updatedAt: LocalDateTime? = null,

    @CreatedDate @Column(nullable = false, updatable = false) var createdAt: LocalDateTime? = null,

)
