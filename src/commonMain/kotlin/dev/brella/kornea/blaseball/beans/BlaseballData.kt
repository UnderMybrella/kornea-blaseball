package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.DecreeID
import dev.brella.kornea.blaseball.GameID
import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.TeamID
import dev.brella.kornea.blaseball.json.RoundsGameSerialiser
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

typealias unknown = Map<String, JsonElement>

@Serializable
data class BlaseballDatabasePlayer(
    val id: String,
    val anticapitalism: Double,
    val baseThirst: Double,
    val buoyancy: Double,
    val chasiness: Double,
    val coldness: Double,
    val continuation: Double,
    val divinity: Double,
    val groundFriction: Double,
    val indulgence: Double,
    val laserlikeness: Double,
    val martyrdom: Double,
    val moxie: Double,
    val musclitude: Double,
    val name: String,
    val bat: String,
    val omniscience: Double,
    val overpowerment: Double,
    val patheticism: Double,
    val ruthlessness: Double,
    val shakespearianism: Double,
    val suppression: Double,
    val tenaciousness: Double,
    val thwackability: Double,
    val tragicness: Double,
    val unthwackability: Double,
    val watchfulness: Double,
    val pressurization: Double,
    val totalFingers: Int,
    val soul: Int,
    val deceased: Boolean,
    val peanutAllergy: Boolean,
    val cinnamon: Double,
    val fate: Int,
    val armor: String,
    val ritual: String,
    val coffee: Int,
    val blood: Int,
    val permAttr: List<String>,
    val seasAttr: List<String>,
    val weekAttr: List<String>,
    val gameAttr: List<String>,
    val hitStreak: Int,
    val consecutiveHits: Int,
    val baserunningRating: Double,
    val pitchingRating: Double,
    val hittingRating: Double,
    val defenseRating: Double,
    val leagueTeamId: String?,
    val tournamentTeamId: String?,
    val eDensity: Double,
    val state: JsonObject,
    val evolution: Double
)

@Serializable
data class BlaseballDatabaseGame(
    val id: GameID,
    val basesOccupied: List<Int>,
    val baseRunners: List<String>,
    val baseRunnerNames: List<String>,
    val outcomes: List<String>,
    val terminology: String,
    val lastUpdate: String,
    val rules: String,
    val statsheet: String,
    val awayPitcher: String?,
    val awayPitcherName: String,
    val awayBatter: String?,
    val awayBatterName: String?,
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
    val homeBatterName: String?,
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
    val baseRunnerMods: List<String>,
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
data class BlaseballDatabasePlayoffs(
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
data class BlaseballDatabasePlayoffRound(
    val id: String,
    val gameIndex: Int,
    val games: List<List<@Serializable(with = RoundsGameSerialiser::class) GameID?>>,
    val matchups: List<String>,
    val name: String,
    val roundNumber: Int,
    val special: Boolean,
    val winnerSeeds: List<Double>,
    val winners: List<String>
)



@Serializable
data class BlaseballDatabaseBlessingResult(
    val id: String,
    val bonusId: String,
    val bonusTitle: String,
    val teamId: String,
    val totalVotes: Int,
    val description: String,
    val teamVotes: Int? = null,
    val highestTeam: String? = null,
    val highestTeamVotes: Int? = null
)

@Serializable
data class BlaseballDatabaseDecreeResult(
    val id: DecreeID,
    val decreeId: String,
    val decreeTitle: String,
    val description: String,
    val totalVotes: Int
)

@Serializable
data class BlaseballDatabaseEventResult(
    val id: String,
    val msg: String
)

@Serializable
data class BlaseballDatabaseOffseasonRecap(
    val id: String,
    val bonusResults: List<String>,
    val decreeResults: List<DecreeID>,
    val name: String,
    val season: Int,
    val totalBonusVotes: Int,
    val totalDecreeVotes: Int,
    val voteCount: Int,
    val eventResults: List<String>,
    val willResults: List<String>? = null,
    val totalWillVotes: Int? = null
)

@Serializable
data class BlaseballDatabaseOffseasonSetup(
    val decrees: List<BlaseballDatabaseOffseasonDecree>,
    val blessings: List<BlaseballDatabaseOffseasonBlessing>,
    val wills: List<BlaseballDatabaseOffseasonWill>,

    val decreesToPass: Int,
    val willsToPass: Int
)

@Serializable
data class BlaseballDatabaseOffseasonDecree(
    val id: String,
    val type: Int,
    val title: String,
    val description: String
)

@Serializable
data class BlaseballDatabaseOffseasonBlessing(
    val id: String,
    val type: Int,
    val value: Int,
    val title: String,
    val subheader: String? = null,
    val description: String
)

@Serializable
data class BlaseballDatabaseOffseasonWill(
    val id: String,
    val title: String,
    val description: String,
    val info: List<JsonObject>
)

@Serializable
data class BlaseballFeedEvent(
    val id: String,
    val playerTags: List<PlayerID>,
    val teamTags: List<TeamID>,
    val gameTags: List<GameID>,
    val metadata: unknown,
    val created: String,
    val season: Int,
    val tournament: Int,
    val type: Int,
    val day: Int,
    val phase: Int,
    val category: Int,
    val description: String
)