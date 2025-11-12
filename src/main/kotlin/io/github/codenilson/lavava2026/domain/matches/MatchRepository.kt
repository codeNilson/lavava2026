package io.github.codenilson.lavava2026.domain.matches

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MatchRepository: JpaRepository<Match, UUID> {
    fun existsByMatchRiotId(matchRiotId: UUID): Boolean
}