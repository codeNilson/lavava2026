package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.application.exceptions.ResourceNotFoundException
import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerRepository
import io.github.codenilson.lavava2026.domain.players.dto.PlayerResponseDTO
import io.github.codenilson.lavava2026.domain.players.dto.PlayerVersusDTO
import io.github.codenilson.lavava2026.domain.ranking.RankingRepository
import io.github.codenilson.lavava2026.domain.performances.PerformanceRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Year
import java.util.UUID

@Service
class PlayerService(
    private val playerRepository: PlayerRepository,
    private val rankingRepository: RankingRepository,
    private val performanceRepository: PerformanceRepository
) {

    private val CURRENT_SEASON = Year.now().toString()

    fun save(player: Player): Player {
        return playerRepository.save(player)
    }

    fun saveAll(players: List<Player>): List<Player> {
        return playerRepository.saveAll(players)
    }

    fun findAll(active: Boolean? = null, sort: Sort, season: String? = null): List<PlayerResponseDTO> {
        val players = if (active == null) {
            playerRepository.findAll(sort)
        } else {
            playerRepository.findByActive(active, sort)
        }
        val effectiveSeason = season ?: CURRENT_SEASON
        return players.map { enrichWithStats(it, effectiveSeason) }
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
        val rankPosition = rankingRepository.getRankPosition(player.puuid, season)
        val knifeDeaths = rankingRepository.countKnifeKills(player.puuid, season)
        val preferredWeapon = rankingRepository.getPreferredWeapon(player.puuid, season)
        val headshotTotals = performanceRepository.getHeadshotTotals(player.puuid, season)
        val headshotPercentage = if (headshotTotals == null || headshotTotals.getTotalShots() == 0L) 0.0 else {
            kotlin.math.round((headshotTotals.getHeadshots().toDouble() / headshotTotals.getTotalShots()) * 10000) / 100.0
        }

        val versusRaw = rankingRepository.getVersus(player.puuid, season)
        val versus = versusRaw.mapNotNull { row ->
            try {
                val oppId = UUID.fromString(row[0] as String)
                val gameName = row[1] as String
                val tagName = row[2] as String
                val killed = (row[3] as Number).toLong()
                val died = (row[4] as Number).toLong()
                PlayerVersusDTO(oppId, gameName, tagName, killed, died)
            } catch (_: Exception) {
                null
            }
        }

        val mvpCount = rankingRepository.getMvpCountForPlayer(player.puuid, season)
        val aceCount = rankingRepository.getAceCountForPlayer(player.puuid, season)
        val winRate = rankingRepository.getWinRateForPlayer(player.puuid, season)

        return PlayerResponseDTO(
            player = player,
            rankPosition = rankPosition,
            winRate = winRate,
            headshotPercentage = headshotPercentage,
            preferredWeapon = preferredWeapon,
            mvpCount = mvpCount,
            aceCount = aceCount,
            knifeDeaths = knifeDeaths,
            versus = versus
        )
    }
}
