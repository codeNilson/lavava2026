package io.github.codenilson.lavava2026.rounds

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoundRepository: JpaRepository<Round, UUID>