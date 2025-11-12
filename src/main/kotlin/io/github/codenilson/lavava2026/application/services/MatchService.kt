package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
import io.github.codenilson.lavava2026.application.usecases.SyncMatchUseCase
import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.MatchInfoDTO
import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val valorantIntegrationService: ValorantIntegrationService,
    private val syncMatchUseCase: SyncMatchUseCase,
//    private val matchMapper: MatchMapper,
) {
//    fun saveFromValorantMatch(valorantMatch: ValorantMatchDTO): Match {
//
//        if (matchAlreadyExists(valorantMatch.matchInfo.matchId)) {
//            throw ResourceAlreadyExists("Match with id ${valorantMatch.matchInfo.matchId} already exists")
//        }
//
//        val match = matchMapper.fromValorantMatch(valorantMatch.matchInfo)
//        return matchRepository.save(match)
//    }

    fun matchAlreadyExists(matchId: UUID): Boolean {
        return matchRepository.existsByMatchRiotId(matchId)
    }

    // função temporária só para testes.
    @Transactional
    fun syncMatch(matchId: UUID): ValorantMatchDTO {

        if (matchAlreadyExists(matchId)) {
            throw ResourceAlreadyExists("Match with id $matchId already exists")
        }

        val valorantMatch = valorantIntegrationService.fetchMatch(matchId).block() ?: throw Exception("Match not found")
        syncMatchUseCase.sync(valorantMatch)
        return valorantMatch
    }

    fun saveValorantMatch(valorantMatch: MatchInfoDTO) : Match {
        return matchRepository.save(Match(
            matchRiotId = valorantMatch.matchId,
            gameLength = valorantMatch.gameLengthMillis,
            map = valorantMatch.map.name,
            startedAt = valorantMatch.startedAt,
            isCompleted = valorantMatch.isCompleted,
            season = "2026",
        ))
    }
}