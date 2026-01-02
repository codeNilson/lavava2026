package io.github.codenilson.lavava2026.domain.players

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlayerStatsRepository : JpaRepository<PlayerStats, UUID> {

    fun findByPlayerAndSeason(player: Player, season: String): PlayerStats?

    fun findByPlayer_PuuidAndSeason(puuid: UUID, season: String): PlayerStats?
}
