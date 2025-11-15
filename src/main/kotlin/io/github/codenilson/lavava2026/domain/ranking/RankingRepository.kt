package io.github.codenilson.lavava2026.domain.ranking

import io.github.codenilson.lavava2026.domain.ranking.dto.PlayerRankingDTO
import io.github.codenilson.lavava2026.domain.players.dto.PlayerVersusDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RankingRepository : JpaRepository<io.github.codenilson.lavava2026.domain.players.Player, UUID> {

    @Query(
        value = """
            WITH
            -- CTE 1: Calcula partidas jogadas, vencidas e perdidas (FILTRADO)
            PlayerMatchStats AS (
                SELECT
                    p.player_id,
                    COUNT(p.match_id) AS matches_played,
                    COUNT(CASE WHEN t.won = TRUE THEN 1 ELSE NULL END) AS matches_won
                FROM
                    performances p
                JOIN
                    teams t ON p.team_id = t.id
                JOIN
                    matches m ON p.match_id = m.id
                WHERE
                    m.season = :season
                GROUP BY
                    p.player_id
            ),
            
            -- CTE 2: Descobre a pontuação MÁXIMA (score) (FILTRADO)
            TeamMVPs AS (
                SELECT
                    p.match_id,
                    p.team_id,
                    MAX(p.score) as max_score
                FROM
                    performances p
                JOIN
                    matches m ON p.match_id = m.id
                WHERE
                    m.season = :season
                GROUP BY
                    p.match_id, p.team_id
            ),
            
            -- CTE 3: Conta MVPs (Baseado na CTE 2, portanto, FILTRADO)
            PlayerMVPStats AS (
                SELECT
                    p.player_id,
                    COUNT(p.id) as mvp_count
                FROM
                    performances p
                JOIN
                    TeamMVPs tmvp ON p.match_id = tmvp.match_id
                                   AND p.team_id = tmvp.team_id
                                   AND p.score = tmvp.max_score
                GROUP BY
                    p.player_id
            ),
            
            -- CTE 4: Encontra rounds com 5+ kills (Aces)
            PlayerRoundAces AS (
                SELECT
                    rk.killer_id,
                    rk.round_id,
                    COUNT(rk.id) as kills_in_round
                FROM
                    round_kills rk
                JOIN
                    rounds r ON rk.round_id = r.id    -- << NOVO JOIN
                JOIN
                    matches m ON r.match_id = m.id  -- << NOVO JOIN
                WHERE
                    m.season = :season                   -- << NOVO FILTRO
                GROUP BY
                    rk.killer_id, rk.round_id
                HAVING
                    COUNT(rk.id) >= 5
            ),
            
            -- CTE 5: Conta o total de Aces (Baseado na CTE 4, portanto, FILTRADO)
            PlayerAceStats AS (
                SELECT
                    killer_id as player_id,
                    COUNT(round_id) as ace_count
                FROM
                    PlayerRoundAces
                GROUP BY
                    killer_id
            ),
            
            -- CTE 6: Encontra partidas únicas com kill de faca (FILTRADO)
            PlayerKnifeKillStats AS (
                SELECT DISTINCT
                    rk.killer_id as player_id,
                    r.match_id
                FROM
                    round_kills rk
                JOIN
                    rounds r ON rk.round_id = r.id
                JOIN
                    matches m ON r.match_id = m.id
                WHERE
                    rk.weapon_type = 'Melee'
                    AND m.season = :season
            ),
            
            -- CTE 7: Conta os pontos de faca (Baseado na CTE 6, portanto, FILTRADO)
            PlayerKnifePoints AS (
                SELECT
                    player_id,
                    COUNT(match_id) as knife_kill_points
                FROM
                    PlayerKnifeKillStats
                GROUP BY
                    player_id
            )
            
            -- Query Final: Junta tudo
            SELECT
                CAST(p.puuid AS VARCHAR) as playerId,
                p.game_name as gameName,
                p.tag_name as tagName,
                COALESCE(ms.matches_played, 0) as matchesPlayed,
                COALESCE(ms.matches_won, 0) as matchesWon,
                (COALESCE(ms.matches_played, 0) - COALESCE(ms.matches_won, 0)) as matchesLost,
                ROUND(
                    CASE
                        WHEN COALESCE(ms.matches_played, 0) > 0
                        THEN (COALESCE(ms.matches_won, 0) * 100.0 / ms.matches_played)
                        ELSE 0.0
                    END,
                2) as winRate,
                COALESCE(mvp.mvp_count, 0) as mvpCount,
                COALESCE(ace.ace_count, 0) as aceCount,
                COALESCE(knife.knife_kill_points, 0) as knifeKillPoints,
                (COALESCE(ms.matches_won, 0) * 3) as pointsFromWins,
                ( (COALESCE(ms.matches_won, 0) * 3) +
                  COALESCE(mvp.mvp_count, 0) +
                  COALESCE(ace.ace_count, 0) +
                  COALESCE(knife.knife_kill_points, 0)
                ) as totalPoints
            FROM
                players p
            LEFT JOIN
                PlayerMatchStats ms ON p.puuid = ms.player_id
            LEFT JOIN
                PlayerMVPStats mvp ON p.puuid = mvp.player_id
            LEFT JOIN
                PlayerAceStats ace ON p.puuid = ace.player_id
            LEFT JOIN
                PlayerKnifePoints knife ON p.puuid = knife.player_id
            WHERE
                p.active = TRUE
            ORDER BY
                totalPoints DESC,
                mvpCount DESC,
                winRate DESC;
        """,
        nativeQuery = true
    )
    fun getRankedPlayers(@Param("season") season: String): List<PlayerRankingDTO>

    @Query(
        value = """
            SELECT COUNT(*)
            FROM round_kills rk
            JOIN rounds r ON rk.round_id = r.id
            JOIN matches m ON r.match_id = m.id
            WHERE rk.weapon_type = 'Melee'
              AND rk.killer_id = :playerId
              AND m.season = :season
        """,
        nativeQuery = true
    )
    fun countKnifeKills(@Param("playerId") playerId: UUID, @Param("season") season: String): Long

    @Query(
        value = """
            WITH kill_events AS (
                SELECT rk.killer_id AS actor, rk.victim_id AS opponent, 1 AS killed, 0 AS died
                FROM round_kills rk
                JOIN rounds r ON rk.round_id = r.id
                JOIN matches m ON r.match_id = m.id
                WHERE m.season = :season
                UNION ALL
                SELECT rk.victim_id AS actor, rk.killer_id AS opponent, 0 AS killed, 1 AS died
                FROM round_kills rk
                JOIN rounds r ON rk.round_id = r.id
                JOIN matches m ON r.match_id = m.id
                WHERE m.season = :season
            )
            SELECT
                CAST(opponent.puuid AS VARCHAR) AS opponentId,
                opponent.game_name AS gameName,
                opponent.tag_name AS tagName,
                SUM(k.killed) AS killed,
                SUM(k.died) AS died
            FROM kill_events k
            JOIN players opponent ON opponent.puuid = k.opponent
            WHERE k.actor = :playerId
            GROUP BY opponent.puuid, opponent.game_name, opponent.tag_name
            ORDER BY killed DESC, died ASC
        """,
        nativeQuery = true
    )
    fun getVersus(@Param("playerId") playerId: UUID, @Param("season") season: String): List<Array<Any>>

    @Query(
        value = """
            SELECT position
            FROM (
                SELECT p.puuid, ROW_NUMBER() OVER (
                    ORDER BY (
                        (COALESCE(ms.matches_won, 0) * 3) +
                        COALESCE(mvp.mvp_count, 0) +
                        COALESCE(ace.ace_count, 0) +
                        COALESCE(knife.knife_kill_points, 0)
                    ) DESC,
                    COALESCE(mvp.mvp_count, 0) DESC,
                    CASE WHEN COALESCE(ms.matches_played, 0) > 0 THEN (COALESCE(ms.matches_won, 0) * 100.0 / ms.matches_played) ELSE 0.0 END DESC
                ) AS position
                FROM players p
                LEFT JOIN (
                    SELECT p.player_id, COUNT(p.match_id) AS matches_played, COUNT(CASE WHEN t.won = TRUE THEN 1 ELSE NULL END) AS matches_won
                    FROM performances p
                    JOIN teams t ON p.team_id = t.id
                    JOIN matches m ON p.match_id = m.id
                    WHERE m.season = :season
                    GROUP BY p.player_id
                ) ms ON p.puuid = ms.player_id
                LEFT JOIN (
                    SELECT p.player_id, COUNT(p.id) AS mvp_count
                    FROM performances p
                    JOIN (
                        SELECT p.match_id, p.team_id, MAX(p.score) AS max_score
                        FROM performances p
                        JOIN matches m ON p.match_id = m.id
                        WHERE m.season = :season
                        GROUP BY p.match_id, p.team_id
                    ) tmvp ON p.match_id = tmvp.match_id AND p.team_id = tmvp.team_id AND p.score = tmvp.max_score
                    GROUP BY p.player_id
                ) mvp ON p.puuid = mvp.player_id
                LEFT JOIN (
                    SELECT killer_id AS player_id, COUNT(round_id) AS ace_count
                    FROM (
                        SELECT rk.killer_id, rk.round_id
                        FROM round_kills rk
                        JOIN rounds r ON rk.round_id = r.id
                        JOIN matches m ON r.match_id = m.id
                        WHERE m.season = :season
                        GROUP BY rk.killer_id, rk.round_id
                        HAVING COUNT(rk.id) >= 5
                    ) x
                    GROUP BY killer_id
                ) ace ON p.puuid = ace.player_id
                LEFT JOIN (
                    SELECT player_id, COUNT(match_id) AS knife_kill_points
                    FROM (
                        SELECT DISTINCT rk.killer_id AS player_id, r.match_id
                        FROM round_kills rk
                        JOIN rounds r ON rk.round_id = r.id
                        JOIN matches m ON r.match_id = m.id
                        WHERE rk.weapon_type = 'Melee' AND m.season = :season
                    ) y
                    GROUP BY player_id
                ) knife ON p.puuid = knife.player_id
                WHERE p.active = TRUE
            ) ranked
            WHERE puuid = :playerId
        """,
        nativeQuery = true
    )
    fun getRankPosition(@Param("playerId") playerId: UUID, @Param("season") season: String): Int?

    @Query(
        value = """
            WITH TeamMVPs AS (
                SELECT p.match_id, p.team_id, MAX(p.score) AS max_score
                FROM performances p
                JOIN matches m ON p.match_id = m.id
                WHERE m.season = :season
                GROUP BY p.match_id, p.team_id
            )
            SELECT COALESCE(COUNT(p.id), 0)
            FROM performances p
            JOIN TeamMVPs tmvp ON p.match_id = tmvp.match_id AND p.team_id = tmvp.team_id AND p.score = tmvp.max_score
            WHERE p.player_id = :playerId
        """,
        nativeQuery = true
    )
    fun getMvpCountForPlayer(@Param("playerId") playerId: UUID, @Param("season") season: String): Long

    @Query(
        value = """
            SELECT COALESCE(COUNT(*), 0)
            FROM (
                SELECT rk.killer_id, rk.round_id
                FROM round_kills rk
                JOIN rounds r ON rk.round_id = r.id
                JOIN matches m ON r.match_id = m.id
                WHERE m.season = :season
                GROUP BY rk.killer_id, rk.round_id
                HAVING COUNT(rk.id) >= 5
            ) aces
            WHERE aces.killer_id = :playerId
        """,
        nativeQuery = true
    )
    fun getAceCountForPlayer(@Param("playerId") playerId: UUID, @Param("season") season: String): Long

    @Query(
        value = """
            SELECT ROUND(
                CASE WHEN agg.total_matches > 0 THEN (agg.matches_won * 100.0 / agg.total_matches) ELSE 0.0 END
            , 2) AS win_rate
            FROM (
                SELECT COUNT(p.match_id) AS total_matches,
                       COUNT(CASE WHEN t.won = TRUE THEN 1 ELSE NULL END) AS matches_won
                FROM performances p
                JOIN teams t ON p.team_id = t.id
                JOIN matches m ON p.match_id = m.id
                WHERE p.player_id = :playerId AND m.season = :season
            ) agg
        """,
        nativeQuery = true
    )
    fun getWinRateForPlayer(@Param("playerId") playerId: UUID, @Param("season") season: String): Double?
}