package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.InvalidCredentialsException
import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration


//TODO: Change to Valorant API
@Service
class RiotApiService(private val webClient: WebClient) {
    fun fetchMatch(matchId: String): Mono<ValorantMatchDTO> {
        return webClient
            .get()
            .uri("/valorant/v4/match/br/$matchId")
            .retrieve()
            .onStatus({ status -> status == HttpStatus.UNAUTHORIZED }, {
                resp -> Mono.error(InvalidCredentialsException("Unauthorized when calling Riot API"))
            })
            // TODO: Handle other status codes (404, 500, etc)
            .bodyToMono(ValorantMatchDTO::class.java)
            .timeout(Duration.ofSeconds(15))
    }
}
