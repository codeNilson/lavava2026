package io.github.codenilson.lavava2026.players

import org.springframework.data.jpa.JpaRepository
import java.util.UUID

interface PlayerRepository: JpaRepository<Player, UUID> {
}