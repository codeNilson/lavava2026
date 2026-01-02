package io.github.codenilson.lavava2026.domain.players

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.UUID

@Entity
@Table(
    name = "player_stats",
    uniqueConstraints = [UniqueConstraint(columnNames = ["player_puuid", "season"])],
)
class PlayerStats(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) 
    var id: UUID? = null,

    @ManyToOne @JoinColumn(name = "player_puuid", nullable = false) 
    var player: Player? = null,

    val season: String = "",
    val matchesPlayed: Int = 0,
    val matchesWon: Int = 0,
    val matchesLost: Int = 0,
    val winrate: Double = 0.0,
    val headshotPercentage: Double = 0.0,
    val preferredWeapon: String = "",
    val mvpCount: Int = 0,
    val aceCount: Int = 0,
    val knifeKills: Int = 0,
) {
    @OneToMany(mappedBy = "playerStats", cascade = [CascadeType.ALL], orphanRemoval = true)
    val versus: MutableList<Versus> = mutableListOf()
    
    constructor(
        player: Player,
        season: String,
        matchesPlayed: Int,
        matchesWon: Int,
        matchesLost: Int,
        winrate: Double,
        headshotPercentage: Double,
        preferredWeapon: String,
        mvpCount: Int,
        aceCount: Int,
        knifeKills: Int
    ) : this(
        null, player, season, matchesPlayed, matchesWon, matchesLost,
        winrate, headshotPercentage, preferredWeapon, mvpCount, aceCount, knifeKills
    )
}

@Entity
@Table(name = "versus_stats")
class Versus(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) 
    var id: UUID? = null,
    
    @ManyToOne @JoinColumn(name = "player_stats_id", nullable = false)
    var playerStats: PlayerStats? = null,
    
    val opponentPuuid: UUID = UUID.randomUUID(),
    val gameName: String = "",
    val tagName: String = "",
    val kills: Int = 0,
    val deaths: Int = 0,
) {
    constructor(
        playerStats: PlayerStats,
        opponentPuuid: UUID,
        gameName: String,
        tagName: String,
        kills: Int,
        deaths: Int
    ) : this(null, playerStats, opponentPuuid, gameName, tagName, kills, deaths)
}
