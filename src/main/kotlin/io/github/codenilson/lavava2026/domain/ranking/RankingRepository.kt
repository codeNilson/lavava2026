package io.github.codenilson.lavava2026.domain.ranking

import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.ranking.dto.PlayerRankingDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RankingRepository : JpaRepository<Player, UUID> {

    /**
     * Busca o ranking completo dos jogadores, calculado via SQL nativo.
     */
    @Query(
        value = """
            WITH
            -- CTE 1: Calcula partidas jogadas, vencidas e perdidas
            PlayerMatchStats AS (
                SELECT
                    p.player_id,
                    COUNT(p.match_id) AS matches_played,
                    COUNT(CASE WHEN t.won = TRUE THEN 1 ELSE NULL END) AS matches_won
                FROM
                    performances p
                JOIN
                    teams t ON p.team_id = t.id
                GROUP BY
                    p.player_id
            ),
            
            -- CTE 2: Descobre a pontuação MÁXIMA (score) para cada time em cada partida
            TeamMVPs AS (
                SELECT
                    match_id,
                    team_id,
                    MAX(score) as max_score
                FROM
                    performances
                GROUP BY
                    match_id, team_id
            ),
            
            -- CTE 3: Compara o score do jogador com o score máximo do time para contar MVPs
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
                    killer_id,
                    round_id,
                    COUNT(id) as kills_in_round
                FROM
                    round_kills
                GROUP BY
                    killer_id, round_id
                HAVING
                    COUNT(id) >= 5
            ),
            
            -- CTE 5: Conta o total de Aces por jogador
            PlayerAceStats AS (
                SELECT
                    killer_id as player_id,
                    COUNT(round_id) as ace_count
                FROM
                    PlayerRoundAces
                GROUP BY
                    killer_id
            ),
            
            -- CTE 6: Encontra partidas únicas onde um jogador matou com faca
            PlayerKnifeKillStats AS (
                SELECT DISTINCT
                    rk.killer_id as player_id,
                    r.match_id
                FROM
                    round_kills rk
                JOIN
                    rounds r ON rk.round_id = r.id
                WHERE
                    rk.weapon_type = 'Melee'
            ),
            
            -- CTE 7: Conta os pontos de faca (1 por partida)
            PlayerKnifePoints AS (
                SELECT
                    player_id,
                    COUNT(match_id) as knife_kill_points
                FROM
                    PlayerKnifeKillStats
                GROUP BY
                    player_id
            )
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
        nativeQuery = true // <-- Isto diz ao Spring: "Execute este SQL diretamente!"
    )
    fun getRankedPlayers(): List<PlayerRankingDTO>
}