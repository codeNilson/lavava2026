package io.github.codenilson.lavava2026.application.services

import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.players.PlayerStats
import io.github.codenilson.lavava2026.domain.players.PlayerStatsRepository
import io.github.codenilson.lavava2026.domain.players.Versus
import io.github.codenilson.lavava2026.domain.ranking.RankingRepository
import io.github.codenilson.lavava2026.domain.performances.PerformanceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Year
import java.util.*

@Service
class PlayerStatsService(
    private val playerStatsRepository: PlayerStatsRepository,
    private val rankingRepository: RankingRepository,
    private val performanceRepository: PerformanceRepository
) {

    private val CURRENT_SEASON = Year.now().toString()

    @Transactional
    fun updatePlayerStats(player: Player, season: String = CURRENT_SEASON) {
        val existingStats = playerStatsRepository.findByPlayerAndSeason(player, season)
        existingStats?.let { playerStatsRepository.delete(it) }

        val matchesPlayed = rankingRepository.getMatchesPlayedForPlayer(player.puuid, season).toInt()
        val matchesWon = rankingRepository.getMatchesWonForPlayer(player.puuid, season).toInt()
        val matchesLost = rankingRepository.getMatchesLostForPlayer(player.puuid, season).toInt()
        val winRate = rankingRepository.getWinRateForPlayer(player.puuid, season) ?: 0.0
        val headshotTotals = performanceRepository.getHeadshotTotals(player.puuid, season)
        val headshotPercentage = if (headshotTotals == null || headshotTotals.getTotalShots() == 0L) 0.0 else {
            kotlin.math.round((headshotTotals.getHeadshots().toDouble() / headshotTotals.getTotalShots()) * 10000) / 100.0
        }
        val preferredWeapon = rankingRepository.getPreferredWeapon(player.puuid, season) ?: ""
        val mvpCount = rankingRepository.getMvpCountForPlayer(player.puuid, season).toInt()
        val aceCount = rankingRepository.getAceCountForPlayer(player.puuid, season).toInt()
        val knifeKills = rankingRepository.countKnifeKills(player.puuid, season).toInt()

        val newStats = PlayerStats(
            player = player,
            season = season,
            matchesPlayed = matchesPlayed,
            matchesWon = matchesWon,
            matchesLost = matchesLost,
            winrate = winRate,
            headshotPercentage = headshotPercentage,
            preferredWeapon = preferredWeapon,
            mvpCount = mvpCount,
            aceCount = aceCount,
            knifeKills = knifeKills
        )

        val savedStats = playerStatsRepository.save(newStats)

        updateVersusStats(savedStats, season)
    }

    private fun updateVersusStats(savedStats: PlayerStats, season: String) {
        savedStats.versus.clear()

        val versusRaw = rankingRepository.getVersus(savedStats.player?.puuid ?: return, season)

        val versusStats = versusRaw.mapNotNull { row ->
            try {
                val oppPuuid = UUID.fromString(row[0] as String)
                val gameName = row[1] as String
                val tagName = row[2] as String
                val kills = (row[3] as Number).toInt()
                val deaths = (row[4] as Number).toInt()

                Versus(
                    playerStats = savedStats,
                    opponentPuuid = oppPuuid,
                    gameName = gameName,
                    tagName = tagName,
                    kills = kills,
                    deaths = deaths
                )
            } catch (_: Exception) {
                null
            }
        }

        savedStats.versus.addAll(versusStats)
    }

    fun findByPlayerAndSeason(player: Player, season: String): PlayerStats? {
        return playerStatsRepository.findByPlayerAndSeason(player, season)
    }
}

