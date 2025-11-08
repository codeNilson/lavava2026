package io.github.codenilson.lavava2026.application.services

//import io.github.codenilson.lavava2026.domain.players.Player
//import io.github.codenilson.lavava2026.domain.rounds.Round
//import io.github.codenilson.lavava2026.domain.rounds.RoundRepository
//import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
//import org.springframework.stereotype.Service
//
//@Service
//class RoundService(
//    private val roundRepository: RoundRepository,
//    private val roundMapper: RoundMapper,
//) {
//
//    fun createRoundFromDTO(roundResultDTO: RoundResultDTO, playersMap: Map<String, Player>): Round {
//        val bombPlanter = if (roundResultDTO.bombPlanter.isNotBlank()) playersMap[roundResultDTO.bombPlanter] else null
//        val bombDefuser = if (roundResultDTO.bombDefuser.isNotBlank()) playersMap[roundResultDTO.bombDefuser] else null
//
//        return roundMapper.fromRoundResultDTO(roundResultDTO, bombPlanter, bombDefuser)
//    }
//
//    fun save(round: Round): Round {
//        return roundRepository.save(round)
//    }
//
//    fun saveAll(rounds: List<Round>): List<Round> {
//        return roundRepository.saveAll(rounds)
//    }
//}