package io.github.codenilson.lavava2026.rounds

import io.github.codenilson.lavava2026.players.Player
import io.github.codenilson.lavava2026.rounds.Round

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

import java.util.UUID

@Entity
@Table(name = "round_kills")
class RoundKill(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: UUID,

        @ManyToOne @JoinColumn(name = "round_id")
        var round: Round,

        //TODO: id vs PUUID
        @ManyToOne @JoinColumn(name = "killer_id")
        var killer: Player,

        @ManyToOne @JoinColumn(name = "victim_id")
        var victim: Player,
)
