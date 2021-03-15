package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class BlaseballDivision(
    val id: DivisionID,
    val teams: List<TeamID>,
    val name: String
)

@Serializable
data class BlaseballLeague(
    val id: String,
    val subleagues: List<String>,
    val name: String,
    val tiebreakers: String
)

@Serializable
data class BlaseballSubleague(
    val id: String,
    val divisions: List<String>,
    val name: String
)

@Serializable
data class BlaseballTeam(
    val id: String,
    val lineup: List<String>,
    val rotation: List<String>,
    val bullpen: List<String>,
    val bench: List<String>,
    val fullName: String,
    val location: String,
    val mainColor: String,
    val nickname: String,
    val secondaryColor: String,
    val shorthand: String,
    val emoji: String,
    val slogan: String,
    val shameRuns: Double,
    val totalShames: Int,
    val totalShamings: Int,
    val seasonShames: Int,
    val seasonShamings: Int,
    val championships: Int,
    val rotationSlot: Int,
    val weekAttr: List<String>,
    val gameAttr: List<String>,
    val seasAttr: List<String>,
    val permAttr: List<String>,
    val teamSpirit: Double,
    val card: Int,
    /*   */
    val tournamentWins: Int,
    val stadium: String?,
    val imPosition: Double? = null,
    val eDensity: Double? = null,
    val eVelocity: Double? = null,
    val state: unknown,
    val evolution: Double,
    val winStreak: Double,
    val level: Int?
)

@Serializable
data class BlaseballPlayoffMatchup(
    val id: MatchupID,
    val name: String?,
    val awaySeed: Int?,
    val awayTeam: TeamID?,
    val awayWins: Int,
    val homeSeed: Int?,
    val homeTeam: TeamID?,
    val homeWins: Int,
    val gamesPlayed: Int,
    val gamesNeeded: String
)

@Serializable
data class BlaseballSeason(
    val id: SeasonID,
    val league: LeagueID,
    val rules: RulesID,
    val schedule: ScheduleID,
    val seasonNumber: Int,
    val standings: StandingsID,
    val stats: SeasonStatsheetID,
    val terminology: TerminologyID
)

@Serializable
data class BlaseballStandings(
    val id: StandingsID,
    val losses: Map<TeamID, Int>,
    val wins: Map<TeamID, Int>,
    val runs: Map<TeamID, Double> = emptyMap(),
    val gamesPlayed: Map<TeamID, Int> = emptyMap(),
)

@Serializable
data class BlaseballTiebreaker(
    val id: TiebreakerID,
    val order: List<TeamID>
)