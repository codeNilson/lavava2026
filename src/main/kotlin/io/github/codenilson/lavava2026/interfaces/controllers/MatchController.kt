package io.github.codenilson.lavava2026.interfaces.controllers

import io.github.codenilson.lavava2026.application.services.MatchService
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/matches")
class MatchController(
    private val matchService: MatchService,
) {

    @GetMapping("/{matchId}")
    fun getMatchInfo(@PathVariable matchId: String): ValorantMatchDTO {
        return matchService.getValorantMatchInfo(matchId)
    }
}