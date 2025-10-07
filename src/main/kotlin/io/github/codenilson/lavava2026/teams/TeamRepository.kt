package io.github.codenilson.lavava2026.teams

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TeamRepository: JpaRepository<Team, UUID> {
}