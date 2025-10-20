package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.players.dto.PlayerResponseDTO
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

    fun findByPuuid(puuid: String) : Player { // TODO: change to players Response
        val player = playerRepository.findByPuuid(puuid) ?: throw ResourceNotFoundException("Player with puuid $puuid not found")
        return player
    }

    fun findAllByPuuidIn(puuids: List<String>): List<Player> {
        return playerRepository.findByPuuidIn(puuids)
    }
}