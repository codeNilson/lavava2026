package io.github.codenilson.lavava2026.domain.players

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlayerRepository : JpaRepository<Player, UUID> {
    fun findByActive(active: Boolean?, sort: Sort): List<Player>
    fun findByPuuid(puuid: String): Player?
}
