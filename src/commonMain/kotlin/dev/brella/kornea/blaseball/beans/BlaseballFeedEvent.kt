package dev.brella.kornea.blaseball.beans

import dev.brella.kornea.blaseball.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer

@Serializable(BlaseballFeedEventSerialiser::class)
sealed class BlaseballFeedEvent {
    abstract val id: FeedID
    abstract val playerTags: List<PlayerID>
    abstract val teamTags: List<TeamID>
    abstract val gameTags: List<GameID>
    abstract val metadata: BlaseballFeedMetadata?
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
    data class AddedInGameModifier(
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
        override val metadata: BlaseballFeedMetadata.AddedInGameModifier
    ) : BlaseballFeedEvent()

    @Serializable
    data class AddedModifierDueToAnotherModifier(
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
        override val metadata: BlaseballFeedMetadata.AddedModifierDueToAnotherModifier
    ) : BlaseballFeedEvent()

    @Serializable
    data class AddedModifierDueToEcho(
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
        override val metadata: BlaseballFeedMetadata.AddedModifierDueToEcho
    ) : BlaseballFeedEvent()

    @Serializable
    data class AllergicReaction(
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
        override val metadata: BlaseballFeedMetadata.AllergicReaction
    ) : BlaseballFeedEvent()

    @Serializable
    data class AlternateCoinText(
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
        override val metadata: BlaseballFeedMetadata.AlternateCoinText?
    ) : BlaseballFeedEvent()

    @Serializable
    data class Ball(
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
        override val metadata: BlaseballFeedMetadata.Ball
    ) : BlaseballFeedEvent()

    @Serializable
    data class BirdsFlavourText(
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
        override val metadata: BlaseballFeedMetadata.BirdsFlavourText
    ) : BlaseballFeedEvent()

    @Serializable
    data class BirdsFreeShelledPlayer(
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
        override val metadata: BlaseballFeedMetadata.BirdsFreeShelledPlayer
    ) : BlaseballFeedEvent()

    @Serializable
    data class BlackHoleInGame(
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
        override val metadata: BlaseballFeedMetadata.BlackHoleInGame
    ) : BlaseballFeedEvent()

    @Serializable
    data class BlessingWon(
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
        override val metadata: BlaseballFeedMetadata.BlessingWon
    ) : BlaseballFeedEvent()

    @Serializable
    data class BlessingResults(
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
        override val metadata: BlaseballFeedMetadata.BlessingResults
    ) : BlaseballFeedEvent()

    @Serializable
    data class BloodDrain(
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
        override val metadata: BlaseballFeedMetadata.BloodDrain
    ) : BlaseballFeedEvent()

    @Serializable
    data class ConsumersAttack(
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
        override val metadata: BlaseballFeedMetadata.ConsumersAttack
    ) : BlaseballFeedEvent()

    @Serializable
    data class DecreeNarration(
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
        override val metadata: BlaseballFeedMetadata.DecreeNarration
    ) : BlaseballFeedEvent()

    @Serializable
    data class DecreePassed(
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
        override val metadata: BlaseballFeedMetadata.DecreePassed
    ) : BlaseballFeedEvent()

    @Serializable
    data class EarlBirds(
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
        override val metadata: BlaseballFeedMetadata.EarlBirds
    ) : BlaseballFeedEvent()

    @Serializable
    data class EchoChamberTrapsWave(
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
        override val metadata: BlaseballFeedMetadata.EchoChamberTrapsWave
    ) : BlaseballFeedEvent()

    @Serializable
    data class EchoFades(
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
        override val metadata: BlaseballFeedMetadata.EchoFades
    ) : BlaseballFeedEvent()

    @Serializable
    data class EchoPlayerReceiver(
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
        override val metadata: BlaseballFeedMetadata.EchoPlayerReceiver
    ) : BlaseballFeedEvent()

    @Serializable
    data class EchoPlayerStatic(
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
        override val metadata: BlaseballFeedMetadata.EchoPlayerStatic
    ) : BlaseballFeedEvent()

    @Serializable
    data class EchoPlayerStaticChild(
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
        override val metadata: BlaseballFeedMetadata.EchoPlayerStaticChild
    ) : BlaseballFeedEvent()

    @Serializable
    data class EmergencyAlert(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()

    @Serializable
    data class EndOfInning(
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
        override val metadata: BlaseballFeedMetadata.EndOfInning
    ) : BlaseballFeedEvent()

    @Serializable
    data class Feedback(
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
        override val metadata: BlaseballFeedMetadata.Feedback
    ) : BlaseballFeedEvent()

    @Serializable
    data class FeedbackBlocked(
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
        override val metadata: BlaseballFeedMetadata.FeedbackBlocked
    ) : BlaseballFeedEvent()

    @Serializable
    data class FinalStandings(
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
        override val metadata: BlaseballFeedMetadata.FinalStandings
    ) : BlaseballFeedEvent()

    @Serializable
    data class FireEating(
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
        override val metadata: BlaseballFeedMetadata.FireEating
    ) : BlaseballFeedEvent()

    @Serializable
    data class FlagPlanted(
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
        override val metadata: BlaseballFeedMetadata.FlagPlanted
    ) : BlaseballFeedEvent()

    @Serializable
    data class Flooding(
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
        override val metadata: BlaseballFeedMetadata.Flooding
    ) : BlaseballFeedEvent()

    @Serializable
    data class Flyout(
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
        override val metadata: BlaseballFeedMetadata.Flyout
    ) : BlaseballFeedEvent()

    @Serializable
    data class FoulBall(
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
        override val metadata: BlaseballFeedMetadata.FoulBall
    ) : BlaseballFeedEvent()

    @Serializable
    data class FreeRefill(
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
        override val metadata: BlaseballFeedMetadata.FreeRefill
    ) : BlaseballFeedEvent()

    @Serializable
    data class GameEndLog(
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
        override val metadata: BlaseballFeedMetadata.GameEndLog
    ) : BlaseballFeedEvent()

    @Serializable
    data class GroundOut(
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
        override val metadata: BlaseballFeedMetadata.GroundOut
    ) : BlaseballFeedEvent()

    @Serializable
    data class HalfInning(
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
        override val metadata: BlaseballFeedMetadata.HalfInning
    ) : BlaseballFeedEvent()

    @Serializable
    data class Hit(
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
        override val metadata: BlaseballFeedMetadata.Hit
    ) : BlaseballFeedEvent()

    @Serializable
    data class HomeRun(
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
        override val metadata: BlaseballFeedMetadata.HomeRun
    ) : BlaseballFeedEvent()

    @Serializable
    data class Homesick(
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
        override val metadata: BlaseballFeedMetadata.Homesick
    ) : BlaseballFeedEvent()

    @Serializable
    data class Incineration(
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
        override val metadata: BlaseballFeedMetadata.Incineration
    ) : BlaseballFeedEvent()

    @Serializable
    data class LateToTheParty(
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
        override val metadata: BlaseballFeedMetadata.LateToTheParty
    ) : BlaseballFeedEvent()

    @Serializable
    data class LetsGo(
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
        override val metadata: BlaseballFeedMetadata.LetsGo
    ) : BlaseballFeedEvent()

    @Serializable
    data class LineupOrganised(
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
        override val metadata: BlaseballFeedMetadata.LineupOrganised
    ) : BlaseballFeedEvent()

    @Serializable
    data class MildPitch(
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
        override val metadata: BlaseballFeedMetadata.MildPitch
    ) : BlaseballFeedEvent()

    @Serializable
    data class ModifierExpires(
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
        override val metadata: BlaseballFeedMetadata.ModifierExpires
    ) : BlaseballFeedEvent()

    @Serializable
    data class MurderOfCrows(
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
        override val metadata: BlaseballFeedMetadata.MurderOfCrows
    ) : BlaseballFeedEvent()

    @Serializable
    data class NecromancyNarration(
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
        override val metadata: BlaseballFeedMetadata.NecromancyNarration
    ) : BlaseballFeedEvent()

    @Serializable
    data class NewPlayer(
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
        override val metadata: BlaseballFeedMetadata.NewPlayer
    ) : BlaseballFeedEvent()

    @Serializable
    data class NewPlayerAfterIncineration(
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
        override val metadata: BlaseballFeedMetadata.NewPlayerAfterIncineration
    ) : BlaseballFeedEvent()

    @Serializable
    data class NewTeam(
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
        override val metadata: BlaseballFeedMetadata.NewTeam
    ) : BlaseballFeedEvent()

    @Serializable
    data class OverUnder(
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
        override val metadata: BlaseballFeedMetadata.OverUnder
    ) : BlaseballFeedEvent()

    @Serializable
    data class Partying(
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
        override val metadata: BlaseballFeedMetadata.Partying
    ) : BlaseballFeedEvent()

    @Serializable
    data class PeanutAllergyCured(
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
        override val metadata: BlaseballFeedMetadata.PeanutAllergyCured
    ) : BlaseballFeedEvent()

    @Serializable
    data class PeanutMister(
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
        override val metadata: BlaseballFeedMetadata.PeanutMister
    ) : BlaseballFeedEvent()

    @Serializable
    data class PeanutFlavourText(
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
        override val metadata: BlaseballFeedMetadata.PeanutFlavourText
    ) : BlaseballFeedEvent()

    @Serializable
    data class Perk(
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
        override val metadata: BlaseballFeedMetadata.Perk
    ) : BlaseballFeedEvent()

    @Serializable
    data class PitcherChange(
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
        override val metadata: BlaseballFeedMetadata.PitcherChange
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlateAppearance(
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
        override val metadata: BlaseballFeedMetadata.PlateAppearance
    ) : BlaseballFeedEvent()

    @Serializable
    data class Play(
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
        override val metadata: BlaseballFeedMetadata.Play
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerBecomesAlternate(
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
        override val metadata: BlaseballFeedMetadata.PlayerBecomesAlternate
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerChangingPosition(
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
        override val metadata: BlaseballFeedMetadata.PlayerChangingPosition
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerDroppedItem(
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
        override val metadata: BlaseballFeedMetadata.PlayerDroppedItem
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerEchoed(
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
        override val metadata: BlaseballFeedMetadata.PlayerEchoed
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerEntersSecretBase(
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
        override val metadata: BlaseballFeedMetadata.PlayerEntersSecretBase
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerExitsSecretBase(
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
        override val metadata: BlaseballFeedMetadata.PlayerExitsSecretBase
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerEntersCrimeScene(
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
        override val metadata: BlaseballFeedMetadata.PlayerEntersCrimeScene
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerEntersHallOfFlame(
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
        override val metadata: BlaseballFeedMetadata.PlayerEntersHallOfFlame
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerExitsHallOfFlame(
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
        override val metadata: BlaseballFeedMetadata.PlayerExitsHallOfFlame
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerEvolves(
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
        override val metadata: BlaseballFeedMetadata.PlayerEvolves
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerGainedBloodType(
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
        override val metadata: BlaseballFeedMetadata.PlayerGainedBloodType
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerGainedItem(
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
        override val metadata: BlaseballFeedMetadata.PlayerGainedItem
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerHatched(
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
        override val metadata: BlaseballFeedMetadata.PlayerHatched
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerHitsAnotherWithPitch(
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
        override val metadata: BlaseballFeedMetadata.PlayerHitsAnotherWithPitch
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerHopsOnGrindRail(
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
        override val metadata: BlaseballFeedMetadata.PlayerHopsOnGrindRail
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerJoiningTeam(
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
        override val metadata: BlaseballFeedMetadata.PlayerJoiningTeam
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerOpensCrate(
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
        override val metadata: BlaseballFeedMetadata.PlayerOpensCrate
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerRecruited(
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
        override val metadata: BlaseballFeedMetadata.PlayerRecruited
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerRemovedAndReplacedFromShadows(
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
        override val metadata: BlaseballFeedMetadata.PlayerRemovedAndReplacedFromShadows
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerReroll(
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
        override val metadata: BlaseballFeedMetadata.PlayerReroll
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayersItemBroke(
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
        override val metadata: BlaseballFeedMetadata.PlayersItemBroke
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayersItemDamaged(
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
        override val metadata: BlaseballFeedMetadata.PlayersItemDamaged
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayersItemRepaired(
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
        override val metadata: BlaseballFeedMetadata.PlayersItemRepaired
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayersItemRestored(
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
        override val metadata: BlaseballFeedMetadata.PlayersItemRestored
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerSentToShadowsAndReplaced(
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
        override val metadata: BlaseballFeedMetadata.PlayerSentToShadowsAndReplaced
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerSkippedShelledOrElsewhere(
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
        override val metadata: BlaseballFeedMetadata.PlayerSkippedShelledOrElsewhere
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerStatDecrease(
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
        override val metadata: BlaseballFeedMetadata.PlayerStatDecrease
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerStatDecreaseByPercent(
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
        override val metadata: BlaseballFeedMetadata.PlayerStatDecreaseByPercent
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerStatIncrease(
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
        override val metadata: BlaseballFeedMetadata.PlayerStatIncrease
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerStatIncreaseByPercent(
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
        override val metadata: BlaseballFeedMetadata.PlayerStatIncreaseByPercent
    ) : BlaseballFeedEvent()

    @Serializable
    data class PlayerTrade(
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
        override val metadata: BlaseballFeedMetadata.PlayerTrade
    ) : BlaseballFeedEvent()

    @Serializable
    data class PostseasonSpot(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()

    @Serializable
    data class PsychoacousticsEcho(
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
        override val metadata: BlaseballFeedMetadata.PsychoacousticsEcho
    ) : BlaseballFeedEvent()

    @Serializable
    data class RemovedInGameModifier(
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
        override val metadata: BlaseballFeedMetadata.RemovedInGameModifier
    ) : BlaseballFeedEvent()

    @Serializable
    data class RemovedModifierDueToAnotherModifier(
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
        override val metadata: BlaseballFeedMetadata.RemovedModifierDueToAnotherModifier
    ) : BlaseballFeedEvent()

    @Serializable
    data class RenovationBuiltInt(
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
        override val metadata: BlaseballFeedMetadata.RenovationBuiltInt
    ) : BlaseballFeedEvent()

    @Serializable
    data class ReturnFromElsewhere(
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
        override val metadata: BlaseballFeedMetadata.ReturnFromElsewhere
    ) : BlaseballFeedEvent()

    @Serializable
    data class ReverbShuffle(
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
        override val metadata: BlaseballFeedMetadata.ReverbShuffle
    ) : BlaseballFeedEvent()

    @Serializable
    data class ReverbShuffleFull(
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
        override val metadata: BlaseballFeedMetadata.ReverbShuffleFull
    ) : BlaseballFeedEvent()

    @Serializable
    data class ReverbShuffleRotation(
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
        override val metadata: BlaseballFeedMetadata.ReverbShuffleRotation
    ) : BlaseballFeedEvent()

    @Serializable
    data class Reverberating(
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
        override val metadata: BlaseballFeedMetadata.Reverberating
    ) : BlaseballFeedEvent()

    @Serializable
    data class SalmonSwimUpstream(
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
        override val metadata: BlaseballFeedMetadata.SalmonSwimUpstream
    ) : BlaseballFeedEvent()

    @Serializable
    data class Siphon(
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
        override val metadata: BlaseballFeedMetadata.Siphon
    ) : BlaseballFeedEvent()

    @Serializable
    data class SiteTakeoverText(
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
        override val metadata: BlaseballFeedMetadata.SiteTakeoverText
    ) : BlaseballFeedEvent()

    @Serializable
    data class SolarPanelAlignment(
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
        override val metadata: BlaseballFeedMetadata.SolarPanelAlignment
    ) : BlaseballFeedEvent()

    @Serializable
    data class SolarPanelOverflowRuns(
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
        override val metadata: BlaseballFeedMetadata.SolarPanelOverflowRuns
    ) : BlaseballFeedEvent()

    @Serializable
    data class SolarPanelRunCollection(
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
        override val metadata: BlaseballFeedMetadata.SolarPanelRunCollection
    ) : BlaseballFeedEvent()

    @Serializable
    data class StolenBase(
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
        override val metadata: BlaseballFeedMetadata.StolenBase
    ) : BlaseballFeedEvent()

    @Serializable
    data class Strike(
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
        override val metadata: BlaseballFeedMetadata.Strike
    ) : BlaseballFeedEvent()

    @Serializable
    data class StrikeZappedElectricBlood(
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
        override val metadata: BlaseballFeedMetadata.StrikeZappedElectricBlood
    ) : BlaseballFeedEvent()

    @Serializable
    data class Strikeout(
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
        override val metadata: BlaseballFeedMetadata.Strikeout
    ) : BlaseballFeedEvent()

    @Serializable
    data class Sun2InGame(
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
        override val metadata: BlaseballFeedMetadata.Sun2InGame
    ) : BlaseballFeedEvent()

    @Serializable
    data class SuperyummyText(
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
        override val metadata: BlaseballFeedMetadata.SuperyummyText
    ) : BlaseballFeedEvent()

    @Serializable
    data class SuperyummyTransition(
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
        override val metadata: BlaseballFeedMetadata.SuperyummyTransition
    ) : BlaseballFeedEvent()

    @Serializable
    data class TarotReading(
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
        override val metadata: BlaseballFeedMetadata.TarotReading
    ) : BlaseballFeedEvent()

    @Serializable
    data class TastingTheInfinite(
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
        override val metadata: BlaseballFeedMetadata.TastingTheInfinite
    ) : BlaseballFeedEvent()

    @Serializable
    data class TeamMiddling(
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
        override val metadata: BlaseballFeedMetadata.TeamMiddling
    ) : BlaseballFeedEvent()

    @Serializable
    data class TeamOverperforming(
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
        override val metadata: BlaseballFeedMetadata.TeamOverperforming
    ) : BlaseballFeedEvent()

    @Serializable
    data class TeamShamed(
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
        override val metadata: BlaseballFeedMetadata.TeamShamed
    ) : BlaseballFeedEvent()

    @Serializable
    data class TeamShames(
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
        override val metadata: BlaseballFeedMetadata.TeamShames
    ) : BlaseballFeedEvent()

    @Serializable
    data class TeamWinsInternetSeries(
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
        override val metadata: BlaseballFeedMetadata.TeamWinsInternetSeries
    ) : BlaseballFeedEvent()

    @Serializable
    data class InvestigationUnderway(
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
        override val metadata: BlaseballFeedMetadata.InvestigationUnderway
    ) : BlaseballFeedEvent()

    @Serializable
    data class TripleThreat(
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
        override val metadata: BlaseballFeedMetadata.TripleThreat
    ) : BlaseballFeedEvent()

    @Serializable
    data class UnderOver(
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
        override val metadata: BlaseballFeedMetadata.UnderOver
    ) : BlaseballFeedEvent()

    @Serializable
    data class Undersea(
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
        override val metadata: BlaseballFeedMetadata.Undersea
    ) : BlaseballFeedEvent()

    @Serializable
    data class Walk(
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
        override val metadata: BlaseballFeedMetadata.Walk
    ) : BlaseballFeedEvent()

    @Serializable
    data class WillReceived(
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
        override val metadata: BlaseballFeedMetadata.WillReceived
    ) : BlaseballFeedEvent()

    @Serializable
    data class WillResults(
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
        override val metadata: BlaseballFeedMetadata.WillResults
    ) : BlaseballFeedEvent()

    @Serializable
    data class Wired(
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
        override val metadata: BlaseballFeedMetadata.Wired
    ) : BlaseballFeedEvent()

    @Serializable
    data class ModificationTransition(
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
        override val metadata: BlaseballFeedMetadata.ModificationTransition
    ) : BlaseballFeedEvent()
    
    @Serializable
    data class Sun2GrantsWin(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()
    
    @Serializable
    data class BlackHoleSwallows(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()
    
    @Serializable
    data class EliminatedFromPostseason(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()
    
    @Serializable
    data class PostseasonAdvance(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()
    
    @Serializable
    data class ReturnedPlayerPermittedToStay(
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
        override val metadata: BlaseballFeedMetadata.None?
    ) : BlaseballFeedEvent()

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
        override val metadata: BlaseballFeedMetadata.Unknown
    ) : BlaseballFeedEvent()

    class Builder {
        companion object {
            fun <T : BlaseballFeedMetadata?> buildConstructor(
                constructor: (
                    id: FeedID,
                    playerTags: List<PlayerID>,
                    teamTags: List<TeamID>,
                    gameTags: List<GameID>,
                    created: String,
                    season: Int,
                    tournament: Int,
                    type: Int,
                    day: Int,
                    phase: Int,
                    category: Int,
                    description: String,
                    nuts: Int,
                    metadata: T
                ) -> BlaseballFeedEvent
            ): BlaseballFeedEventConstructor = { id, playerTags, teamTags, gameTags, created, season, tournament, type, day, phase, category, description, nuts, metadata ->
                constructor(id, playerTags, teamTags, gameTags, created, season, tournament, type, day, phase, category, description, nuts, metadata as T)
            }

            val CONSTRUCTORS = BlaseballFeedEventType.associateWith { type ->
                when (type) {
                    BlaseballFeedEventType.LETS_GO -> buildConstructor(::LetsGo)
                    BlaseballFeedEventType.PLAY_BALL -> buildConstructor(::Play)
                    BlaseballFeedEventType.HALF_INNING -> buildConstructor(::HalfInning)
                    BlaseballFeedEventType.PITCHER_CHANGE -> buildConstructor(::PitcherChange)
                    BlaseballFeedEventType.STOLEN_BASE -> buildConstructor(::StolenBase)
                    BlaseballFeedEventType.WALK -> buildConstructor(::Walk)
                    BlaseballFeedEventType.STRIKEOUT -> buildConstructor(::Strikeout)
                    BlaseballFeedEventType.FLYOUT -> buildConstructor(::Flyout)
                    BlaseballFeedEventType.GROUND_OUT -> buildConstructor(::GroundOut)
                    BlaseballFeedEventType.HOME_RUN -> buildConstructor(::HomeRun)
                    BlaseballFeedEventType.HIT -> buildConstructor(::Hit)
                    BlaseballFeedEventType.GAME_END_LOG -> buildConstructor(::GameEndLog)
                    BlaseballFeedEventType.PLATE_APPEARANCE -> buildConstructor(::PlateAppearance)
                    BlaseballFeedEventType.STRIKE -> buildConstructor(::Strike)
                    BlaseballFeedEventType.BALL -> buildConstructor(::Ball)
                    BlaseballFeedEventType.FOUL_BALL -> buildConstructor(::FoulBall)
                    BlaseballFeedEventType.SOLAR_PANEL_OVERFLOW_RUNS -> buildConstructor(::SolarPanelOverflowRuns)
                    BlaseballFeedEventType.PLAYER_HITS_ANOTHER_WITH_PITCH -> buildConstructor(::PlayerHitsAnotherWithPitch)
                    BlaseballFeedEventType.PLAYER_SKIPPED_DUE_TO_SHELL -> buildConstructor(::PlayerSkippedShelledOrElsewhere)
                    BlaseballFeedEventType.PARTYING -> buildConstructor(::Partying)
                    BlaseballFeedEventType.STRIKE_ZAPPED_BY_ELECTRIC_BLOOD -> buildConstructor(::StrikeZappedElectricBlood)
                    BlaseballFeedEventType.MILD_PITCH -> buildConstructor(::MildPitch)
                    BlaseballFeedEventType.END_OF_INNING -> buildConstructor(::EndOfInning)
                    BlaseballFeedEventType.SITE_TAKEOVER_TEXT -> buildConstructor(::SiteTakeoverText)

                    BlaseballFeedEventType.BLACK_HOLE -> buildConstructor(::BlackHoleInGame)
                    BlaseballFeedEventType.SUN_2 -> buildConstructor(::Sun2InGame)
                    BlaseballFeedEventType.BIRDS_FLAVOUR_TEXT -> buildConstructor(::BirdsFlavourText)
                    BlaseballFeedEventType.MURDER_OF_CROWS -> buildConstructor(::MurderOfCrows)
                    BlaseballFeedEventType.BIRDS_FREE_SHELLED_PLAYER -> buildConstructor(::BirdsFreeShelledPlayer)
                    BlaseballFeedEventType.TRIPLE_THREAT -> buildConstructor(::TripleThreat)
                    BlaseballFeedEventType.FREE_REFILL -> buildConstructor(::FreeRefill)
                    BlaseballFeedEventType.WIRED -> buildConstructor(::Wired)
                    BlaseballFeedEventType.FEEDBACK_BLOCKED -> buildConstructor(::FeedbackBlocked)
                    BlaseballFeedEventType.FEEDBACK -> buildConstructor(::Feedback)
                    BlaseballFeedEventType.ALLERGIC_REACTION -> buildConstructor(::AllergicReaction)
                    BlaseballFeedEventType.REVERBERATING -> buildConstructor(::Reverberating)
                    BlaseballFeedEventType.REVERB_SHUFFLE -> buildConstructor(::ReverbShuffle)
                    BlaseballFeedEventType.BLOODDRAIN -> buildConstructor(::BloodDrain)
                    BlaseballFeedEventType.SIPHON -> buildConstructor(::Siphon)
                    BlaseballFeedEventType.INCINERATION -> buildConstructor(::Incineration)
                    BlaseballFeedEventType.FIRE_EATING -> buildConstructor(::FireEating)
                    BlaseballFeedEventType.FLAG_PLANTED -> buildConstructor(::FlagPlanted)
                    BlaseballFeedEventType.RENOVATION_BUILT -> buildConstructor(::RenovationBuiltInt)
                    BlaseballFeedEventType.DECREE_PASSED -> buildConstructor(::DecreePassed)
                    BlaseballFeedEventType.BLESSING_WON -> buildConstructor(::BlessingWon)
                    BlaseballFeedEventType.WILL_RECEIVED -> buildConstructor(::WillReceived)
                    BlaseballFeedEventType.FLOOD -> buildConstructor(::Flooding)
                    BlaseballFeedEventType.SALMON_SWIM_UPSTREAM -> buildConstructor(::SalmonSwimUpstream)

                    BlaseballFeedEventType.PLAYER_ENTERS_SECRET_BASE -> buildConstructor(::PlayerEntersSecretBase)
                    BlaseballFeedEventType.PLAYER_EXITS_SECRET_BASE -> buildConstructor(::PlayerExitsSecretBase)
                    BlaseballFeedEventType.CONSUMERS_ATTACK_PLAYER -> buildConstructor(::ConsumersAttack)

                    BlaseballFeedEventType.ECHO_CHAMBER_TRAPS_WAVE -> buildConstructor(::EchoChamberTrapsWave)
                    BlaseballFeedEventType.PLAYER_HOPS_ON_GRIND_RAIL -> buildConstructor(::PlayerHopsOnGrindRail)

                    BlaseballFeedEventType.PEANUT_MISTER -> buildConstructor(::PeanutMister)
                    BlaseballFeedEventType.PEANUTS_FLAVOUR_TEXT -> buildConstructor(::PeanutFlavourText)

                    /** Shelling */
                    BlaseballFeedEventType.TASTING_THE_INFINITE -> buildConstructor(::TastingTheInfinite)
                    BlaseballFeedEventType.SOLAR_PANEL_ALIGNMENT -> buildConstructor(::SolarPanelAlignment)
                    BlaseballFeedEventType.SOLAR_PANEL_RUN_COLLECTION -> buildConstructor(::SolarPanelRunCollection)

                    BlaseballFeedEventType.TAROT_READING -> buildConstructor(::TarotReading)
                    BlaseballFeedEventType.EMERGENCY_ALERT -> buildConstructor(::EmergencyAlert)
                    BlaseballFeedEventType.RETURN_FROM_ELSEWHERE -> buildConstructor(::ReturnFromElsewhere)
                    BlaseballFeedEventType.OVER_UNDER -> buildConstructor(::OverUnder)
                    BlaseballFeedEventType.UNDER_OVER -> buildConstructor(::UnderOver)

                    BlaseballFeedEventType.UNDERSEA -> buildConstructor(::Undersea)

                    BlaseballFeedEventType.HOMESICK -> buildConstructor(::Homesick)
                    BlaseballFeedEventType.SUPERYUMMY_TEXT -> buildConstructor(::SuperyummyText)
                    BlaseballFeedEventType.PERK -> buildConstructor(::Perk)

                    BlaseballFeedEventType.EARLBIRDS -> buildConstructor(::EarlBirds)
                    BlaseballFeedEventType.LATE_TO_PARTY -> buildConstructor(::LateToTheParty)

                    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                    BlaseballFeedEventType.ADDED_INGAME_MODIFIER -> buildConstructor(::AddedInGameModifier)

                    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                    BlaseballFeedEventType.REMOVED_INGAME_MODIFIER -> buildConstructor(::RemovedInGameModifier)

                    BlaseballFeedEventType.MODIFIER_EXPIRES -> buildConstructor(::ModifierExpires)

                    /** Including Postseason Births */
                    BlaseballFeedEventType.PLAYER_RECRUITED -> buildConstructor(::PlayerRecruited)

                    /** Necromancy */
                    BlaseballFeedEventType.PLAYER_SENT_TO_SHADOWS_AND_REPLACED -> buildConstructor(::PlayerSentToShadowsAndReplaced)

                    /** Returned */
                    BlaseballFeedEventType.PLAYER_REMOVED_AND_REPLACED_FROM_SHADOWS -> buildConstructor(::PlayerRemovedAndReplacedFromShadows)
                    BlaseballFeedEventType.ECHO_PLAYER_STATIC_CHILD -> buildConstructor(::EchoPlayerStaticChild)
                    BlaseballFeedEventType.PLAYER_TRADE -> buildConstructor(::PlayerTrade)
                    BlaseballFeedEventType.PLAYER_CHANGING_POSITION -> buildConstructor(::PlayerChangingPosition)
                    BlaseballFeedEventType.PLAYER_JOINING_TEAM -> buildConstructor(::PlayerJoiningTeam)
                    BlaseballFeedEventType.NEW_PLAYER_AFTER_INCINERATION -> buildConstructor(::NewPlayerAfterIncineration)
                    BlaseballFeedEventType.PLAYER_STAT_INCREASE -> buildConstructor(::PlayerStatIncrease)
                    BlaseballFeedEventType.PLAYER_STAT_DECREASE -> buildConstructor(::PlayerStatDecrease)
                    BlaseballFeedEventType.PLAYER_REROLL -> buildConstructor(::PlayerReroll)

                    BlaseballFeedEventType.PLAYER_ENTERS_HALL_OF_FLAME -> buildConstructor(::PlayerEntersHallOfFlame)
                    BlaseballFeedEventType.PLAYER_EXITS_HALL_OF_FLAME -> buildConstructor(::PlayerExitsHallOfFlame)
                    BlaseballFeedEventType.PLAYER_GAINED_ITEM -> buildConstructor(::PlayerGainedItem)
                    BlaseballFeedEventType.PLAYER_DROPPED_ITEM -> buildConstructor(::PlayerDroppedItem)

                    BlaseballFeedEventType.REVERB_SHUFFLE_FULL -> buildConstructor(::ReverbShuffleFull)
                    BlaseballFeedEventType.REVERB_SHUFFLE_ROTATION -> buildConstructor(::ReverbShuffleRotation)

                    BlaseballFeedEventType.NEW_TEAM -> buildConstructor(::NewTeam)

                    /** Excluding Incinerations */
                    BlaseballFeedEventType.NEW_PLAYER -> buildConstructor(::NewPlayer)
                    BlaseballFeedEventType.PLAYER_HATCHED -> buildConstructor(::PlayerHatched)

                    BlaseballFeedEventType.PLAYER_EVOLVES -> buildConstructor(::PlayerEvolves)
                    BlaseballFeedEventType.TEAM_WINS_INTERNET_SERIES -> buildConstructor(::TeamWinsInternetSeries)
                    BlaseballFeedEventType.POSTSEASON_SPOT -> buildConstructor(::PostseasonSpot)
                    BlaseballFeedEventType.FINAL_STANDINGS -> buildConstructor(::FinalStandings)
                    BlaseballFeedEventType.WIRED_TO_TIRED -> buildConstructor(::ModificationTransition)
                    BlaseballFeedEventType.PLAYER_TO_ALTERNATE -> buildConstructor(::PlayerBecomesAlternate)
                    BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_MODIFIER -> buildConstructor(::AddedModifierDueToAnotherModifier)
                    BlaseballFeedEventType.REMOVED_MODIFIER_DUE_TO_MODIFIER -> buildConstructor(::RemovedModifierDueToAnotherModifier)
                    BlaseballFeedEventType.SUPERYUMMY_TRANSITIONS -> buildConstructor(::SuperyummyTransition)
                    BlaseballFeedEventType.NECROMANCY_NARRATION -> buildConstructor(::NecromancyNarration)
                    BlaseballFeedEventType.RETURNED_PLAYER_PERMITTED_TO_STAY -> buildConstructor(::ReturnedPlayerPermittedToStay)
                    BlaseballFeedEventType.DECREE_NARRATION -> buildConstructor(::DecreeNarration)
                    BlaseballFeedEventType.WILL_RESULTS -> buildConstructor(::WillResults)
                    BlaseballFeedEventType.BLESSING_RESULTS -> buildConstructor(::BlessingResults)
                    BlaseballFeedEventType.TEAM_SHAMED -> buildConstructor(::TeamShamed)
                    BlaseballFeedEventType.TEAM_SHAMES -> buildConstructor(::TeamShames)
                    BlaseballFeedEventType.SUN_2_GRANTS_WIN -> buildConstructor(::Sun2GrantsWin)
                    BlaseballFeedEventType.BLACK_HOLE_SWALLOWS -> buildConstructor(::BlackHoleSwallows)
                    BlaseballFeedEventType.ELIMINATED_FROM_POSTSEASON -> buildConstructor(::EliminatedFromPostseason)
                    BlaseballFeedEventType.POSTSEASON_ADVANCE -> buildConstructor(::PostseasonAdvance)

                    BlaseballFeedEventType.PLAYER_GAINED_BLOOD_TYPE -> buildConstructor(::PlayerGainedBloodType)

                    BlaseballFeedEventType.TEAM_OVERPERFORMING -> buildConstructor(::TeamOverperforming)
                    BlaseballFeedEventType.LINEUP_OPTIMISED -> buildConstructor(::LineupOrganised)

                    BlaseballFeedEventType.PEANUT_ALLERGY_CURED -> buildConstructor(::PeanutAllergyCured)
                    BlaseballFeedEventType.PLAYER_ECHOED -> buildConstructor(::PlayerEchoed)
                    BlaseballFeedEventType.ECHO_PLAYER_STATIC -> buildConstructor(::EchoPlayerStatic)
                    BlaseballFeedEventType.ECHO_FADES -> buildConstructor(::EchoFades)
                    BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_ECHO -> buildConstructor(::AddedModifierDueToEcho)
                    BlaseballFeedEventType.PSYCHOACOUSTICS_ECHO -> buildConstructor(::PsychoacousticsEcho)
                    BlaseballFeedEventType.ECHO_PLAYER_ECHO_PLAYER_ECHO -> buildConstructor(::EchoPlayerStatic)
                    BlaseballFeedEventType.ALTERNATE_COIN_TEXT -> buildConstructor(::AlternateCoinText)
                    BlaseballFeedEventType.INVESTIGATION_UNDERWAY -> buildConstructor(::InvestigationUnderway)
                    BlaseballFeedEventType.PLAYER_OPENS_CRATE -> buildConstructor(::PlayerOpensCrate)
                    BlaseballFeedEventType.TEAM_MIDDLING -> buildConstructor(::TeamMiddling)
                    BlaseballFeedEventType.PLAYER_STAT_INCREASED_PERCENT -> buildConstructor(::PlayerStatIncreaseByPercent)
                    BlaseballFeedEventType.PLAYER_STAT_DECREASED_PERCENT -> buildConstructor(::PlayerStatDecreaseByPercent)
                    BlaseballFeedEventType.PLAYER_ENTERS_CRIME_SCENE_TO_INVESTIGATE -> buildConstructor(::PlayerEntersCrimeScene)

                    BlaseballFeedEventType.PLAYERS_ITEM_BROKE -> buildConstructor(::PlayersItemBroke)
                    BlaseballFeedEventType.PLAYERS_ITEM_DAMAGED -> buildConstructor(::PlayersItemDamaged)
                    BlaseballFeedEventType.PLAYERS_ITEM_RESTORED -> buildConstructor(::PlayersItemRestored)
                    BlaseballFeedEventType.PLAYERS_ITEM_REPAIRED -> buildConstructor(::PlayersItemRepaired)

                    else -> buildConstructor(::Unknown)
                }
            } + mapOf(-1 to buildConstructor(::Unknown))
        }

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
        var metadata: BlaseballFeedMetadata? = null

        fun build(): BlaseballFeedEvent {
            val id: FeedID = id!!
            val playerTags: List<PlayerID> = playerTags!!
            val teamTags: List<TeamID> = teamTags!!
            val gameTags: List<GameID> = gameTags!!
            val created: String = created!!
            val season: Int = season!!
            val tournament: Int = tournament!!
            val type: Int = type!!
            val day: Int = day!!
            val phase: Int = phase!!
            val category: Int = category!!
            val description: String = description!!
            val nuts: Int = nuts!!

            return (CONSTRUCTORS[type] ?: CONSTRUCTORS.getValue(-1))(id, playerTags, teamTags, gameTags, created, season, tournament, type, day, phase, category, description, nuts, metadata ?: BlaseballFeedMetadata.None)
        }
    }
}

typealias BlaseballFeedEventConstructor=(
    id: FeedID,
    playerTags: List<PlayerID>,
    teamTags: List<TeamID>,
    gameTags: List<GameID>,
    created: String,
    season: Int,
    tournament: Int,
    type: Int,
    day: Int,
    phase: Int,
    category: Int,
    description: String,
    nuts: Int,
    metadata: BlaseballFeedMetadata?
) -> BlaseballFeedEvent

sealed class BlaseballFeedMetadata {
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

    @Serializable
    class AddedInGameModifier(override val play: Int? = null, override val subPlay: Int? = null, val parent: FeedID? = null, val mod: ModificationID, val type: Int) : BlaseballFeedMetadata(), WithPlay

    @Serializable
    class AddedModifierDueToAnotherModifier(override val play: Int, override val subPlay: Int, val mod: ModificationID, val type: Int, val parent: FeedID, val source: ModificationID) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class AddedModifierDueToEcho(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val source: ModificationID,
        val adds: List<EchoModAddition>
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent {
        @Serializable
        data class EchoModAddition(val mod: ModificationID, val type: Int)
    }

    @Serializable
    class AllergicReaction(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class AlternateCoinText(override val play: Int? = null, override val subPlay: Int? = null, override val parent: FeedID? = null) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class Ball(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class BirdsFlavourText(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class BirdsFreeShelledPlayer(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class BlackHoleInGame(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class BlessingWon(val id: BlessingID, val title: String, val votes: Int, val teamId: TeamID, override val children: List<FeedID>, val teamName: String, val totalVotes: Int? = null, val highestTeam: TeamID, val highestVotes: Int? = null) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class BlessingResults(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class BloodDrain(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ConsumersAttack(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class DecreeNarration(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class DecreePassed(val id: DecreeID, val title: String, val votes: Int, override val children: List<FeedID>, val totalVotes: Int) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class EarlBirds(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoChamberTrapsWave(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoFades(override val play: Int? = null, override val subPlay: Int? = null, val source: ModificationID, val removes: List<EchoFadeRemoved>) : BlaseballFeedMetadata(), WithPlay {
        @Serializable
        data class EchoFadeRemoved(val mod: ModificationID, val type: Int)
    }

    @Serializable
    class EchoPlayerReceiver(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoPlayerStatic(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoPlayerStaticChild(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val teamId: TeamID,
        val playerId: PlayerID,
        val teamName: String,
        val playerName: String
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class EndOfInning(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Feedback(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class FeedbackBlocked(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class FinalStandings(val place: Int) : BlaseballFeedMetadata()

    @Serializable
    class FireEating(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FlagPlanted(val title: String, val votes: Int, val renoId: String) : BlaseballFeedMetadata()

    @Serializable
    class Flooding(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Flyout(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FoulBall(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FreeRefill(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class GameEndLog(override val play: Int, override val subPlay: Int, val winner: TeamID, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class GroundOut(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class HalfInning(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Hit(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class HomeRun(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Homesick(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Incineration(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class LateToTheParty(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class LetsGo(val home: TeamID, val away: TeamID, override val play: Int, override val subPlay: Int, val stadium: StadiumID? = null, val weather: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class LineupOrganised(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class MildPitch(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class ModifierExpires(val mods: List<ModificationID>, val type: Int) : BlaseballFeedMetadata()

    @Serializable
    class MurderOfCrows(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class NecromancyNarration(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class NewPlayer(val id: PlayerID) : BlaseballFeedMetadata()

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class NewTeam(val teamId: TeamID, val teamName: String, val divisionID: DivisionID, val divisionName: String) : BlaseballFeedMetadata()

    @Serializable
    class OverUnder(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Partying(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PeanutAllergyCured(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PeanutMister(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class PeanutFlavourText(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Perk(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PitcherChange(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlateAppearance(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Play(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class PlayerBecomesAlternate(val type: Int, val before: Double, val after: Double, override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PlayerDroppedItem(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val mods: List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        override val parent: FeedID,
        val playerRating: Double,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PlayerEchoed(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerEntersSecretBase(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerExitsSecretBase(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerEntersCrimeScene(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerEntersHallOfFlame(override val play: Int, override val subPlay: Int, override val parent: FeedID) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class PlayerExitsHallOfFlame(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerEvolves(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerGainedBloodType(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PlayerHatched(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val id: PlayerID
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerHitsAnotherWithPitch(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerHopsOnGrindRail(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerOpensCrate(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerRecruited(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val teamId: TeamID,
        val teamName: String,
        val location: Int,
        val playerId: PlayerID,
        val playerName: String
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerRemovedAndReplacedFromShadows(
        val teamId: TeamID,
        val teamName: String,
        val removeLocation: Int,
        val removePlayerId: PlayerID,
        val removePlayerName: String,
        val promoteLocation: Int,
        val promotePlayerId: PlayerID,
        val promotePlayerName: String
    ) : BlaseballFeedMetadata()

    @Serializable
    class PlayerReroll(
        val type: Int,
        val before: Double,
        val after: Double,
        override val parent: FeedID
    ) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerSkippedShelledOrElsewhere(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerStatDecrease(override val play: Int? = null, override val subPlay: Int? = null, val type: Int, val before: Double, val after: Double, override val parent: FeedID? = null) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerStatDecreaseByPercent(override val parent: FeedID? = null, val before: Double, val after: Double, val type: Int) : BlaseballFeedMetadata(), WithParent

    @Serializable
    class PlayerStatIncrease(override val play: Int? = null, override val subPlay: Int? = null, val type: Int, val before: Double, val after: Double, override val parent: FeedID? = null) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerStatIncreaseByPercent(override val parent: FeedID? = null, val before: Double, val after: Double, val type: Int) : BlaseballFeedMetadata(), WithParent

    @Serializable
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
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PsychoacousticsEcho(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class RemovedInGameModifier(override val play: Int? = null, override val subPlay: Int? = null, override val parent: FeedID? = null, val mod: ModificationID, val type: Int) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class RemovedModifierDueToAnotherModifier(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mod: ModificationID,
        val type: Int,
        val source: ModificationID
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class RenovationBuiltInt(val title: String, val votes: @Serializable(CoercedIntSerialiser::class) Int, val renoId: String) : BlaseballFeedMetadata()

    @Serializable
    class ReturnFromElsewhere(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class ReverbShuffle(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ReverbShuffleFull(override val play: Int, override val subPlay: Int, override val parent: FeedID) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class ReverbShuffleRotation(override val play: Int, override val subPlay: Int, override val parent: FeedID) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class Reverberating(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class SalmonSwimUpstream(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Siphon(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SiteTakeoverText(val being: Int) : BlaseballFeedMetadata()

    @Serializable
    class SolarPanelAlignment(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class SolarPanelOverflowRuns(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class SolarPanelRunCollection(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class StolenBase(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Strike(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class StrikeZappedElectricBlood(override val play: Int, override val subPlay: Int) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Strikeout(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Sun2InGame(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SuperyummyText(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SuperyummyTransition(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val from: ModificationID,
        val to: ModificationID,
        val type: Int,
        val source: ModificationID
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class TarotReading(@Serializable(CoercedIntSerialiser::class) val count: Int, val spread: List<Int>, val tarotId: String? = null) : BlaseballFeedMetadata()

    @Serializable
    class TastingTheInfinite(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class TeamMiddling(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class TeamOverperforming(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class TeamShamed(val totalShames: Int, val totalShamings: Int) : BlaseballFeedMetadata()

    @Serializable
    class TeamShames(val totalShames: Int, val totalShamings: Int) : BlaseballFeedMetadata()

    @Serializable
    class TeamWinsInternetSeries(val championships: Int) : BlaseballFeedMetadata()

    @Serializable
    class InvestigationUnderway(override val children: List<FeedID>) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class TripleThreat(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class UnderOver(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Undersea(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Walk(override val play: Int, override val subPlay: Int, override val children: List<FeedID>? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class WillReceived(val id: WillID, val title: String, override val children: List<FeedID>, val dataVotes: Int, val willVotes: Int, val totalVotes: Int) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class WillResults(override val parent: FeedID) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class Wired(override val play: Int, override val subPlay: Int, override val children: List<FeedID>) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ModificationTransition(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val from: ModificationID,
        val to: ModificationID,
        val type: Int
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable(NoMetadataSerialiser::class)
    object None : BlaseballFeedMetadata()

    @Serializable(UnknownMetadataSerialiser::class)
    class Unknown(val contents: Map<String, JsonElement>) : BlaseballFeedMetadata(), Map<String, JsonElement> by contents

//    @OptIn(InternalSerializationApi::class)
//    fun test() {
//        println("This: $this / ${this::class} / ${this::class.serializer() as KSerializer<BlaseballFeedMetadata>}}")
//    }

    @OptIn(InternalSerializationApi::class)
    fun serialise(encoder: CompositeEncoder, descriptor: SerialDescriptor, index: Int) {
//        val serializer =
        encoder.encodeSerializableElement(descriptor, index, this::class.serializer() as KSerializer<BlaseballFeedMetadata>, this)
    }
}

object UnknownMetadataSerialiser : KSerializer<BlaseballFeedMetadata.Unknown> {
    private object UnknownDescriptor : SerialDescriptor by serialDescriptor<HashMap<String, JsonElement>>() {
        @ExperimentalSerializationApi
        override val serialName: String = "BlaseballFeedMetadata.Unknown"
    }

    override val descriptor: SerialDescriptor = UnknownDescriptor

    override fun serialize(encoder: Encoder, value: BlaseballFeedMetadata.Unknown) {
        MapSerializer(String.serializer(), JsonElement.serializer()).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): BlaseballFeedMetadata.Unknown {
        return BlaseballFeedMetadata.Unknown(MapSerializer(String.serializer(), JsonElement.serializer()).deserialize(decoder))
    }
}

object NoMetadataSerialiser : KSerializer<BlaseballFeedMetadata.None> {
    // technically, JsonNull is an object, but it does not call beginStructure/endStructure at all
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("NoMetadata", SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: BlaseballFeedMetadata.None) {
        encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): BlaseballFeedMetadata.None {
        decoder.decodeNull()
        return BlaseballFeedMetadata.None
    }
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
object BlaseballFeedEventSerialiser : KSerializer<BlaseballFeedEvent> {
    val METADATA_TYPES =
        BlaseballFeedEventType.associateWith { type ->
            when (type) {
                BlaseballFeedEventType.LETS_GO -> serializer<BlaseballFeedMetadata.LetsGo>()
                BlaseballFeedEventType.PLAY_BALL -> serializer<BlaseballFeedMetadata.Play>()
                BlaseballFeedEventType.HALF_INNING -> serializer<BlaseballFeedMetadata.HalfInning>()
                BlaseballFeedEventType.PITCHER_CHANGE -> serializer<BlaseballFeedMetadata.PitcherChange>()
                BlaseballFeedEventType.STOLEN_BASE -> serializer<BlaseballFeedMetadata.StolenBase>()
                BlaseballFeedEventType.WALK -> serializer<BlaseballFeedMetadata.Walk>()
                BlaseballFeedEventType.STRIKEOUT -> serializer<BlaseballFeedMetadata.Strikeout>()
                BlaseballFeedEventType.FLYOUT -> serializer<BlaseballFeedMetadata.Flyout>()
                BlaseballFeedEventType.GROUND_OUT -> serializer<BlaseballFeedMetadata.GroundOut>()
                BlaseballFeedEventType.HOME_RUN -> serializer<BlaseballFeedMetadata.HomeRun>()
                BlaseballFeedEventType.HIT -> serializer<BlaseballFeedMetadata.Hit>()
                BlaseballFeedEventType.GAME_END_LOG -> serializer<BlaseballFeedMetadata.GameEndLog>()
                BlaseballFeedEventType.PLATE_APPEARANCE -> serializer<BlaseballFeedMetadata.PlateAppearance>()
                BlaseballFeedEventType.STRIKE -> serializer<BlaseballFeedMetadata.Strike>()
                BlaseballFeedEventType.BALL -> serializer<BlaseballFeedMetadata.Ball>()
                BlaseballFeedEventType.FOUL_BALL -> serializer<BlaseballFeedMetadata.FoulBall>()
                BlaseballFeedEventType.SOLAR_PANEL_OVERFLOW_RUNS -> serializer<BlaseballFeedMetadata.SolarPanelOverflowRuns>()
                BlaseballFeedEventType.PLAYER_HITS_ANOTHER_WITH_PITCH -> serializer<BlaseballFeedMetadata.PlayerHitsAnotherWithPitch>()
                BlaseballFeedEventType.PLAYER_SKIPPED_DUE_TO_SHELL -> serializer<BlaseballFeedMetadata.PlayerSkippedShelledOrElsewhere>()
                BlaseballFeedEventType.PARTYING -> serializer<BlaseballFeedMetadata.Partying>()
                BlaseballFeedEventType.STRIKE_ZAPPED_BY_ELECTRIC_BLOOD -> serializer<BlaseballFeedMetadata.StrikeZappedElectricBlood>()
                BlaseballFeedEventType.MILD_PITCH -> serializer<BlaseballFeedMetadata.MildPitch>()
                BlaseballFeedEventType.END_OF_INNING -> serializer<BlaseballFeedMetadata.EndOfInning>()
                BlaseballFeedEventType.SITE_TAKEOVER_TEXT -> serializer<BlaseballFeedMetadata.SiteTakeoverText>()

                BlaseballFeedEventType.BLACK_HOLE -> NoMetadataSerialiser
                BlaseballFeedEventType.SUN_2 -> NoMetadataSerialiser
                BlaseballFeedEventType.BIRDS_FLAVOUR_TEXT -> serializer<BlaseballFeedMetadata.BirdsFlavourText>()
                BlaseballFeedEventType.MURDER_OF_CROWS -> serializer<BlaseballFeedMetadata.MurderOfCrows>()
                BlaseballFeedEventType.BIRDS_FREE_SHELLED_PLAYER -> serializer<BlaseballFeedMetadata.BirdsFreeShelledPlayer>()
                BlaseballFeedEventType.TRIPLE_THREAT -> serializer<BlaseballFeedMetadata.TripleThreat>()
                BlaseballFeedEventType.FREE_REFILL -> serializer<BlaseballFeedMetadata.FreeRefill>()
                BlaseballFeedEventType.WIRED -> serializer<BlaseballFeedMetadata.Wired>()
                BlaseballFeedEventType.FEEDBACK_BLOCKED -> serializer<BlaseballFeedMetadata.FeedbackBlocked>()
                BlaseballFeedEventType.FEEDBACK -> serializer<BlaseballFeedMetadata.Feedback>()
                BlaseballFeedEventType.ALLERGIC_REACTION -> serializer<BlaseballFeedMetadata.AllergicReaction>()
                BlaseballFeedEventType.REVERBERATING -> serializer<BlaseballFeedMetadata.Reverberating>()
                BlaseballFeedEventType.REVERB_SHUFFLE -> serializer<BlaseballFeedMetadata.ReverbShuffle>()
                BlaseballFeedEventType.BLOODDRAIN -> serializer<BlaseballFeedMetadata.BloodDrain>()
                BlaseballFeedEventType.SIPHON -> serializer<BlaseballFeedMetadata.Siphon>()
                BlaseballFeedEventType.INCINERATION -> serializer<BlaseballFeedMetadata.Incineration>()
                BlaseballFeedEventType.FIRE_EATING -> serializer<BlaseballFeedMetadata.FireEating>()
                BlaseballFeedEventType.FLAG_PLANTED -> serializer<BlaseballFeedMetadata.FlagPlanted>()
                BlaseballFeedEventType.RENOVATION_BUILT -> serializer<BlaseballFeedMetadata.RenovationBuiltInt>()
                BlaseballFeedEventType.DECREE_PASSED -> serializer<BlaseballFeedMetadata.DecreePassed>()
                BlaseballFeedEventType.BLESSING_WON -> serializer<BlaseballFeedMetadata.BlessingWon>()
                BlaseballFeedEventType.WILL_RECEIVED -> serializer<BlaseballFeedMetadata.WillReceived>()
                BlaseballFeedEventType.FLOOD -> serializer<BlaseballFeedMetadata.Flooding>()
                BlaseballFeedEventType.SALMON_SWIM_UPSTREAM -> serializer<BlaseballFeedMetadata.SalmonSwimUpstream>()

                BlaseballFeedEventType.PLAYER_ENTERS_SECRET_BASE -> serializer<BlaseballFeedMetadata.PlayerEntersSecretBase>()
                BlaseballFeedEventType.PLAYER_EXITS_SECRET_BASE -> serializer<BlaseballFeedMetadata.PlayerExitsSecretBase>()
                BlaseballFeedEventType.CONSUMERS_ATTACK_PLAYER -> serializer<BlaseballFeedMetadata.ConsumersAttack>()

                BlaseballFeedEventType.ECHO_CHAMBER_TRAPS_WAVE -> serializer<BlaseballFeedMetadata.EchoChamberTrapsWave>()
                BlaseballFeedEventType.PLAYER_HOPS_ON_GRIND_RAIL -> serializer<BlaseballFeedMetadata.PlayerHopsOnGrindRail>()

                BlaseballFeedEventType.PEANUT_MISTER -> serializer<BlaseballFeedMetadata.PeanutMister>()
                BlaseballFeedEventType.PEANUTS_FLAVOUR_TEXT -> serializer<BlaseballFeedMetadata.PeanutFlavourText>()

                /** Shelling */
                BlaseballFeedEventType.TASTING_THE_INFINITE -> serializer<BlaseballFeedMetadata.TastingTheInfinite>()
                BlaseballFeedEventType.SOLAR_PANEL_ALIGNMENT -> serializer<BlaseballFeedMetadata.SolarPanelAlignment>()
                BlaseballFeedEventType.SOLAR_PANEL_RUN_COLLECTION -> serializer<BlaseballFeedMetadata.SolarPanelRunCollection>()

                BlaseballFeedEventType.TAROT_READING -> serializer<BlaseballFeedMetadata.TarotReading>()
                BlaseballFeedEventType.EMERGENCY_ALERT -> NoMetadataSerialiser
                BlaseballFeedEventType.RETURN_FROM_ELSEWHERE -> serializer<BlaseballFeedMetadata.ReturnFromElsewhere>()
                BlaseballFeedEventType.OVER_UNDER -> serializer<BlaseballFeedMetadata.OverUnder>()
                BlaseballFeedEventType.UNDER_OVER -> serializer<BlaseballFeedMetadata.UnderOver>()

                BlaseballFeedEventType.UNDERSEA -> serializer<BlaseballFeedMetadata.Undersea>()

                BlaseballFeedEventType.HOMESICK -> serializer<BlaseballFeedMetadata.Homesick>()
                BlaseballFeedEventType.SUPERYUMMY_TEXT -> serializer<BlaseballFeedMetadata.SuperyummyText>()
                BlaseballFeedEventType.PERK -> serializer<BlaseballFeedMetadata.Perk>()

                BlaseballFeedEventType.EARLBIRDS -> serializer<BlaseballFeedMetadata.EarlBirds>()
                BlaseballFeedEventType.LATE_TO_PARTY -> serializer<BlaseballFeedMetadata.LateToTheParty>()

                /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                BlaseballFeedEventType.ADDED_INGAME_MODIFIER -> serializer<BlaseballFeedMetadata.AddedInGameModifier>()

                /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                BlaseballFeedEventType.REMOVED_INGAME_MODIFIER -> serializer<BlaseballFeedMetadata.RemovedInGameModifier>()

                BlaseballFeedEventType.MODIFIER_EXPIRES -> serializer<BlaseballFeedMetadata.ModifierExpires>()

                /** Including Postseason Births */
                BlaseballFeedEventType.PLAYER_RECRUITED -> serializer<BlaseballFeedMetadata.PlayerRecruited>()

                /** Necromancy */
                BlaseballFeedEventType.PLAYER_SENT_TO_SHADOWS_AND_REPLACED -> serializer<BlaseballFeedMetadata.PlayerSentToShadowsAndReplaced>()

                /** Returned */
                BlaseballFeedEventType.PLAYER_REMOVED_AND_REPLACED_FROM_SHADOWS -> serializer<BlaseballFeedMetadata.PlayerRemovedAndReplacedFromShadows>()
                BlaseballFeedEventType.ECHO_PLAYER_STATIC_CHILD -> serializer<BlaseballFeedMetadata.EchoPlayerStaticChild>()
                BlaseballFeedEventType.PLAYER_TRADE -> serializer<BlaseballFeedMetadata.PlayerTrade>()
                BlaseballFeedEventType.PLAYER_CHANGING_POSITION -> serializer<BlaseballFeedMetadata.PlayerChangingPosition>()
                BlaseballFeedEventType.PLAYER_JOINING_TEAM -> serializer<BlaseballFeedMetadata.PlayerJoiningTeam>()
                BlaseballFeedEventType.NEW_PLAYER_AFTER_INCINERATION -> serializer<BlaseballFeedMetadata.NewPlayerAfterIncineration>()
                BlaseballFeedEventType.PLAYER_STAT_INCREASE -> serializer<BlaseballFeedMetadata.PlayerStatIncrease>()
                BlaseballFeedEventType.PLAYER_STAT_DECREASE -> serializer<BlaseballFeedMetadata.PlayerStatDecrease>()
                BlaseballFeedEventType.PLAYER_REROLL -> serializer<BlaseballFeedMetadata.PlayerReroll>()

                BlaseballFeedEventType.PLAYER_ENTERS_HALL_OF_FLAME -> serializer<BlaseballFeedMetadata.PlayerEntersHallOfFlame>()
                BlaseballFeedEventType.PLAYER_EXITS_HALL_OF_FLAME -> serializer<BlaseballFeedMetadata.PlayerExitsHallOfFlame>()
                BlaseballFeedEventType.PLAYER_GAINED_ITEM -> serializer<BlaseballFeedMetadata.PlayerGainedItem>()
                BlaseballFeedEventType.PLAYER_DROPPED_ITEM -> serializer<BlaseballFeedMetadata.PlayerDroppedItem>()

                BlaseballFeedEventType.REVERB_SHUFFLE_FULL -> serializer<BlaseballFeedMetadata.ReverbShuffleFull>()
                BlaseballFeedEventType.REVERB_SHUFFLE_ROTATION -> serializer<BlaseballFeedMetadata.ReverbShuffleRotation>()

                BlaseballFeedEventType.NEW_TEAM -> serializer<BlaseballFeedMetadata.NewTeam>()

                /** Excluding Incinerations */
                BlaseballFeedEventType.NEW_PLAYER -> serializer<BlaseballFeedMetadata.NewPlayer>()
                BlaseballFeedEventType.PLAYER_HATCHED -> serializer<BlaseballFeedMetadata.PlayerHatched>()

                BlaseballFeedEventType.PLAYER_EVOLVES -> serializer<BlaseballFeedMetadata.PlayerEvolves>()
                BlaseballFeedEventType.TEAM_WINS_INTERNET_SERIES -> serializer<BlaseballFeedMetadata.TeamWinsInternetSeries>()
                BlaseballFeedEventType.POSTSEASON_SPOT -> NoMetadataSerialiser
                BlaseballFeedEventType.FINAL_STANDINGS -> serializer<BlaseballFeedMetadata.FinalStandings>()
                BlaseballFeedEventType.WIRED_TO_TIRED -> serializer<BlaseballFeedMetadata.ModificationTransition>()
                BlaseballFeedEventType.PLAYER_TO_ALTERNATE -> serializer<BlaseballFeedMetadata.PlayerBecomesAlternate>()
                BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_MODIFIER -> serializer<BlaseballFeedMetadata.AddedModifierDueToAnotherModifier>()
                BlaseballFeedEventType.REMOVED_MODIFIER_DUE_TO_MODIFIER -> serializer<BlaseballFeedMetadata.RemovedModifierDueToAnotherModifier>()
                BlaseballFeedEventType.SUPERYUMMY_TRANSITIONS -> serializer<BlaseballFeedMetadata.SuperyummyTransition>()
                BlaseballFeedEventType.NECROMANCY_NARRATION -> serializer<BlaseballFeedMetadata.NecromancyNarration>()
                BlaseballFeedEventType.RETURNED_PLAYER_PERMITTED_TO_STAY -> NoMetadataSerialiser
                BlaseballFeedEventType.DECREE_NARRATION -> serializer<BlaseballFeedMetadata.DecreeNarration>()
                BlaseballFeedEventType.WILL_RESULTS -> serializer<BlaseballFeedMetadata.WillResults>()
                BlaseballFeedEventType.BLESSING_RESULTS -> serializer<BlaseballFeedMetadata.BlessingResults>()
                BlaseballFeedEventType.TEAM_SHAMED -> serializer<BlaseballFeedMetadata.TeamShamed>()
                BlaseballFeedEventType.TEAM_SHAMES -> serializer<BlaseballFeedMetadata.TeamShames>()
                BlaseballFeedEventType.SUN_2_GRANTS_WIN -> NoMetadataSerialiser
                BlaseballFeedEventType.BLACK_HOLE_SWALLOWS -> NoMetadataSerialiser
                BlaseballFeedEventType.ELIMINATED_FROM_POSTSEASON -> NoMetadataSerialiser
                BlaseballFeedEventType.POSTSEASON_ADVANCE -> NoMetadataSerialiser

                BlaseballFeedEventType.PLAYER_GAINED_BLOOD_TYPE -> serializer<BlaseballFeedMetadata.PlayerGainedBloodType>()

                BlaseballFeedEventType.TEAM_OVERPERFORMING -> serializer<BlaseballFeedMetadata.TeamOverperforming>()
                BlaseballFeedEventType.LINEUP_OPTIMISED -> serializer<BlaseballFeedMetadata.LineupOrganised>()

                BlaseballFeedEventType.PEANUT_ALLERGY_CURED -> serializer<BlaseballFeedMetadata.PeanutAllergyCured>()
                BlaseballFeedEventType.PLAYER_ECHOED -> serializer<BlaseballFeedMetadata.PlayerEchoed>()
                BlaseballFeedEventType.ECHO_PLAYER_STATIC -> serializer<BlaseballFeedMetadata.EchoPlayerStatic>()
                BlaseballFeedEventType.ECHO_FADES -> serializer<BlaseballFeedMetadata.EchoFades>()
                BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_ECHO -> serializer<BlaseballFeedMetadata.AddedModifierDueToEcho>()
                BlaseballFeedEventType.PSYCHOACOUSTICS_ECHO -> serializer<BlaseballFeedMetadata.PsychoacousticsEcho>()
                BlaseballFeedEventType.ECHO_PLAYER_ECHO_PLAYER_ECHO -> serializer<BlaseballFeedMetadata.EchoPlayerStatic>()
                BlaseballFeedEventType.ALTERNATE_COIN_TEXT -> serializer<BlaseballFeedMetadata.AlternateCoinText>()
                BlaseballFeedEventType.INVESTIGATION_UNDERWAY -> serializer<BlaseballFeedMetadata.InvestigationUnderway>()
                BlaseballFeedEventType.PLAYER_OPENS_CRATE -> serializer<BlaseballFeedMetadata.PlayerOpensCrate>()
                BlaseballFeedEventType.TEAM_MIDDLING -> serializer<BlaseballFeedMetadata.TeamMiddling>()
                BlaseballFeedEventType.PLAYER_STAT_INCREASED_PERCENT -> serializer<BlaseballFeedMetadata.PlayerStatIncreaseByPercent>()
                BlaseballFeedEventType.PLAYER_STAT_DECREASED_PERCENT -> serializer<BlaseballFeedMetadata.PlayerStatDecreaseByPercent>()
                BlaseballFeedEventType.PLAYER_ENTERS_CRIME_SCENE_TO_INVESTIGATE -> serializer<BlaseballFeedMetadata.PlayerEntersCrimeScene>()

                BlaseballFeedEventType.PLAYERS_ITEM_BROKE -> serializer<BlaseballFeedMetadata.PlayersItemBroke>()
                BlaseballFeedEventType.PLAYERS_ITEM_DAMAGED -> serializer<BlaseballFeedMetadata.PlayersItemDamaged>()
                BlaseballFeedEventType.PLAYERS_ITEM_RESTORED -> serializer<BlaseballFeedMetadata.PlayersItemRestored>()
                BlaseballFeedEventType.PLAYERS_ITEM_REPAIRED -> serializer<BlaseballFeedMetadata.PlayersItemRepaired>()

                else -> UnknownMetadataSerialiser
            }
        } + mapOf(-1 to UnknownMetadataSerialiser)

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
            METADATA_TYPES.forEach { (k, v) -> element(if (k == -1) "Unknown" else BlaseballFeedEventType.textFromType(k), v.descriptor) }
        }, isOptional = true)
    }

    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): BlaseballFeedEvent =
        decoder.decodeStructure(descriptor) {
            val builder = BlaseballFeedEvent.Builder()
            var metaJson: JsonElement? = null

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
                        if (builder.type == null) {
                            when (this) {
                                is JsonDecoder -> {
                                    metaJson = decodeJsonElement()
                                    continue
                                }
                                else -> throw IllegalStateException("Metadata initialised before type was read!")
                            }
                        } else {
                            builder.metadata = decodeSerializableElement(descriptor, index, METADATA_TYPES[builder.type] ?: METADATA_TYPES.getValue(-1))
                        }
                    }
                    DECODE_DONE -> break // Input is over
                    else -> error("Unexpected index: $index")
                }
            }

            if (metaJson != null && this is JsonDecoder) {
//                println("==[Json Hacking]==")
                builder.metadata = json.decodeFromString(METADATA_TYPES[builder.type] ?: METADATA_TYPES.getValue(-1), json.encodeToString(metaJson))
//                println("==[Json Hacked]==")
            }

            return builder.build()
        }

    override fun serialize(encoder: Encoder, value: BlaseballFeedEvent) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, descriptor.getElementIndex("type"), value.type)
            encodeInlineElement(descriptor, descriptor.getElementIndex("id"), value.id)
            encodeSerializableElement(descriptor, descriptor.getElementIndex("playerTags"), ListSerializer(serializer()), value.playerTags)
            encodeSerializableElement(descriptor, descriptor.getElementIndex("teamTags"), ListSerializer(serializer()), value.teamTags)
            encodeSerializableElement(descriptor, descriptor.getElementIndex("gameTags"), ListSerializer(serializer()), value.gameTags)
            encodeStringElement(descriptor, descriptor.getElementIndex("created"), value.created)
            encodeIntElement(descriptor, descriptor.getElementIndex("season"), value.season)
            encodeIntElement(descriptor, descriptor.getElementIndex("tournament"), value.tournament)
            encodeIntElement(descriptor, descriptor.getElementIndex("day"), value.day)
            encodeIntElement(descriptor, descriptor.getElementIndex("phase"), value.phase)
            encodeIntElement(descriptor, descriptor.getElementIndex("category"), value.category)
            encodeStringElement(descriptor, descriptor.getElementIndex("description"), value.description)
            encodeIntElement(descriptor, descriptor.getElementIndex("nuts"), value.nuts)

//            encodeSerializableElement(descriptor, descriptor.getElementIndex("metadata"), metadataSerialiser, value.metadata)

            value.metadata?.serialise(this, descriptor, descriptor.getElementIndex("metadata"))
        }
    }
}

