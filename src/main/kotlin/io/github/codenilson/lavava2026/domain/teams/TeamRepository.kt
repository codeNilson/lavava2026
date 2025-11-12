package io.github.codenilson.lavava2026.domain.teams

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository: JpaRepository<Team, UUID>