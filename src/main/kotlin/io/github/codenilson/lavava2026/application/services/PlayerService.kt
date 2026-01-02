package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.players.dto.PlayerResponseDTO
import io.github.codenilson.lavava2026.domain.players.dto.PlayerVersusDTO
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Year
import java.util.UUID

@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val playerStatsService: PlayerStatsService
) {

    private val CURRENT_SEASON = Year.now().toString()

    fun saveAll(players: List<Player>): List<Player> {
        return playerRepository.saveAll(players)
    }

    fun findAll(active: Boolean? = null, sort: Sort, season: String? = null): List<PlayerResponseDTO> {
        val effectiveSeason = season ?: CURRENT_SEASON

        val players = if (active == null) {
            playerRepository.findAllWithStatsForSeason(effectiveSeason)
        } else {
            playerRepository.findByActiveWithStatsForSeason(active, effectiveSeason)
        }

        val sortedPlayers = applySorting(players, sort)

        return sortedPlayers.map { enrichWithStats(it, effectiveSeason) }
    }

    fun findByPuuid(puuid: UUID): Player {
        val player = playerRepository.findById(puuid)
            .orElseThrow { throw ResourceNotFoundException("Player with puuid $puuid not found") }
        return player
    }

    fun getByPuuidWithStats(puuid: UUID, season: String? = null): PlayerResponseDTO {
        val player = findByPuuid(puuid)
        val effectiveSeason = season ?: CURRENT_SEASON
        return enrichWithStats(player, effectiveSeason)
    }

    private fun enrichWithStats(player: Player, season: String): PlayerResponseDTO {
        val playerStats = playerStatsService.findByPlayerAndSeason(player, season)

        return if (playerStats != null) {
            val versus = playerStats.versus.map { v ->
                PlayerVersusDTO(
                    opponentId = v.opponentPuuid,
                    gameName = v.gameName,
                    tagName = v.tagName,
                    killed = v.kills.toLong(),
                    died = v.deaths.toLong()
                )
            }

            PlayerResponseDTO(
                player = player,
                rankPosition = null,
                winRate = playerStats.winrate,
                headshotPercentage = playerStats.headshotPercentage,
                preferredWeapon = playerStats.preferredWeapon,
                mvpCount = playerStats.mvpCount.toLong(),
                aceCount = playerStats.aceCount.toLong(),
                knifeDeaths = playerStats.knifeKills.toLong(),
                versus = versus
            )
        } else {
            PlayerResponseDTO(player = player)
        }
    }

    private fun applySorting(players: List<Player>, sort: Sort): List<Player> {
        if (sort.isUnsorted) return players

        var sortedList = players
        sort.forEach { order ->
            val comparator = when (order.property) {
                "gameName" -> compareBy<Player> { it.gameName }
                "tagName" -> compareBy { it.tagName }
                "competitiveTier" -> compareBy { it.competitiveTier }
                "accountLevel" -> compareBy { it.accountLevel }
                "createdAt" -> compareBy { it.createdAt }
                "updatedAt" -> compareBy { it.updatedAt }
                "id", "puuid" -> compareBy { it.puuid }
                else -> compareBy { it.gameName } // fallback
            }

            sortedList = if (order.isAscending) {
                sortedList.sortedWith(comparator)
            } else {
                sortedList.sortedWith(comparator.reversed())
            }
        }

        return sortedList
    }
}
