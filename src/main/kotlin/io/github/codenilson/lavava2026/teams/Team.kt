package io.github.codenilson.lavava2026.teams

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "teams")
class Team (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID,

    // TODO: Maybe accept null and have a default value
    var won: Boolean,

    var roundsPlayed: Int,

    var roundsWon: Int,

)