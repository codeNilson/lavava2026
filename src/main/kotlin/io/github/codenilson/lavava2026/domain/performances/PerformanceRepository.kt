package io.github.codenilson.lavava2026.domain.performances

import io.github.codenilson.lavava2026.domain.performances.dto.HeadshotTotalsProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface PerformanceRepository: JpaRepository<Performance, UUID> {
    @Query(
        value = """
            SELECT
                COALESCE(SUM(p.headshots), 0) AS headshots,
                COALESCE(SUM(p.headshots + p.legshots + p.bodyshots), 0) AS totalShots
            FROM performances p
            JOIN matches m ON p.match_id = m.id
            WHERE p.player_id = :playerId AND m.season = :season
        """,
        nativeQuery = true
    )
    fun getHeadshotTotals(
        @Param("playerId") playerId: UUID,
        @Param("season") season: String
    ): HeadshotTotalsProjection?
}
