package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/players")
class PlayerController(
    private val playerService: PlayerService
) {

    @GetMapping
    fun getPlayers(
        @RequestParam(required = false) active: Boolean?,
    ): ResponseEntity<List<PlayerResponseDTO>> {
        val players = playerService.findAll(active)
        return ResponseEntity.ok(players)
    }
}