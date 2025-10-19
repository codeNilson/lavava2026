package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.mapper.RoundMapper
import io.github.codenilson.lavava2026.domain.rounds.Round
import io.github.codenilson.lavava2026.domain.rounds.RoundRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import org.springframework.stereotype.Service

@Service
class RoundService(
    private val roundRepository: RoundRepository,
    private val roundMapper: RoundMapper,
) {

    fun saveFromRoundStatusDTO(roundResultDTO: RoundResultDTO): Round {
        val round = roundMapper.fromRoundResultDTO(roundResultDTO)
        return save(round)
    }

    fun save(round: Round): Round {
        return roundRepository.save(round)
    }
}