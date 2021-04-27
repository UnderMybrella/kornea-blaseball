package dev.brella.kornea.blaseball.base.common.beans

import com.soywiz.klock.DateTimeTz
import dev.brella.kornea.blaseball.base.common.*
import dev.brella.kornea.blaseball.base.common.json.BlaseballDateTimeSerialiser
import dev.brella.kornea.blaseball.base.common.json.CoercedDoubleSerialiser
import dev.brella.kornea.blaseball.base.common.json.CoercedIntSerialiser
import dev.brella.kornea.blaseball.base.common.json.RoundsGameSerialiser
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.Serializable

@Serializable
data class BlaseballStreamDataResponse(val value: BlaseballStreamData)

@Serializable
data class BlaseballStreamData(
    val games: BlaseballStreamDataGames? = null,
    val leagues: BlaseballStreamDataLeagues? = null,
    val temporal: BlaseballStreamDataTemporal? = null,
    val fights: BlaseballStreamDataFights? = null
)

@Serializable
data class BlaseballStreamDataGames(
    val sim: BlaseballStreamDataSim,
    val season: BlaseballStreamDataSeason,
    val standings: BlaseballStreamDataStandings,
    val schedule: List<BlaseballDatabaseGame>,
    val tomorrowSchedule: List<BlaseballDatabaseGame>,
    val postseason: BlaseballStreamDataPostseason
)

@Serializable
data class BlaseballStreamDataSim(
    val id: SimulationID,
    val day: Int,
    val league: LeagueID,
    val nextPhaseTime: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val phase: Int,
    val playOffRound: Int,
    val playoffs: PlayoffID,
    val rules: RulesID,
    val season: Int,
    val seasonId: SeasonID,
    val terminology: TerminologyID,
    val eraColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val eraTitle: String,
    val subEraColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val subEraTitle: String,
    val attr: List<SimulationAttributeID>,
    val agitations: Int,
    val salutations: Int,
    val tournament: Int,
    val tournamentRound: Int,
    val godsDayDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val preseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val earlseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val earlsiestaDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val midseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val latesiestaDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val lateseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val endseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val earlpostseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val latepostseasonDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
    val electionDate: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz
)

@Serializable
data class BlaseballStreamDataSeason(
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
data class BlaseballStreamDataStandings(
    val id: StandingsID,
    val losses: UUIDMap<TeamID, Double>,
    val wins: UUIDMap<TeamID, Double>,
    val runs: UUIDMap<TeamID, Double>,
    val gamesPlayed: UUIDMap<TeamID, Double>
)

@Serializable
data class BlaseballStreamDataPostseason(
    val playoffs: BlaseballStreamDataPostseasonPlayoffs? = null,
    val allRounds: List<BlaseballStreamDataPostseasonRound>? = null,
    val allMatchups: List<BlaseballStreamDataPostseasonMatchup>? = null,
    val round: BlaseballStreamDataPostseasonRound? = null,
    val matchups: List<BlaseballStreamDataPostseasonMatchup>? = null,
    val tomorrowRound: BlaseballStreamDataPostseasonRound? = null,
    val tomorrowMatchups: List<BlaseballStreamDataPostseasonMatchup>? = null
)

/*   */

@Serializable
data class BlaseballStreamDataPostseasonPlayoffs(
    val id: PlayoffID,
    val name: String,
    val numberOfRounds: Int,
    val playoffDay: Int,
    val rounds: List<PlayoffRoundID>,
    val season: Int,
    val tomorrowRound: Int,
    val winner: TeamID?,
    val tournament: Int
)

@Serializable
data class BlaseballStreamDataPostseasonRound(
    val id: PlayoffRoundID,
    val gameIndex: Int,
    val games: List<List<@Serializable(with = RoundsGameSerialiser::class) GameID?>>,
    val matchups: List<PlayoffMatchupID>,
    val name: String,
    val roundNumber: Int,
    val special: Boolean,
    val winnerSeeds: List<Double>,
    val winners: List<TeamID>
)

@Serializable
data class BlaseballStreamDataPostseasonMatchup(
    val id: PlayoffMatchupID,
    val name: String?,
    val awaySeed: Double?,
    val awayTeam: TeamID?,
    val awayWins: Int,
    val homeSeed: Double,
    val homeTeam: TeamID,
    val homeWins: Int,
    val gamesPlayed: Int,
    val gamesNeeded: String
)

/*   */

@Serializable
data class BlaseballStreamDataLeagues(
    val leagues: List<BlaseballLeague>,
    val stadiums: List<BlaseballStreamDataStadium>,
    val subleagues: List<BlaseballSubleague>,
    val divisions: List<BlaseballDivision>,
    val teams: List<BlaseballTeam>,
    val tiebreakers: List<BlaseballTiebreaker>,
    val stats: BlaseballStreamDataStats? = null
)

@Serializable
data class BlaseballStreamDataStadium(
    val id: StadiumID,
    val teamId: TeamID,
    val name: String,
    val nickname: String,
    val model: Int?,
    val mainColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val secondaryColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val tertiaryColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val ominousness: Double,
    val forwardness: Double,
    val obtuseness: Double,
    val grandiosity: Double,
    val fortification: Double,
    val elongation: Double,
    val inconvenience: Double,
    val viscosity: Double,
    val hype: Double,
    val mysticism: Double,
    val luxuriousness: Double,
    val filthiness: Double,
    val weather: JsonObject,
    val renoHand: JsonArray,
    val renoDiscard: JsonArray,
    val renoLog: JsonObject,
    val renoCost: Int,
    val mods: JsonArray,
    val birds: Int,
    val state: JsonObject
)

/*   */

@Serializable
data class BlaseballStreamDataTemporal(val doc: BlaseballStreamDataTemporalDoc)

@Serializable
data class BlaseballStreamDataTemporalDoc(val id: String, val alpha: Int, val beta: Int, val gamma: Int, val delta: Boolean, val epsilon: Boolean, val zeta: String, val eta: Int, val theta: String, val iota: Int)

@Serializable
data class BlaseballStreamDataFights(val bossFights: JsonArray)

@Serializable
data class BlaseballStreamDataStats(val communityChest: BlaseballCommunityChest)

@Serializable
data class BlaseballCommunityChest(
    val progress: @Serializable(CoercedDoubleSerialiser::class) Double,
    val chestsUnlocked: @Serializable(CoercedIntSerialiser::class) Int,
    val runs: @Serializable(CoercedDoubleSerialiser::class) Double
)