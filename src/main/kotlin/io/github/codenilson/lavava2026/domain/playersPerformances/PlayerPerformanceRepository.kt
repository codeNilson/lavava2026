package io.github.codenilson.lavava2026.domain.playersPerformances

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PlayerPerformanceRepository: JpaRepository<PlayerPerformance, UUID>