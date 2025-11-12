// Arquivo: .../controllers/MatchController.kt
package io.github.codenilson.lavava2026.interfaces.controllers

import io.github.codenilson.lavava2026.application.usecases.SyncMatchUseCase
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/matches")
class MatchController(
    private val syncMatchUseCase: SyncMatchUseCase
) {

    @GetMapping("/{matchId}")
    fun getMatchInfo(@PathVariable matchId: UUID): ValorantMatchDTO {
        return syncMatchUseCase.execute(matchId)
    }
}