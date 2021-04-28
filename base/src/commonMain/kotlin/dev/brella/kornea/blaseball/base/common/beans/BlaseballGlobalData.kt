package dev.brella.kornea.blaseball.base.common.beans

import com.soywiz.klock.DateTimeTz
import dev.brella.kornea.blaseball.*
import dev.brella.kornea.blaseball.base.common.*
import dev.brella.kornea.blaseball.base.common.json.BlaseballDateTimeSerialiser
import dev.brella.kornea.blaseball.base.common.json.ItemAttrSerialiser
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class BlaseballIdols(
    val idols: List<PlayerID>,
    val data: unknown
)

@Serializable
data class BlaseballSeasonSixIdol(
    val id: String,
    val total: Int,
    val playerId: PlayerID
)

@Serializable
data class BlaseballTribute(
    val playerId: PlayerID,
    var peanuts: Long
)

@Serializable
data class BlaseballGlobalEvent(
    val id: String,
    val msg: String,
    val expire: String? = null
)

@Serializable
@Deprecated("Items are changing!")
data class BlaseballOldItem(
    val id: String,
    val name: String,
    val attr: @Serializable(with = ItemAttrSerialiser::class) String?
)

@Serializable
data class BlaseballItem(
    val id: ItemID,
    val name: String,
    val forger: JsonElement?,
    val forgerName: String?,
    val prePrefix: BlaseballItemPrefix?,
    val prefixes: List<BlaseballItemPrefix>?,
    val postPrefix: BlaseballItemPrefix?,
    val root: BlaseballRootItem,
    val suffix: JsonElement?,
    val durability: Double,
    val health: Double,
    val baserunningRating: Double?,
    val pitchingRating: Double?,
    val hittingRating: Double?,
    val defenseRating: Double?
)

@Serializable
data class BlaseballRootItem(
    val name: String,
    val adjustments: List<BlaseballItemAdjustment>
)

@Serializable
data class BlaseballItemPrefix(
    val name: String,
    val adjustments: List<BlaseballItemAdjustment>
)

@Serializable(BlaseballItemAdjustmentSerialiser::class)
sealed class BlaseballItemAdjustment {
    abstract val type: Int
}

@Serializable
//TODO: Once https://github.com/Kotlin/kotlinx.serialization/pull/1408 has been merged into main, replace mod: String with ModificationID
data class BlaseballItemModAdjustment(val mod: String, override val type: Int): BlaseballItemAdjustment()

@Serializable
data class BlaseballItemStatAdjustment(val stat: Int, override val type: Int, val value: Double): BlaseballItemAdjustment()

@Serializable
data class BlaseballItemDurabilityAdjustment(override val type: Int, val value: Double): BlaseballItemAdjustment()

//data class BlaseballUnknownAdjustment()

object BlaseballItemAdjustmentSerialiser: JsonContentPolymorphicSerializer<BlaseballItemAdjustment>(BlaseballItemAdjustment::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out BlaseballItemAdjustment> =
        when(val type = ((element as? JsonObject)?.get("type") as? JsonPrimitive)?.intOrNull) {
            0 -> BlaseballItemModAdjustment.serializer()
            1 -> BlaseballItemStatAdjustment.serializer()
            3 -> BlaseballItemDurabilityAdjustment.serializer()
            else -> TODO("No support for $type! ${Json.encodeToString(element)}")
        }
}

@Serializable
data class BlaseballMod(
    val id: ModificationID,
    val color: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val background: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val textColor: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val title: String,
    val description: String
) {
    fun hoverText(): String = "$title ($description)"
}

@Serializable
data class BlaseballSimulationData(
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
    val eraColor: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val eraTitle: String,
    val subEraColor: @Serializable(with = ColourAsHexSerialiser::class) Colour,
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