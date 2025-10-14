package io.github.codenilson.lavava2026.domain.rounds

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoundKillRepository: JpaRepository<RoundKill, UUID>