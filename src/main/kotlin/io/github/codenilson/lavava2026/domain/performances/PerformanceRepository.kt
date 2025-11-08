package io.github.codenilson.lavava2026.domain.performances

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PerformanceRepository: JpaRepository<Performance, UUID>