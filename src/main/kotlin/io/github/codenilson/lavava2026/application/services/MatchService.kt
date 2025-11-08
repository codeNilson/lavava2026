package io.github.codenilson.lavava2026.application.services
//
//import io.github.codenilson.lavava2026.application.exceptions.ResourceAlreadyExists
//import io.github.codenilson.lavava2026.domain.matches.Match
//import io.github.codenilson.lavava2026.domain.matches.MatchRepository
//import io.github.codenilson.lavava2026.domain.valorant.dto.matches.ValorantMatchDTO
//import org.springframework.stereotype.Service
//
//@Service
//class MatchService(
//    private val matchRepository: MatchRepository,
//    private val matchMapper: MatchMapper,
//) {
//    fun saveFromValorantMatch(valorantMatch: ValorantMatchDTO): Match {
//
//        if (matchAlreadyExists(valorantMatch.matchInfo.matchId)) {
//            throw ResourceAlreadyExists("Match with id ${valorantMatch.matchInfo.matchId} already exists")
//        }
//
//        val match = matchMapper.fromValorantMatch(valorantMatch.matchInfo)
//        return matchRepository.save(match)
//    }
//
//    fun matchAlreadyExists(matchId: String): Boolean {
//        return matchRepository.existsByMatchRiotId(matchId)
//    }
//}