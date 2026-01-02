package io.github.codenilson.lavava2026.domain.rounds

import io.github.codenilson.lavava2026.domain.players.Player

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Transient

import java.util.UUID

@Entity
@Table(name = "round_kills")
class RoundKill(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID? = null,

    @ManyToOne @JoinColumn(name = "round_id") var round: Round? = null,

    @ManyToOne @JoinColumn(name = "killer_id") var killer: Player,

    @ManyToOne @JoinColumn(name = "victim_id") var victim: Player,

    var weapon: String?,

    var weaponType: String,

    @Transient var roundNum: Int,

)
