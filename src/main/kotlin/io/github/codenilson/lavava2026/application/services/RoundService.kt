package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.rounds.Round
import io.github.codenilson.lavava2026.domain.rounds.RoundRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import org.springframework.stereotype.Service

@Service
class RoundService(
    private val roundRepository: RoundRepository,
) {

    fun save(round: Round): Round {
        return roundRepository.save(round)
    }

    fun saveAll(rounds: List<Round>): List<Round> {
        return roundRepository.saveAll(rounds)
    }

    fun createRoundsFromDTO(roundResult: List<RoundResultDTO>) : List<Round> {
        return roundResult.map {
            Round(
                roundNumber = it.roundNum + 1,
                result = it.result,
                winningTeamColor = it.winningTeam,
            )
        }
    }
}