package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.mapper.MatchMapper
import io.github.codenilson.lavava2026.application.mapper.TeamMapper
import io.github.codenilson.lavava2026.domain.matches.MatchRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MatchService(
    private val matchRepository: MatchRepository,
    private val riotApiService: RiotApiService,
    private val matchMapper: MatchMapper,
    private val teamMapper: TeamMapper,
) {
    @Transactional
    fun syncMatch(matchId: String) {
        val valorantMatch = riotApiService.fetchMatch(matchId).block()
        val match = matchMapper.fromValorantMatch(valorantMatch!!.matchInfo)

        val (team1, team2) = valorantMatch.teams

    }
}