package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.players.dto.PlayerResponseDTO
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {

    fun save(player: Player): Player {
        return playerRepository.save(player)
    }

    fun saveAll(players: List<Player>): List<Player> {
        return playerRepository.saveAll(players)
    }

    fun findAll(active: Boolean? = null, sort: Sort): List<PlayerResponseDTO> {
        val players = if (active == null) {
            playerRepository.findAll(sort)
        } else {
            playerRepository.findByActive(active, sort)
        }

        return players.map(::PlayerResponseDTO)
    }

    fun findByPuuid(puuid: UUID): Player {
        val player = playerRepository.findById(puuid)
            .orElseThrow { throw ResourceNotFoundException("Player with puuid $puuid not found") }
        return player
    }

}