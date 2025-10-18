package io.github.codenilson.lavava2026.domain.rounds

import io.github.codenilson.lavava2026.domain.players.Player
import io.github.codenilson.lavava2026.domain.matches.Match

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.JoinColumn

import java.util.UUID

@Entity
@Table(name = "rounds")
class Round (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,

    var roundNumber: Int,
    //TODO: VERIFY
    //var roundResult: String,
    //var winningTeam: String,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var match: Match? = null,

    @ManyToOne
    @JoinColumn(name = "bomb_planter_id") //TODO: PUUID
    var bombPlanter: Player? = null,

    @ManyToOne
    @JoinColumn(name = "bomb_defuser_id")
    var bombDefuser: Player? = null,

    @OneToMany(mappedBy = "round")
    val kills: MutableList<RoundKill> = mutableListOf(),

    // var roundResultCode?
)

// REPRESENTA CAMPOS RoundResultDto DA API DA RIOT