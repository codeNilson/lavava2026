package io.github.codenilson.lavava2026.players

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlayerRepository: JpaRepository<Player, UUID> {
    fun findByActiveTrue(): List<Player>
}