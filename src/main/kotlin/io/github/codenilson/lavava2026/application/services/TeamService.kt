package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.teams.Team
import io.github.codenilson.lavava2026.domain.teams.TeamRepository
import org.springframework.stereotype.Service

@Service
class TeamService(
    private val teamRepository: TeamRepository,
) {
    fun getTeamByRiotId(teamRiotId: String): Team {
        val team = teamRepository.findTeamByTeamRiotId(teamRiotId)
            ?: throw ResourceNotFoundException("Team $teamRiotId not found")
        return team
    }
}