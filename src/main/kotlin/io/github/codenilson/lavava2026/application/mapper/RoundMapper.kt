package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.application.services.PlayerService
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.rounds.Round
import io.github.codenilson.lavava2026.domain.valorant.dto.rounds.RoundResultDTO
import org.springframework.stereotype.Component

@Component
class RoundMapper{
    fun fromRoundResultDTO(
        roundResultDTO: RoundResultDTO,
        bombPlanter: Player?,
        bombDefuseR: Player?,
    ): Round {
        return Round(
            roundNumber = roundResultDTO.roundNum,
            bombPlanter = bombPlanter,
            bombDefuser = bombDefuseR,
        )
    }
}