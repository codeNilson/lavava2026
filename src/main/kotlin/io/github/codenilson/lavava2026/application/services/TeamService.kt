package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.teams.Team
import io.github.codenilson.lavava2026.domain.teams.TeamRepository
import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
import org.springframework.stereotype.Service


@Service
class TeamService(
        private val teamRepository: TeamRepository,
) {

    fun save(team: Team): Team {
        return teamRepository.save(team)
    }

    fun saveAll(teams: List<Team>): List<Team> {
        return teamRepository.saveAll(teams)
    }

    fun createTeamsFromDTO(valorantTeams: List<TeamDTO>): List<Team> {
        val teams =
                valorantTeams.map {
                    Team(
                            color = it.teamId,
                            won = it.won,
                            roundsWon = it.rounds.won,
                            roundLost = it.rounds.lost,
                    )
                }
        return teams
    }
}
