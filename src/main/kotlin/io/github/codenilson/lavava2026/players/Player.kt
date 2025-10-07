package io.github.codenilson.lavava2026.players

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
class Player (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID,

    @Column(nullable = false, unique = true)
    var puuid: String,

    @Column(nullable = false)
    var gameName: String,

    @Column(nullable = false)
    var tagName: String,

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: ZonedDateTime,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: ZonedDateTime
)