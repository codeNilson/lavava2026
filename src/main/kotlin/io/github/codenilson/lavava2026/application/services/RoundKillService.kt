package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.rounds.RoundKill
import io.github.codenilson.lavava2026.domain.rounds.RoundKillRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.players.KillDTO
import org.springframework.stereotype.Service

@Service
class RoundKillService(
    private val roundKillRepository: RoundKillRepository,
    private val playerRepository: PlayerRepository,
) {
    fun saveAll(rounds: List<RoundKill>): List<RoundKill> {
        return roundKillRepository.saveAll(rounds)
    }

    fun createKillsFromDTO(roundKills: List<KillDTO>) : List<RoundKill> {
        return roundKills.map {
            RoundKill(
                killer =  playerRepository.getReferenceById(it.killer.puuid),
                victim = playerRepository.getReferenceById(it.victim.puuid),
                weapon = it.weapon.name,
                weaponType = it.weapon.type,
                roundNum = it.round,
            )
        }
    }
}