package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.*
import dev.brella.kornea.blaseball.json.RoundsGameSerialiser
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

typealias unknown = Map<String, JsonElement>

@Serializable
data class BlaseballDatabasePlayer(
    val id: PlayerID,
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
//    val bat: String,
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
    val peanutAllergy: Boolean?,
    val cinnamon: Double?,
    val fate: Int?,
//    val armor: String,
    val ritual: String?,
    val coffee: Int?,
    val blood: Int?,
    val permAttr: List<ModificationID>,
    val seasAttr: List<ModificationID>,
    val weekAttr: List<ModificationID>,
    val gameAttr: List<ModificationID>,
    val hitStreak: Int,
    val consecutiveHits: Int,
    val baserunningRating: Double,
    val pitchingRating: Double,
    val hittingRating: Double,
    val defenseRating: Double,
    val leagueTeamId: TeamID?,
    val tournamentTeamId: String?,
    val eDensity: Double,
    val state: JsonObject,
    val evolution: Double,
    val items: List<BlaseballItem>,
    val itemAttr: List<ModificationID>
)

@Serializable
data class BlaseballDatabaseGame(
    val id: GameID,
    val basesOccupied: List<Int>,
    val baseRunners: List<PlayerID>,
    val baseRunnerNames: List<String>,
    val outcomes: List<String>,
    val terminology: TerminologyID,
    val lastUpdate: String,
    val rules: RulesID,
    val statsheet: GameStatsheetID,
    val awayPitcher: PlayerID?,
    val awayPitcherName: String?,
    val awayBatter: PlayerID?,
    val awayBatterName: String?,
    val awayTeam: TeamID,
    val awayTeamName: String,
    val awayTeamNickname: String,
    val awayTeamColor: @Serializable(ColourAsHexSerialiser::class) Colour,
    val awayTeamEmoji: String,
    val awayOdds: Double,
    val awayStrikes: Double,
    val awayScore: Double,
    val awayTeamBatterCount: Int,
    val homePitcher: PlayerID?,
    val homePitcherName: String,
    val homeBatter: PlayerID?,
    val homeBatterName: String?,
    val homeTeam: TeamID,
    val homeTeamName: String,
    val homeTeamNickname: String,
    val homeTeamColor: @Serializable(ColourAsHexSerialiser::class) Colour,
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
    val awayTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour?,
    val homeTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour?,
    val homeBalls: Int,
    val awayBalls: Int,
    val homeOuts: Int,
    val awayOuts: Int,
    val playCount: Int,
    val tournament: Int,
    val baseRunnerMods: List<ModificationID>,
    val homePitcherMod: ModificationID,
    val homeBatterMod: ModificationID,
    val awayPitcherMod: ModificationID,
    val awayBatterMod: ModificationID,
    val scoreUpdate: String,
    val scoreLedger: String,
    val stadiumId: StadiumID? = null,
    val secretBaserunner: JsonElement? = null,
    val topInningScore: Double? = null,
    val bottomInningScore: Double? = null,
    val newInningPhase: Int? = null,
    val gameStartPhase: Int? = null,
    val isTitleMatch: Boolean? = null
)

@Serializable
data class BlaseballDatabasePlayoffs(
    val id: PlayoffID,
    val name: String,
    val numberOfRounds: Int,
    val playoffDay: Int,
    val rounds: List<PlayoffRoundID>,
    val season: Int,
    val tomorrowRound: Int,
    val winner: String,
    val tournament: Int
)

@Serializable
data class BlaseballDatabasePlayoffRound(
    val id: PlayoffRoundID,
    val gameIndex: Int,
    val games: List<List<@Serializable(with = RoundsGameSerialiser::class) GameID?>>,
    val matchups: List<PlayoffMatchupID>,
    val name: String,
    val roundNumber: Int,
    val special: Boolean,
    val winnerSeeds: List<Double>,
    val winners: List<String>
)



@Serializable
data class BlaseballDatabaseBlessingResult(
    val id: BlessingID,
    val bonusId: String,
    val bonusTitle: String,
    val teamId: TeamID,
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
data class BlaseballDatabaseTidingResult(
    val id: TidingID,
    val msg: String
)

@Serializable
data class BlaseballDatabaseOffseasonRecap(
    val id: OffseasonRecapID,
    val bonusResults: List<BlessingID>,
    val decreeResults: List<DecreeID>,
    val name: String,
    val season: Int,
    val totalBonusVotes: Int,
    val totalDecreeVotes: Int,
    val voteCount: Int,
    val eventResults: List<TidingID>,
    val willResults: List<WillID>? = null,
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
    val id: DecreeID,
    val type: Int,
    val title: String,
    val description: String
)

@Serializable
data class BlaseballDatabaseOffseasonBlessing(
    val id: BlessingID,
    val type: Int,
    val value: Int,
    val title: String,
    val subheader: String? = null,
    val description: String
)

@Serializable
data class BlaseballDatabaseOffseasonWill(
    val id: WillID,
    val title: String,
    val description: String,
    val info: List<JsonObject>
)