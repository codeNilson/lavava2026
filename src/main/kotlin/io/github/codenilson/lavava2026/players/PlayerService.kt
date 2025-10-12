package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO
import org.springframework.stereotype.Service

@Service
class PlayerService (
    private val playerRepository: PlayerRepository
) {
    fun findAll(active: Boolean?): List<PlayerResponseDTO> {
        val players = when (active) {
            true -> playerRepository.findByActiveTrue()
            false -> playerRepository.findByActiveFalse()
            null -> playerRepository.findAll()
        }
        return players.map { PlayerResponseDTO(it) }
    }
}
