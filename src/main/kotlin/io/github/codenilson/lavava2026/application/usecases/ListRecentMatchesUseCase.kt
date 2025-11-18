package io.github.codenilson.lavava2026.application.usecases

import io.github.codenilson.lavava2026.application.services.ValorantIntegrationService
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.stereotype.Service

@Service
class ListRecentMatchesUseCase(
    private val valorantIntegrationService: ValorantIntegrationService,
) {
    /**
     * Executa a busca por partidas recentes de um jogador.
     *
     * @param region Região do jogador (ex: "br").
     * @param platform Plataforma do jogador (ex: "pc").
     * @param gameName Nome do jogo do jogador.
     * @param tagName Tag do jogador.
     * @param map (Opcional) Filtro por mapa específico.
     * @return Lista de partidas recentes do jogador.
     */
    fun execute(
        region: String = "br",
        platform: String = "pc",
        gameName: String,
        tagName: String,
        map: String? = null,
    ): List<ValorantMatchDTO>? {
        return valorantIntegrationService.fetchMatches(
            region = region,
            platform = platform,
            gameName = gameName,
            tagName = tagName,
            map = map
        ).block()
    }
}