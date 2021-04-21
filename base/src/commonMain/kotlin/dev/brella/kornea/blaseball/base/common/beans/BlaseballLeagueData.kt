package dev.brella.kornea.blaseball.base.common.beans

import dev.brella.kornea.blaseball.*
import dev.brella.kornea.blaseball.base.common.beans.Colour
import dev.brella.kornea.blaseball.base.common.beans.ColourAsHexSerialiser
import dev.brella.kornea.blaseball.base.common.beans.unknown
import kotlinx.serialization.Serializable

@Serializable
data class BlaseballDivision(
    val id: DivisionID,
    val teams: List<TeamID>,
    val name: String
)

@Serializable
data class BlaseballLeague(
    val id: LeagueID,
    val subleagues: List<SubleagueID>,
    val name: String,
    val tiebreakers: String
)

@Serializable
data class BlaseballSubleague(
    val id: SubleagueID,
    val divisions: List<DivisionID>,
    val name: String
)

@Serializable
data class BlaseballTeam(
    val id: TeamID,
    val lineup: List<PlayerID>,
    val rotation: List<PlayerID>,
    val bullpen: List<PlayerID>,
    val bench: List<PlayerID>,
    val fullName: String,
    val location: String,
    val mainColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val nickname: String,
    val secondaryColor: @Serializable(ColourAsHexSerialiser::class) Colour,
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
    val weekAttr: List<ModificationID>,
    val gameAttr: List<ModificationID>,
    val seasAttr: List<ModificationID>,
    val permAttr: List<ModificationID>,
    val teamSpirit: Double,
    val card: Int,
    /*   */
    val tournamentWins: Int,
    val stadium: StadiumID?,
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
    val id: PlayoffMatchupID,
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
    val losses: UUIDMap<TeamID, Int>,
    val wins: UUIDMap<TeamID, Int>,
    val runs: UUIDMap<TeamID, Double> = emptyUUIDMap(),
    val gamesPlayed: UUIDMap<TeamID, Int> = emptyUUIDMap(),
)

@Serializable
data class BlaseballTiebreaker(
    val id: TiebreakerID,
    val order: List<TeamID>
)