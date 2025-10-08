package io.github.codenilson.lavava2026.matches

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MatchRepostiory: JpaRepository<Match, UUID> {}