package dev.brella.kornea.blaseball.chronicler

import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.GameStatsheetID
import dev.brella.kornea.blaseball.base.common.ModificationID
import dev.brella.kornea.blaseball.base.common.PlayerID
import dev.brella.kornea.blaseball.base.common.RulesID
import dev.brella.kornea.blaseball.base.common.StadiumID
import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.TerminologyID
import dev.brella.kornea.blaseball.base.common.beans.Colour
import dev.brella.kornea.blaseball.base.common.beans.ColourAsHexSerialiser
import dev.brella.kornea.blaseball.base.common.beans.ColourAsNullableHexSerialiser
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.kornea.errors.common.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNames

enum class EnumOrder(val order: String) {
    ASC("asc"),
    DESC("desc")
}

inline fun <T> unwrapData(wrapper: ChroniclerWrapper<T>): List<T> =
    wrapper.data


inline fun <T> unwrapResultData(wrapper: KorneaResult<ChroniclerWrapper<T>>): KorneaResult<List<T>> =
    wrapper.map { it.data }

@Serializable
class ChroniclerWrapper<T>(val data: List<T>)

@Serializable
data class ChroniclerGameWrapper(val gameId: GameID, val startTime: String, val endTime: String, val data: ChroniclerBlaseballGame)

@Serializable
data class ChroniclerBlaseballGame(
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
    val awayStrikes: Double? = null,
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
    val homeStrikes: Double? = null,
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
    val weather: Int? = null,
    val baserunnerCount: Int,
    val homeBases: Double,
    val awayBases: Double,
    val repeatCount: Int? = null,
    val awayTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour? = null,
    val homeTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour? = null,
    val homeBalls: Int? = null,
    val awayBalls: Int? = null,
    val homeOuts: Int? = null,
    val awayOuts: Int? = null,
    val playCount: Int? = null,
    val tournament: Int? = null,
    val baseRunnerMods: List<ModificationID>? = null,
    val homePitcherMod: ModificationID? = null,
    val homeBatterMod: ModificationID? = null,
    val awayPitcherMod: ModificationID? = null,
    val awayBatterMod: ModificationID? = null,
    val scoreUpdate: String? = null,
    val scoreLedger: String? = null,
    val stadiumId: StadiumID? = null,
    val secretBaserunner: JsonElement? = null,
    val topInningScore: Double? = null,
    val bottomInningScore: Double? = null,
    val newInningPhase: Int? = null,
    val gameStartPhase: Int? = null,
    val isTitleMatch: Boolean? = null,
    val queuedEvents: JsonArray? = null
)

@Serializable
data class ChroniclerUpdateResponse(val nextPage: String? = null, val data: List<ChroniclerUpdateWrapper>)

@Serializable
data class ChroniclerUpdateWrapper(val gameId: GameID, val timestamp: String, val hash: String, val data: ChroniclerBlaseballGameUpdate)

@Serializable
data class ChroniclerBlaseballGameUpdate constructor(
    @OptIn(ExperimentalSerializationApi::class)
    @JsonNames("id", "_id")
    val id: GameID,
    val basesOccupied: List<Int>,
    val baseRunners: List<PlayerID>,
    val baseRunnerNames: List<String>? = null,
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
    val awayStrikes: Double? = null,
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
    val homeStrikes: Double? = null,
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
    val homeBases: Double? = null,
    val awayBases: Double? = null,
    val repeatCount: Int? = null,
    val awayTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour? = null,
    val homeTeamSecondaryColor: @Serializable(ColourAsNullableHexSerialiser::class) Colour? = null,
    val homeBalls: Int? = null,
    val awayBalls: Int? = null,
    val homeOuts: Int? = null,
    val awayOuts: Int? = null,
    val playCount: Int? = null,
    val tournament: Int? = null,
    val baseRunnerMods: List<ModificationID>? = null,
    val homePitcherMod: ModificationID? = null,
    val homeBatterMod: ModificationID? = null,
    val awayPitcherMod: ModificationID? = null,
    val awayBatterMod: ModificationID? = null,
    val scoreUpdate: String? = null,
    val scoreLedger: String? = null,
    val stadiumId: StadiumID? = null,
    val secretBaserunner: JsonElement? = null,
    val topInningScore: Double? = null,
    val bottomInningScore: Double? = null,
    val newInningPhase: Int? = null,
    val gameStartPhase: Int? = null,
    val isTitleMatch: Boolean? = null,
    val queuedEvents: JsonArray? = null
)