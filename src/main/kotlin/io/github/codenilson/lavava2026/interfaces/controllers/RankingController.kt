package io.github.codenilson.lavava2026.interfaces.controllers

import io.github.codenilson.lavava2026.application.services.RankingService
import io.github.codenilson.lavava2026.domain.ranking.dto.PlayerRankingDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ranking")
class RankingController(
    private val rankingService: RankingService
) {

    @GetMapping
    fun getRanking(): ResponseEntity<List<PlayerRankingDTO>> {
        val ranking = rankingService.getPlayerRanking()
        return ResponseEntity.ok(ranking)
    }
}