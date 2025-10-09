package io.github.codenilson.lavava2026.rounds

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.JoinColumn

import java.util.UUID

import io.github.codenilson.lavava2026.players.Player
import io.github.codenilson.lavava2026.matches.Match
import org.hibernate.mapping.OneToMany

@Entity
@Table(name = "rounds")
class Round (
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,

    var roundNumber: Int,
    //var roundResult: String,
    //var winningTeam: String,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var match: Match,

    @ManyToOne
    @JoinColumn(name = "bomb_planter_puuid") //TODO: PUUID
    var bombPlanter: Player? = null,

    @ManyToOne
    @JoinColumn(name = "bomb_defuser_puuid")
    var bombDefuser: Player? = null,

    @OneToMany(mappedBy = "roundResult")
    val kills: MutableList<RoundKill> = mutableListOf(),

    // var roundResultCode?
)

// REPRESENTA CAMPOS RoundResultDto DA API DA RIOT