package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.mapper.MatchMapper
import io.github.codenilson.lavava2026.domain.matches.Match
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.ValorantMatchDTO
import org.springframework.stereotype.Service

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val matchMapper: MatchMapper,
) {
    fun saveFromValorantMatch(valorantMatch: ValorantMatchDTO): Match {

        val match = matchMapper.fromValorantMatch(valorantMatch.matchInfo)
        return matchRepository.save(match)
    }
}