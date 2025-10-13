package io.github.codenilson.lavava2026.players

import io.github.codenilson.lavava2026.players.dto.PlayerResponseDTO
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.domain.Sort
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

    @ApiResponse(
        responseCode = "200",
        description = "Returns a list of players, optionally filtered by active status and sorted by specified field and direction.",
        content = [
            Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PlayerResponseDTO::class))
            )
        ]
    )
    @GetMapping
    fun getPlayers(
        @RequestParam(required = false)
        @Parameter(description = "Filter by active status. true for only active, false for only inactive, null for all.")
        active: Boolean?,
        @RequestParam(defaultValue = "gameName")
        @Parameter(description = "Field to order by. Possible values: gameName, id, tagName, competitiveTier, accountLevel, createdAt, updatedAt.")
        orderBy: String,
        @RequestParam(defaultValue = "ASC")
        @Parameter(description = "Sort direction. Possible values: ASC, DESC.")
        direction: String
    ): ResponseEntity<List<PlayerResponseDTO>> {
        val sort = Sort.by(Sort.Direction.fromString(direction.uppercase()), orderBy)
        val players = playerService.findAll(active, sort)
        return ResponseEntity.ok(players)
    }
}
