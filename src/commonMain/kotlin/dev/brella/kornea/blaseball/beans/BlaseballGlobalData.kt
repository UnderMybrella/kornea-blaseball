package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.json.ItemAttrSerialiser
import kotlinx.serialization.Serializable

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
    val peanuts: Long
)

@Serializable
data class BlaseballGlobalEvent(
    val id: String,
    val msg: String,
    val expire: String? = null
)

@Serializable
data class BlaseballItem(
    val id: String,
    val name: String,
    val attr: @Serializable(with = ItemAttrSerialiser::class) String?
)

@Serializable
data class BlaseballMod(
    val id: String,
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
    val eraColor: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val eraTitle: String,
    val subEraColor: @Serializable(with = ColourAsHexSerialiser::class) Colour,
    val subEraTitle: String,
    val attr: List<String>,
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