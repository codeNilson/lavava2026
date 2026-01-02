package io.github.codenilson.lavava2026.interfaces.controllers

import io.github.codenilson.lavava2026.application.usecases.ListRecentMatchesUseCase
import io.github.codenilson.lavava2026.application.usecases.SyncMatchUseCase
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/matches")
class MatchController(
    private val syncMatchUseCase: SyncMatchUseCase,
    private val listRecentMatchesUseCase: ListRecentMatchesUseCase,
) {

    @PostMapping("/{matchId}")
    fun getMatchInfo(@PathVariable matchId: UUID): ResponseEntity<Void> {
        syncMatchUseCase.execute(matchId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/recent/{gameName}/{tagName}")
    fun listRecentMatches(
        @PathVariable gameName: String,
        @PathVariable tagName: String,
        @RequestParam map: String?,
    ): ResponseEntity<List<ValorantMatchDTO>> {
        val recentMatches = listRecentMatchesUseCase.execute(
            gameName = gameName,
            tagName = tagName,
            map = map,
        )
        return ResponseEntity.ok(recentMatches)
    }
}