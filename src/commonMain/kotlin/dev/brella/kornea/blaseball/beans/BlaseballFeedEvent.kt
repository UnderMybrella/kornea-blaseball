package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

@Serializable
sealed class BlaseballFeedEvent<T> {
    abstract val id: FeedID
    abstract val playerTags: List<PlayerID>
    abstract val teamTags: List<TeamID>
    abstract val gameTags: List<GameID>
    abstract val metadata: T
    abstract val created: String
    abstract val season: Int
    abstract val tournament: Int
    abstract val type: Int
    abstract val day: Int
    abstract val phase: Int
    abstract val category: Int
    abstract val description: String
    abstract val nuts: Int

    @Serializable
    data class Unknown(
        override val id: FeedID,
        override val playerTags: List<PlayerID>,
        override val teamTags: List<TeamID>,
        override val gameTags: List<GameID>,
        override val created: String,
        override val season: Int,
        override val tournament: Int,
        override val type: Int,
        override val day: Int,
        override val phase: Int,
        override val category: Int,
        override val description: String,
        override val nuts: Int,
        override val metadata: JsonObject
    ) : BlaseballFeedEvent<JsonObject>()

    class Builder {
        var id: FeedID? = null
        var playerTags: List<PlayerID>? = null
        var teamTags: List<TeamID>? = null
        var gameTags: List<GameID>? = null
        var created: String? = null
        var season: Int? = null
        var tournament: Int? = null
        var type: Int? = null
        var day: Int? = null
        var phase: Int? = null
        var category: Int? = null
        var description: String? = null
        var nuts: Int? = null
    }
}

object BlaseballFeedMetadata {
    interface WithPlay {
        val play: Int?
        val subPlay: Int?

        interface AlwaysPresent : WithPlay {
            override val play: Int
            override val subPlay: Int

            @Serializable
            data class Instance(override val play: Int, override val subPlay: Int) : AlwaysPresent
        }

        @Serializable
        data class Instance(override val play: Int? = null, override val subPlay: Int? = null) : WithPlay
    }

    interface WithChildren {
        val children: List<FeedID>?

        interface AlwaysPresent : WithChildren {
            override val children: List<FeedID>
        }
    }

    interface WithParent {
        val parent: FeedID?

        interface AlwaysPresent : WithParent {
            override val parent: FeedID
        }
    }

    class AddedInGameModifier(override val play: Int? = null, override val subPlay: Int?, val parent: FeedID? = null, val mod: ModificationID, val type: Int) : WithPlay
    class AddedModifierDueToAnotherModifier(override val play: Int, override val subPlay: Int, val mod: ModificationID, val type: Int, val parent: FeedID, val source: ModificationID) : WithPlay.AlwaysPresent
    class AllergicReaction(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class BirdsFlavourText(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class BirdsFreeShelledPlayer(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class BlackHoleInGame(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class BlessingWon(val id: BlessingID, val title: String, val votes: Int, val teamId: TeamID, override val children: List<FeedID>) : WithChildren.AlwaysPresent
    class BloodDrain(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class ConsumersAttack(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class DecreeNarration(override val parent: FeedID) : WithParent.AlwaysPresent
    class DecreePassed(val id: DecreeID, val title: String, val votes: Int, override val children: List<FeedID>, val totalVotes: Int) : WithChildren.AlwaysPresent

    class EarlBirds(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class EchoChamberTrapsWave(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class EchoFades(override val play: Int? = null, override val subPlay: Int? = null, val source: ModificationID, val removes: List<EchoFadeRemoved>) : WithPlay {
        data class EchoFadeRemoved(val mod: ModificationID, val type: Int)
    }

    class EchoPlayerReceiver(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class EchoPlayerStatic(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class EchoPlayerStaticChild(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val teamId: TeamID,
        val playerId: PlayerID,
        val teamName: String,
        val playerName: String
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class EndOfInning(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren

    class Feedback(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class FeedbackBlocked(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class FinalStandings(val place: Int)
    class FireEating(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class FlagPlanted(val title: String, val votes: Int, val renoId: String)
    class Flooding(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Flyout(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class FoulBall(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class FreeRefill(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class GameEndLog(override val play: Int, override val subPlay: Int, val winner: TeamID, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class GroundOut(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren

    class HalfInning(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class Hit(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class HomeRun(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Homesick(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class Incineration(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren

    class LateToTheParty(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class LetsGo(val home: TeamID, val away: TeamID, override val play: Int, override val subPlay: Int, val stadium: StadiumID, val weather: Int) : WithPlay.AlwaysPresent
    class LineupOrganised(override val parent: FeedID) : WithParent.AlwaysPresent

    class MildPitch(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class ModifierExpires(val mods: List<ModificationID>, val type: Int)
    class MurderOfCrows(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class NecromancyNarration(override val parent: FeedID) : WithParent.AlwaysPresent
    class NewPlayer(val id: PlayerID)
    class NewPlayerAfterIncineration(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val teamId: TeamID,
        val location: Int,
        val teamName: String,
        val inPlayerId: PlayerID,
        val outPlayerId: PlayerID,
        val inPlayerName: String,
        val outPlayerName: String
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class NewTeam(val teamId: TeamID, val teamName: String, val divisionID: DivisionID, val divisionName: String)

    class OverUnder(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class Partying(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PeanutAllergyCured(override val parent: FeedID) : WithParent.AlwaysPresent
    class PeanutMister(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class PeanutFlavourText(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class Perk(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PitcherChange(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class PlateAppearance(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Play(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class PlayerBecomesAlternate(val type: Int, val before: Double, val after: Double, override val parent: FeedID) : WithParent.AlwaysPresent
    class PlayerChangingPosition(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID,
        val teamId: TeamID,
        val teamName: String,
        val aLocation: Int,
        val aPlayerId: PlayerID,
        val bLocation: Int,
        val bPlayerId: PlayerID,
        val aPlayerName: String,
        val bPlayerName: String
    ) : WithPlay, WithParent.AlwaysPresent

    class PlayerDroppedItem(
        override val play: Int,
        override val subPlay: Int,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        override val parent: FeedID,
        val playerRating: Double,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class PlayerEchoed(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PlayerEntersSecretBase(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class PlayerExitsSecretBase(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class PlayerEntersCrimeScene(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PlayerEntersHallOfFame(override val play: Int, override val subPlay: Int, override val parent: FeedID) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent
    class PlayerExitsHallOfFame(override val parent: FeedID) : WithParent.AlwaysPresent
    class PlayerEvolves(override val parent: FeedID) : WithParent.AlwaysPresent
    class PlayerGainedBloodType(override val parent: FeedID) : WithParent.AlwaysPresent
    class PlayerGainedItem(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        override val parent: FeedID,
        val playerRating: Double,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay, WithParent.AlwaysPresent

    class PlayerHatched(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val id: PlayerID
    ) : WithPlay, WithParent

    class PlayerHitsAnotherWithPitch(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PlayerHopsOnGrindRail(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class PlayerJoiningTeam(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val location: Int,
        val playerId: PlayerID,
        val playerName: String,
        val sendTeamId: TeamID,
        val sendTeamName: String,
        val receiveTeamId: TeamID,
        val receiveTeamName: String,
        val receiveLocation: Int
    ) : WithPlay, WithParent

    class PlayerOpensCrate(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class PlayerRecruited(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val teamId: TeamID,
        val teamName: String,
        val location: Int,
        val playerId: PlayerID,
        val playerName: String
    ) : WithPlay, WithParent

    class PlayerRemovedAndReplacedFromShadows(
        val teamId: TeamID,
        val teamName: String,
        val removeLocation: Int,
        val removePlayerId: PlayerID,
        val removePlayerName: String,
        val promoteLocation: Int,
        val promotePlayerId: PlayerID,
        val promotePlayerName: String
    )

    class PlayerReroll(
        val type: Int,
        val before: Double,
        val after: Double,
        override val parent: FeedID
    ) : WithParent.AlwaysPresent

    class PlayersItemBroke(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class PlayersItemDamaged(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class PlayersItemRepaired(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class PlayersItemRestored(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class PlayerSentToShadowsAndReplaced(
        override val parent: FeedID,
        val teamId: TeamID,
        val teamName: String,
        val addLocation: Int,
        val addPlayerId: PlayerID,
        val addPlayerName: String,
        val retreatLocation: Int,
        val retreatPlayerId: PlayerID,
        val retreatPlayerName: String
    ) : WithParent.AlwaysPresent

    class PlayerSkippedShelledOrElsewhere(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class PlayerStatDecrease(override val play: Int? = null, override val subPlay: Int? = null, val type: Int, val before: Double, val after: Double, override val parent: FeedID? = null) : WithPlay, WithParent
    class PlayerStatDecreaseByPercent(override val parent: FeedID? = null, val before: Double, val after: Double, val type: Int) : WithParent
    class PlayerStatIncrease(override val play: Int? = null, override val subPlay: Int? = null, val type: Int, val before: Double, val after: Double, override val parent: FeedID? = null) : WithPlay, WithParent
    class PlayerStatIncreaseByPercent(override val parent: FeedID? = null, val before: Double, val after: Double, val type: Int) : WithParent
    class PlayerTrade(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID,
        val aTeamId: TeamID,
        val aTeamName: String,
        val bTeamId: TeamID,
        val bTeamName: String,
        val aLocation: Int,
        val bLocation: Int,
        val aPlayerId: PlayerID,
        val aPlayerName: String,
        val bPlayerId: PlayerID,
        val bPlayerName: String
    ) : WithPlay, WithParent.AlwaysPresent

    class PsychoacousticsEcho(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class RemovedInGameModifier(override val play: Int? = null, override val subPlay: Int? = null, override val parent: FeedID? = null, val mod: ModificationID, val type: Int) : WithPlay, WithParent
    class RemovedModifierDueToAnotherModifier(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mod: ModificationID,
        val type: Int,
        val source: ModificationID
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class RenovationBuiltInt(val title: String, val votes: @Serializable(CoercedIntSerialiser::class) Int, val renoId: String)
    class ReturnFromElsewhere(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class ReverbShuffle(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class ReverbShuffleFull(override val play: Int, override val subPlay: Int, override val parent: FeedID) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent
    class ReverbShuffleRotation(override val play: Int, override val subPlay: Int, override val parent: FeedID) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent
    class Reverberating(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    class SalmonSwimUpstream(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Siphon(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class SiteTakeoverText(val being: Int)
    class SolarPanelAlignment(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class SolarPanelOverflowRuns(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class SolarPanelRunCollection(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class StolenBase(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Strike(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class StrikeZappedElectricBlood(override val play: Int, override val subPlay: Int) : WithPlay.AlwaysPresent
    class Strikeout(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class Sun2InGame(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class SuperyummyText(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class SuperyummyTransition(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val from: ModificationID,
        val to: ModificationID,
        val type: Int,
        val source: ModificationID
    ) : WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    class TarotReading(@Serializable(CoercedIntSerialiser::class) val count: Int, val spread: List<Int>, val tarotId: String? = null)
    class TastingTheInfinite(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class TeamMiddling(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class TeamOverperforming(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class TeamShamed(val totalShames: Int, val totalShamings: Int)
    class TeamShames(val totalShames: Int, val totalShamings: Int)
    class TeamWinsInternetSeries(val championships: Int)
    class InvestigationUnderway(override val children: List<FeedID>) : WithChildren.AlwaysPresent
    class TripleThreat(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class UnderOver(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class Undersea(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class Walk(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : WithPlay.AlwaysPresent, WithChildren
    class WillReceived(val id: WillID, val title: String, override val children: List<FeedID>, val dataVotes: Int, val willVotes: Int, val totalVotes: Int) : WithChildren.AlwaysPresent
    class WillResults(override val parent: FeedID) : WithParent.AlwaysPresent
    class Wired(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : WithPlay.AlwaysPresent, WithChildren.AlwaysPresent
    class ModificationTransition(override val play: Int? = null, override val subPlay: Int? = null, override val parent: FeedID? = null, val from: ModificationID, val to: ModificationID, val type: Int) : WithPlay, WithParent
}

object CoercedIntSerialiser : KSerializer<Int> {
    override val descriptor: SerialDescriptor = Int.serializer().descriptor

    override fun deserialize(decoder: Decoder): Int =
        when (decoder) {
            is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.int
            else -> try {
                decoder.decodeInt()
            } catch (serial: SerializationException) {
                //NOTE: Don't *know* if this will actually work
                decoder.decodeString().toInt()
            }
        }

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeInt(value)
    }
}

//TODO: Once https://github.com/Kotlin/kotlinx.serialization/pull/1408 has been merged into main, replace with sealed class serialisation via @SerialName
object BlaseballFeedEventSerialiser : KSerializer<BlaseballFeedEvent<*>> {
    val METADATA_TYPES = mapOf(
        "UNKNOWN" to JsonObject.serializer(),
        "WITH_PLAY_OPTIONAL" to BlaseballFeedMetadata.WithPlay.Instance.serializer(),
        "WITH_PLAY" to BlaseballFeedMetadata.WithPlay.AlwaysPresent.Instance.serializer()
    )

    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ClientEvent") {
        element<Int>("type")

        element<FeedID>("id")
        element("playerTags", listSerialDescriptor<PlayerID>())
        element("teamTags", listSerialDescriptor<TeamID>())
        element("gameTags", listSerialDescriptor<GameID>())
        element<String>("created")
        element<Int>("season")
        element<Int>("tournament")
        element<Int>("day")
        element<Int>("phase")
        element<Int>("category")
        element<String>("description")
        element<Int>("nuts")

        element("metadata", buildSerialDescriptor("metadata", PolymorphicKind.SEALED) {
            METADATA_TYPES.forEach { (k, v) -> element(k, v.descriptor) }
        })
    }

    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): BlaseballFeedEvent<*> =
        decoder.decodeStructure(descriptor) {
            val builder = BlaseballFeedEvent.Builder()

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    descriptor.getElementIndex("type") -> builder.type = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("id") -> builder.id = decodeInlineElement<FeedID>(descriptor, index)
                    descriptor.getElementIndex("playerTags") -> builder.playerTags = decodeSerializableElement(descriptor, index, ListSerializer(PlayerID::class.serializer()))
                    descriptor.getElementIndex("teamTags") -> builder.teamTags = decodeSerializableElement(descriptor, index, ListSerializer(TeamID::class.serializer()))
                    descriptor.getElementIndex("gameTags") -> builder.gameTags = decodeSerializableElement(descriptor, index, ListSerializer(GameID::class.serializer()))
                    descriptor.getElementIndex("created") -> builder.created = decodeStringElement(descriptor, index)
                    descriptor.getElementIndex("season") -> builder.season = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("tournament") -> builder.tournament = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("day") -> builder.day = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("phase") -> builder.phase = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("category") -> builder.category = decodeIntElement(descriptor, index)
                    descriptor.getElementIndex("description") -> builder.description = decodeStringElement(descriptor, index)
                    descriptor.getElementIndex("nuts") -> builder.nuts = decodeIntElement(descriptor, index)

                    descriptor.getElementIndex("metadata") -> {
                        if (builder.type == null) throw IllegalStateException("Metadata initialised before type was read!")


                    }
                }
            }

            val type = decodeStringElement(descriptor, decodeElementIndex(descriptor))
            val deserialiser = SERIALISER_MAP[type] ?: throw IllegalStateException("Unknown event of type '${type}'!")

            decodeSerializableElement(descriptor, decodeElementIndex(descriptor), deserialiser)
        }

    override fun serialize(encoder: Encoder, value: ClientEvent) {
        val type = TYPE_NAMES[value::class] ?: throw IllegalStateException("Unknown event $value")

        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, type)

            value.serialise(this, descriptor)
        }
    }
}

