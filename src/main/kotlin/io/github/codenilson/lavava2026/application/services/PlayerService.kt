package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.players.dto.PlayerResponseDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.players.PlayerInfoDTO
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {

    fun save(player: Player): player {
        return playerRepository.save(player)
    }

    fun findAll(active: Boolean? = null, sort: Sort): List<PlayerResponseDTO> {
        val players = if (active == null) {
            playerRepository.findAll(sort)
        } else {
            playerRepository.findByActive(active, sort)
        }

        return players.map(::PlayerResponseDTO)
    }

    fun findByPuuid(puuid: UUID): Player { // TODO: change to players Response
        val player = playerRepository.findById(puuid)
            .orElseThrow { throw ResourceNotFoundException("Player with puuid $puuid not found") }
        return player
    }

    fun findAllByPuuid(puuids: List<UUID>): List<Player> {
        return playerRepository.findByPuuidIn(puuids)
    }

    fun createPlayersFromDTO(playersInfos: List<PlayerInfoDTO>): List<Player> {

        val players = playersInfos.map {
            Player(
                puuid = it.puuid,
                gameName = it.name,
                tagName = it.tag,
                competitiveTier = it.competitiveTier.name,
                accountLevel = it.accountLevel,
            )
        }

        return players
    }

}