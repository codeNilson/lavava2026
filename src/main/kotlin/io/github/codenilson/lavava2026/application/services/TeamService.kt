package io.github.codenilson.lavava2026.application.services

//import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
//import io.github.codenilson.lavava2026.domain.teams.Team
//import io.github.codenilson.lavava2026.domain.teams.TeamRepository
//import io.github.codenilson.lavava2026.domain.valorant.dto.teams.TeamDTO
//import org.springframework.stereotype.Service
//
//@Service
//class TeamService(
//    private val teamRepository: TeamRepository,
//    private val teamMapper: TeamMapper,
//) {
//    fun getTeamByRiotId(teamRiotId: String): Team {
//        val team = teamRepository.findTeamByTeamRiotId(teamRiotId)
//            ?: throw ResourceNotFoundException("Team $teamRiotId not found")
//        return team
//    }
//
//    fun saveValorantTeam(valorantTeam: TeamDTO): Team {
//        val team = teamMapper.fromTeamDTO(valorantTeam)
//        return save(team)
//    }
//
//    fun save(team: Team): Team {
//        return teamRepository.save(team)
//    }
//
//    fun findAllByTeamRiotIdIn(teamRiotIds: List<String>): List<Team> {
//        return teamRepository.findByTeamRiotIdIn(teamRiotIds)
//    }
//}