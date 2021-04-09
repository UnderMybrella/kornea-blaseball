package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.GameStatsheetID
import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.PlayerStatsheetID
import dev.brella.kornea.blaseball.SeasonStatsheetID
import dev.brella.kornea.blaseball.TeamID
import dev.brella.kornea.blaseball.TeamStatsheetID
import kotlinx.serialization.Serializable

@Serializable
data class BlaseballGameStatsheet(
    val id: GameStatsheetID,
    val homeTeamRunsByInning: List<Int>,
    val awayTeamRunsByInning: List<Int>,
    val awayTeamTotalBatters: Int,
    val homeTeamTotalBatters: Int,
    val awayTeamStats: TeamStatsheetID,
    val homeTeamStats: TeamStatsheetID
)

@Serializable
data class BlaseballTeamStatsheet(
    val id: TeamStatsheetID,
    val playerStats: List<PlayerStatsheetID>,
    val gamesPlayed: Int,
    val wins: Int,
    val losses: Int,
    val name: String,
    val teamId: TeamID
)

@Serializable
data class BlaseballPlayerStatsheet(
    val id: PlayerStatsheetID,
    val playerId: PlayerID,
    val teamId: TeamID,
    val team: String,
    val name: String,
    val atBats: Int,
    val caughtStealing: Int,
    val doubles: Int,
    val earnedRuns: Int,
    val groundIntoDp: Int,
    val hits: Int,
    val hitsAllowed: Int,
    val homeRuns: Int,
    val losses: Int,
    val outsRecorded: Int,
    val rbis: Int,
    val runs: Int,
    val stolenBases: Int,
    val strikeouts: Int,
    val struckouts: Int,
    val triples: Int,
    val walks: Int,
    val walksIssued: Int,
    val wins: Int,
    val hitByPitch: Int? = null,
    val hitBatters: Int? = null,
    val quadruples: Int? = null,
    val pitchesThrown: Int? = null
)

@Serializable
data class BlaseballSeasonStatsheet(
    val id: SeasonStatsheetID,
    val teamStats: List<TeamStatsheetID> = emptyList()
)