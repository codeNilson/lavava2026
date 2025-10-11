package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO

class PlayerService (
    private val playerRepository: PlayerRepository
) {
    fun findAll(): List<PlayerResponseDTO> {
        return playerRepository.findAll().map { PlayerResponseDTO(it) }
    }

    fun findActivePlayers(): List<PlayerResponseDTO> {
        return playerRepository.findByActiveTrue().map { PlayerResponseDTO(it) }
    }
}
