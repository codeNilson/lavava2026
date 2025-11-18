package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.InvalidCredentialsException
import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.valorant.dto.HenrikResponseDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.UUID


@Service
class ValorantIntegrationService(
    private val valorantWebClient: WebClient
) {
    fun fetchMatch(matchId: UUID): Mono<ValorantMatchDTO> {
        return valorantWebClient
            .get()
            .uri("/valorant/v4/match/br/$matchId")
            .retrieve()
            .onStatus({ status -> status == HttpStatus.UNAUTHORIZED }, { resp ->
                Mono.error(InvalidCredentialsException("Unauthorized when calling Riot API"))
            })
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }, { resp ->
                Mono.error(ResourceNotFoundException("Match with id $matchId not found"))
            })
            // TODO: Handle other status codes (404, 500, etc)
            .bodyToMono(object : ParameterizedTypeReference<HenrikResponseDTO<ValorantMatchDTO>>() {})
            .map { it.data }
            .timeout(Duration.ofSeconds(15))
    }

    fun fetchMatches(
        region: String = "br",
        platform: String = "pc",
        gameName: String,
        tagName: String,
        map: String? = null,
    ): Mono<List<ValorantMatchDTO>> {
        return valorantWebClient
            .get()
            .uri("/valorant/v4/matches/$region/$platform/$gameName/$tagName") {
                if (map != null) {
                    it.queryParam("map", map)
                }
                it.build()
            }
            .retrieve()
            .onStatus({ status -> status == HttpStatus.UNAUTHORIZED }, { resp ->
                Mono.error(InvalidCredentialsException("Unauthorized when calling Riot API"))
            })
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }, { resp ->
                Mono.error(ResourceNotFoundException("Matches for player $gameName#$tagName not found"))
            })
            .bodyToMono(object : ParameterizedTypeReference<HenrikResponseDTO<List<ValorantMatchDTO>>>() {})
            .map { it.data }
            .timeout(Duration.ofSeconds(15))
    }
}
