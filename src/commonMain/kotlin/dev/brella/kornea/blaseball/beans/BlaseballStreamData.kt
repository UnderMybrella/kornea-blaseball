package dev.brella.kornea.blaseball.beans

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class BlaseballStreamDataResponse(val value: BlaseballStreamData)

@Serializable
data class BlaseballStreamData(val games: BlaseballStreamDataGames, val leagues: BlaseballStreamDataLeagues, val temporal: BlaseballStreamDataTemporal, val fights: BlaseballStreamDataFights)

@Serializable
data class BlaseballStreamDataGames(
    val sim: BlaseballStreamDataSim,
    val season: BlaseballStreamDataSeason,
    val standings: BlaseballStreamDataStandings,
    val schedule: List<BlaseballStreamDataSchedule>,
    val tomorrowSchedule: JsonArray,
    val postseason: BlaseballStreamDataPostseason
)

@Serializable
data class BlaseballStreamDataSim(
    val id: String,
    val day: Int,
    val league: String,
    val nextPhaseTime: String,
    val phase: Int,
    val playOffRound: Int,
    val playoffs: String,
    val rules: String,
    val season: Int,
    val seasonId: String,
    val terminology: String,
    val eraColor: String,
    val eraTitle: String,
    val subEraColor: String,
    val subEraTitle: String,
    val attr: Array<String>,
    val agitations: Int,
    val salutations: Int,
    val tournament: Int,
    val tournamentRound: Int,
    val godsDayDate: String,
    val preseasonDate: String,
    val earlseasonDate: String,
    val earlsiestaDate: String,
    val midseasonDate: String,
    val latesiestaDate: String,
    val lateseasonDate: String,
    val endseasonDate: String,
    val earlpostseasonDate: String,
    val latepostseasonDate: String,
    val electionDate: String
)

@Serializable
data class BlaseballStreamDataSeason(
    val id: String,
    val league: String,
    val rules: String,
    val schedule: String,
    val seasonNumber: Int,
    val standings: String,
    val stats: String,
    val terminology: String
)

@Serializable
data class BlaseballStreamDataStandings(
    val id: String,
    val losses: Map<String, Double>,
    val wins: Map<String, Double>,
    val runs: Map<String, Double>,
    val gamesPlayed: Map<String, Double>
)

@Serializable
data class BlaseballStreamDataSchedule(
    val id: String,
    val basesOccupied: JsonArray,
    val baseRunners: JsonArray,
    val baseRunnerNames: JsonArray,
    val outcomes: JsonArray,
    val terminology: String,
    val lastUpdate: String,
    val rules: String,
    val statsheet: String,
    val awayPitcher: String?,
    val awayPitcherName: String,
    val awayBatter: String?,
    val awayBatterName: String,
    val awayTeam: String,
    val awayTeamName: String,
    val awayTeamNickname: String,
    val awayTeamColor: String,
    val awayTeamEmoji: String,
    val awayOdds: Double,
    val awayStrikes: Double,
    val awayScore: Double,
    val awayTeamBatterCount: Int,
    val homePitcher: String?,
    val homePitcherName: String,
    val homeBatter: String?,
    val homeBatterName: String,
    val homeTeam: String,
    val homeTeamName: String,
    val homeTeamNickname: String,
    val homeTeamColor: String,
    val homeTeamEmoji: String,
    val homeOdds: Double,
    val homeStrikes: Double,
    val homeScore: Double,
    val homeTeamBatterCount: Int,
    val season: Int,
    val isPostseason: Boolean,
    val day: Int,
    val phase: Int,
    val gameComplete: Boolean,
    val finalized: Boolean,
    val gameStart: Boolean,
    val halfInningOuts: Double,
    val halfInningScore: Double,
    val inning: Double,
    val topOfInning: Boolean,
    val atBatBalls: Int,
    val atBatStrikes: Double,
    val seriesIndex: Int,
    val seriesLength: Int,
    val shame: Boolean,
    val weather: Int,
    val baserunnerCount: Int,
    val homeBases: Double,
    val awayBases: Double,
    val repeatCount: Int,
    val awayTeamSecondaryColor: String,
    val homeTeamSecondaryColor: String,
    val homeBalls: Int,
    val awayBalls: Int,
    val homeOuts: Int,
    val awayOuts: Int,
    val playCount: Int,
    val tournament: Int,
    val baseRunnerMods: JsonArray,
    val homePitcherMod: String,
    val homeBatterMod: String,
    val awayPitcherMod: String,
    val awayBatterMod: String,
    val scoreUpdate: String,
    val scoreLedger: String,
    val stadiumId: JsonElement?,
    val secretBaserunner: JsonElement?,
    val topInningScore: Double,
    val bottomInningScore: Double,
    val newInningPhase: Int,
    val gameStartPhase: Int,
    val isTitleMatch: Boolean
)

@Serializable
data class BlaseballStreamDataPostseason(
    val playoffs: BlaseballStreamDataPostseasonPlayoffs,
    val allRounds: List<BlaseballStreamDataPostseasonRound>,
    val allMatchups: List<BlaseballStreamDataPostseasonMatchup>,
    val round: BlaseballStreamDataPostseasonRound,
    val matchups: List<BlaseballStreamDataPostseasonMatchup>,
    val tomorrowRound: BlaseballStreamDataPostseasonRound,
    val tomorrowMatchups: List<BlaseballStreamDataPostseasonMatchup>
)

/*   */

@Serializable
data class BlaseballStreamDataPostseasonPlayoffs(
    val id: String,
    val name: String,
    val numberOfRounds: Int,
    val playoffDay: Int,
    val rounds: List<String>,
    val season: Int,
    val tomorrowRound: Int,
    val winner: String,
    val tournament: Int
)

@Serializable
data class BlaseballStreamDataPostseasonRound(
    val id: String,
    val gameIndex: Int,
    val games: List<List<String>>,
    val matchups: List<String>,
    val name: String,
    val roundNumber: Int,
    val special: Boolean,
    val winnerSeeds: List<Double>,
    val winners: List<String>
)

@Serializable
data class BlaseballStreamDataPostseasonMatchup(
    val id: String,
    val name: String?,
    val awaySeed: Double?,
    val awayTeam: String?,
    val awayWins: Int,
    val homeSeed: Double,
    val homeTeam: String,
    val homeWins: Int,
    val gamesPlayed: Int,
    val gamesNeeded: String
)

/*   */

@Serializable
data class BlaseballStreamDataLeagues(
    val leagues: List<BlaseballStreamDataLeague>,
    val stadiums: List<BlaseballStreamDataStadium>,
    val subleagues: List<BlaseballStreamDataSubleague>,
    val divisions: List<BlaseballStreamDataDivision>,
    val teams: List<BlaseballStreamDataTeam>,
    val tiebreakers: List<BlaseballStreamDataTiebreaker>
)

@Serializable
data class BlaseballStreamDataLeague(
    val id: String,
    val subleagues: List<String>,
    val name: String,
    val tiebreakers: String
)

@Serializable
data class BlaseballStreamDataStadium(
    val id: String,
    val teamId: String,
    val name: String,
    val nickname: String,
    val model: Int?,
    val mainColor: String,
    val secondaryColor: String,
    val tertiaryColor: String,
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

@Serializable
data class BlaseballStreamDataSubleague(
    val id: String,
    val divisions: List<String>,
    val name: String
)

@Serializable
data class BlaseballStreamDataDivision(
    val id: String,
    val teams: List<String>,
    val name: String
)

@Serializable
data class BlaseballStreamDataTeam(
    val id: String,
    val lineup: List<String>,
    val rotation: List<String>,
    val bullpen: List<String>,
    val bench: List<String>,
    val seasAttr: List<String>,
    val permAttr: List<String>,
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
    val weekAttr: JsonArray,
    val gameAttr: JsonArray,
    val rotationSlot: Int,
    val teamSpirit: Double,
    val card: Int,
    val tournamentWins: Int,
    val stadium: String?,
    val imPosition: Double,
    val eDensity: Double,
    val eVelocity: Double,
    val state: JsonObject,
    val evolution: Double,
    val winStreak: Double
)

@Serializable
data class BlaseballStreamDataTiebreaker(
    val id: String,
    val order: List<String>
)

/*   */

@Serializable
data class BlaseballStreamDataTemporal(val doc: BlaseballStreamDataTemporalDoc)

@Serializable
data class BlaseballStreamDataTemporalDoc(val id: String, val alpha: Int, val beta: Int, val gamma: Int, val delta: Boolean, val epsilon: Boolean, val zeta: String, val eta: Int, val theta: String, val iota: Int)

@Serializable
data class BlaseballStreamDataFights(val bossFights: JsonArray)