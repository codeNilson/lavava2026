package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.rounds.RoundKill
import io.github.codenilson.lavava2026.domain.rounds.RoundKillRepository
import org.springframework.stereotype.Service

@Service
class RoundKillService(
    private val roundKillRepository: RoundKillRepository,
) {
    fun saveAll(rounds: List<RoundKill>): List<RoundKill> {
        return roundKillRepository.saveAll(rounds)
    }
}