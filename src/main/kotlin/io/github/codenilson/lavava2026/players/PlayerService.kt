package io.github.codenilson.lavava2026.players

class PlayerService (
    private val playerRepository: PlayerRepository
) {
    fun findAll(): List<Player> = playerRepository.findAll()
}