package io.github.codenilson.lavava2026.application.mapper

import io.github.codenilson.lavava2026.domain.teams.Team
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import org.springframework.stereotype.Component

@Component
class TeamMapper {
    fun fromTeamDTO(teamDTO: TeamDTO) : Team {
        return Team(
            teamRiotId = teamDTO.teamId,
            won = teamDTO.won,
            roundsPlayed = teamDTO.roundsPlayed,
            roundsWon = teamDTO.roundsWon,
//          TODO: numPoints = teamDTO.numPoints
        )
    }
}