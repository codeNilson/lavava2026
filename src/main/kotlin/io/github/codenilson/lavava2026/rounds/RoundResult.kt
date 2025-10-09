package io.github.codenilson.lavava2026.rounds

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn

import java.util.UUID

import io.github.codenilson.lavava2026.players.Player
import io.github.codenilson.lavava2026.matches.Match

@Entity
@Table(name = "round_results")
class RoundResult (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,

    var roundNum: Int,
    //var roundResult: String,
    //var winningTeam: String,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var match: Match,

    @ManyToOne
    @JoinColumn(name = "bomb_planter_player_id")
    var bombPlanter: Player? = null,

    @ManyToOne
    @JoinColumn(name = "bomb_defuser_player_id")
    var bombDefuser: Player? = null,

    // var roundResultCode?
)

// REPRESENTA CAMPOS RoundResultDto DA API DA RIOT