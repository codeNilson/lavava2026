// Arquivo: .../controllers/MatchController.kt
package io.github.codenilson.lavava2026.interfaces.controllers

import io.github.codenilson.lavava2026.application.usecases.SyncMatchUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/matches")
class MatchController(
    private val syncMatchUseCase: SyncMatchUseCase
) {

    @PostMapping("/{matchId}")
    fun getMatchInfo(@PathVariable matchId: UUID): ResponseEntity<Void> {
        syncMatchUseCase.execute(matchId)
        return ResponseEntity.ok().build()
    }
}