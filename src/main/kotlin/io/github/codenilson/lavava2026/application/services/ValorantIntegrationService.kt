package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.InvalidCredentialsException
import io.github.codenilson.lavava2026.domain.valorant.dto.HenrikResponseDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration


//TODO: Change to Valorant API
@Service
class ValorantIntegrationService(private val valorantWebClient: WebClient) {
    fun fetchMatch(matchId: String): Mono<ValorantMatchDTO> {
        return valorantWebClient
            .get()
            .uri("/valorant/v4/match/br/$matchId")
            .retrieve()
            .onStatus({ status -> status == HttpStatus.UNAUTHORIZED }, { resp ->
                Mono.error(InvalidCredentialsException("Unauthorized when calling Riot API"))
            })
            // TODO: Handle other status codes (404, 500, etc)
            .bodyToMono(object : ParameterizedTypeReference<HenrikResponseDTO<ValorantMatchDTO>>() {})
            .map { it.data }
            .timeout(Duration.ofSeconds(15))
    }
}
