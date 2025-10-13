package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {
    fun findAll(active: Boolean? = null, sort: Sort): List<PlayerResponseDTO> {
        val players = if (active == null) {
            playerRepository.findAll(sort)
        } else {
            playerRepository.findByActive(active, sort)
        }
    
        return players.map(::PlayerResponseDTO)
    }
}
