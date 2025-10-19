package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.application.services.PlayerService
import io.github.codenilson.lavava2026.domain.rounds.Round
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import org.springframework.stereotype.Component

@Component
class RoundMapper(
    private val playerService: PlayerService
) {
    fun fromRoundResultDTO(roundResultDTO: RoundResultDTO): Round {
        return Round(
            roundNumber = roundResultDTO.roundNum,
            bombPlanter = playerService.findByPuuid(roundResultDTO.bombPlanter),
            bombDefuser = playerService.findByPuuid(roundResultDTO.bombPlanter),
        )
    }
}