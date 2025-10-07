package io.github.codenilson.lavava2026.players

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PlayerRepository: JpaRepository<Player, UUID> {
}