package io.github.codenilson.lavava2026.application.services

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RiotApiService(private val webClient: WebClient) {
    fun fetchMatch(matchId: String): Mono<String> {
        return webClient
                .get()
                .uri("/lol/match/v5/matches/$matchId")
                .retrieve()
                .bodyToMono(String::class.java)
    }
}
