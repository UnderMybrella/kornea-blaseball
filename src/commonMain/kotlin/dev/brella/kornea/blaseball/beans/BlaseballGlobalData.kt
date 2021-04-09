package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.LeagueID
import dev.brella.kornea.blaseball.ModificationID
import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.PlayoffID
import dev.brella.kornea.blaseball.RulesID
import dev.brella.kornea.blaseball.SeasonID
import dev.brella.kornea.blaseball.SimulationAttributeID
import dev.brella.kornea.blaseball.SimulationID
import dev.brella.kornea.blaseball.TerminologyID
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
@Deprecated("Items are changing!")
data class BlaseballItem(
    val id: String,
    val name: String,
    val attr: @Serializable(with = ItemAttrSerialiser::class) String?
)

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
    val nextPhaseTime: String,
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