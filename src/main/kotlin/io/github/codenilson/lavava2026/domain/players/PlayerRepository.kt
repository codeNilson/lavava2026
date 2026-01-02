package io.github.codenilson.lavava2026.domain.players

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlayerRepository : JpaRepository<Player, UUID> {
    fun findByActive(active: Boolean?, sort: Sort): List<Player>

    @Query("SELECT DISTINCT p FROM Player p LEFT JOIN FETCH p.stats s WHERE (:season IS NULL OR s.season = :season)")
    fun findAllWithStatsForSeason(@Param("season") season: String?): List<Player>

    @Query("SELECT DISTINCT p FROM Player p LEFT JOIN FETCH p.stats s WHERE p.active = :active AND (:season IS NULL OR s.season = :season)")
    fun findByActiveWithStatsForSeason(@Param("active") active: Boolean, @Param("season") season: String?): List<Player>
}
