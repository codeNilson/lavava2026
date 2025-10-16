package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


//TODO: Change to Valorant API
@Service
class RiotApiService(private val webClient: WebClient) {
    fun fetchMatch(matchId: String): Mono<ValorantMatchDTO> {
        return webClient
                .get()
                .uri("/lol/match/v5/matches/$matchId")
                .retrieve()
                .bodyToMono(ValorantMatchDTO::class.java)
    }
}
