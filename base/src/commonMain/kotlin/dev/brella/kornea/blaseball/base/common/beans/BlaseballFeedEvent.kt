package dev.brella.kornea.blaseball.base.common.beans

import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.parse
import dev.brella.kornea.blaseball.*
import dev.brella.kornea.blaseball.base.common.BLASEBALL_TIME_PATTERN
import dev.brella.kornea.blaseball.base.common.BlaseballFeedEventType
import dev.brella.kornea.blaseball.base.common.FeedID
import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.PlayerID
import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.decodeInlineElement
import dev.brella.kornea.blaseball.base.common.encodeInlineElement
import dev.brella.kornea.blaseball.base.common.json.BlaseballDateTimeSerialiser
import dev.brella.kornea.blaseball.beans.BlaseballFeedMetadata
import dev.brella.kornea.blaseball.beans.NoMetadataSerialiser
import dev.brella.kornea.blaseball.beans.UnknownMetadataSerialiser
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

@Serializable(BlaseballFeedEventSerialiser::class)
sealed class BlaseballFeedEvent {
    abstract val id: FeedID
    abstract val playerTags: List<PlayerID>
    abstract val teamTags: List<TeamID>
    abstract val gameTags: List<GameID>
    abstract val metadata: BlaseballFeedMetadata?
    abstract val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
        override val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
                    created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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
                    BlaseballFeedEventType.LETS_GO -> buildConstructor(BlaseballFeedEvent::LetsGo)
                    BlaseballFeedEventType.PLAY_BALL -> buildConstructor(BlaseballFeedEvent::Play)
                    BlaseballFeedEventType.HALF_INNING -> buildConstructor(BlaseballFeedEvent::HalfInning)
                    BlaseballFeedEventType.PITCHER_CHANGE -> buildConstructor(BlaseballFeedEvent::PitcherChange)
                    BlaseballFeedEventType.STOLEN_BASE -> buildConstructor(BlaseballFeedEvent::StolenBase)
                    BlaseballFeedEventType.WALK -> buildConstructor(BlaseballFeedEvent::Walk)
                    BlaseballFeedEventType.STRIKEOUT -> buildConstructor(BlaseballFeedEvent::Strikeout)
                    BlaseballFeedEventType.FLYOUT -> buildConstructor(BlaseballFeedEvent::Flyout)
                    BlaseballFeedEventType.GROUND_OUT -> buildConstructor(BlaseballFeedEvent::GroundOut)
                    BlaseballFeedEventType.HOME_RUN -> buildConstructor(BlaseballFeedEvent::HomeRun)
                    BlaseballFeedEventType.HIT -> buildConstructor(BlaseballFeedEvent::Hit)
                    BlaseballFeedEventType.GAME_END_LOG -> buildConstructor(BlaseballFeedEvent::GameEndLog)
                    BlaseballFeedEventType.PLATE_APPEARANCE -> buildConstructor(BlaseballFeedEvent::PlateAppearance)
                    BlaseballFeedEventType.STRIKE -> buildConstructor(BlaseballFeedEvent::Strike)
                    BlaseballFeedEventType.BALL -> buildConstructor(BlaseballFeedEvent::Ball)
                    BlaseballFeedEventType.FOUL_BALL -> buildConstructor(BlaseballFeedEvent::FoulBall)
                    BlaseballFeedEventType.SOLAR_PANEL_OVERFLOW_RUNS -> buildConstructor(BlaseballFeedEvent::SolarPanelOverflowRuns)
                    BlaseballFeedEventType.PLAYER_HITS_ANOTHER_WITH_PITCH -> buildConstructor(BlaseballFeedEvent::PlayerHitsAnotherWithPitch)
                    BlaseballFeedEventType.PLAYER_SKIPPED_DUE_TO_SHELL -> buildConstructor(BlaseballFeedEvent::PlayerSkippedShelledOrElsewhere)
                    BlaseballFeedEventType.PARTYING -> buildConstructor(BlaseballFeedEvent::Partying)
                    BlaseballFeedEventType.STRIKE_ZAPPED_BY_ELECTRIC_BLOOD -> buildConstructor(BlaseballFeedEvent::StrikeZappedElectricBlood)
                    BlaseballFeedEventType.MILD_PITCH -> buildConstructor(BlaseballFeedEvent::MildPitch)
                    BlaseballFeedEventType.END_OF_INNING -> buildConstructor(BlaseballFeedEvent::EndOfInning)
                    BlaseballFeedEventType.SITE_TAKEOVER_TEXT -> buildConstructor(BlaseballFeedEvent::SiteTakeoverText)

                    BlaseballFeedEventType.BLACK_HOLE -> buildConstructor(BlaseballFeedEvent::BlackHoleInGame)
                    BlaseballFeedEventType.SUN_2 -> buildConstructor(BlaseballFeedEvent::Sun2InGame)
                    BlaseballFeedEventType.BIRDS_FLAVOUR_TEXT -> buildConstructor(BlaseballFeedEvent::BirdsFlavourText)
                    BlaseballFeedEventType.MURDER_OF_CROWS -> buildConstructor(BlaseballFeedEvent::MurderOfCrows)
                    BlaseballFeedEventType.BIRDS_FREE_SHELLED_PLAYER -> buildConstructor(BlaseballFeedEvent::BirdsFreeShelledPlayer)
                    BlaseballFeedEventType.TRIPLE_THREAT -> buildConstructor(BlaseballFeedEvent::TripleThreat)
                    BlaseballFeedEventType.FREE_REFILL -> buildConstructor(BlaseballFeedEvent::FreeRefill)
                    BlaseballFeedEventType.WIRED -> buildConstructor(BlaseballFeedEvent::Wired)
                    BlaseballFeedEventType.FEEDBACK_BLOCKED -> buildConstructor(BlaseballFeedEvent::FeedbackBlocked)
                    BlaseballFeedEventType.FEEDBACK -> buildConstructor(BlaseballFeedEvent::Feedback)
                    BlaseballFeedEventType.ALLERGIC_REACTION -> buildConstructor(BlaseballFeedEvent::AllergicReaction)
                    BlaseballFeedEventType.REVERBERATING -> buildConstructor(BlaseballFeedEvent::Reverberating)
                    BlaseballFeedEventType.REVERB_SHUFFLE -> buildConstructor(BlaseballFeedEvent::ReverbShuffle)
                    BlaseballFeedEventType.BLOODDRAIN -> buildConstructor(BlaseballFeedEvent::BloodDrain)
                    BlaseballFeedEventType.SIPHON -> buildConstructor(BlaseballFeedEvent::Siphon)
                    BlaseballFeedEventType.INCINERATION -> buildConstructor(BlaseballFeedEvent::Incineration)
                    BlaseballFeedEventType.FIRE_EATING -> buildConstructor(BlaseballFeedEvent::FireEating)
                    BlaseballFeedEventType.FLAG_PLANTED -> buildConstructor(BlaseballFeedEvent::FlagPlanted)
                    BlaseballFeedEventType.RENOVATION_BUILT -> buildConstructor(BlaseballFeedEvent::RenovationBuiltInt)
                    BlaseballFeedEventType.DECREE_PASSED -> buildConstructor(BlaseballFeedEvent::DecreePassed)
                    BlaseballFeedEventType.BLESSING_WON -> buildConstructor(BlaseballFeedEvent::BlessingWon)
                    BlaseballFeedEventType.WILL_RECEIVED -> buildConstructor(BlaseballFeedEvent::WillReceived)
                    BlaseballFeedEventType.FLOOD -> buildConstructor(BlaseballFeedEvent::Flooding)
                    BlaseballFeedEventType.SALMON_SWIM_UPSTREAM -> buildConstructor(BlaseballFeedEvent::SalmonSwimUpstream)

                    BlaseballFeedEventType.PLAYER_ENTERS_SECRET_BASE -> buildConstructor(BlaseballFeedEvent::PlayerEntersSecretBase)
                    BlaseballFeedEventType.PLAYER_EXITS_SECRET_BASE -> buildConstructor(BlaseballFeedEvent::PlayerExitsSecretBase)
                    BlaseballFeedEventType.CONSUMERS_ATTACK_PLAYER -> buildConstructor(BlaseballFeedEvent::ConsumersAttack)

                    BlaseballFeedEventType.ECHO_CHAMBER_TRAPS_WAVE -> buildConstructor(BlaseballFeedEvent::EchoChamberTrapsWave)
                    BlaseballFeedEventType.PLAYER_HOPS_ON_GRIND_RAIL -> buildConstructor(BlaseballFeedEvent::PlayerHopsOnGrindRail)

                    BlaseballFeedEventType.PEANUT_MISTER -> buildConstructor(BlaseballFeedEvent::PeanutMister)
                    BlaseballFeedEventType.PEANUTS_FLAVOUR_TEXT -> buildConstructor(BlaseballFeedEvent::PeanutFlavourText)

                    /** Shelling */
                    BlaseballFeedEventType.TASTING_THE_INFINITE -> buildConstructor(BlaseballFeedEvent::TastingTheInfinite)
                    BlaseballFeedEventType.SOLAR_PANEL_ALIGNMENT -> buildConstructor(BlaseballFeedEvent::SolarPanelAlignment)
                    BlaseballFeedEventType.SOLAR_PANEL_RUN_COLLECTION -> buildConstructor(BlaseballFeedEvent::SolarPanelRunCollection)

                    BlaseballFeedEventType.TAROT_READING -> buildConstructor(BlaseballFeedEvent::TarotReading)
                    BlaseballFeedEventType.EMERGENCY_ALERT -> buildConstructor(BlaseballFeedEvent::EmergencyAlert)
                    BlaseballFeedEventType.RETURN_FROM_ELSEWHERE -> buildConstructor(BlaseballFeedEvent::ReturnFromElsewhere)
                    BlaseballFeedEventType.OVER_UNDER -> buildConstructor(BlaseballFeedEvent::OverUnder)
                    BlaseballFeedEventType.UNDER_OVER -> buildConstructor(BlaseballFeedEvent::UnderOver)

                    BlaseballFeedEventType.UNDERSEA -> buildConstructor(BlaseballFeedEvent::Undersea)

                    BlaseballFeedEventType.HOMESICK -> buildConstructor(BlaseballFeedEvent::Homesick)
                    BlaseballFeedEventType.SUPERYUMMY_TEXT -> buildConstructor(BlaseballFeedEvent::SuperyummyText)
                    BlaseballFeedEventType.PERK -> buildConstructor(BlaseballFeedEvent::Perk)

                    BlaseballFeedEventType.EARLBIRDS -> buildConstructor(BlaseballFeedEvent::EarlBirds)
                    BlaseballFeedEventType.LATE_TO_PARTY -> buildConstructor(BlaseballFeedEvent::LateToTheParty)

                    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                    BlaseballFeedEventType.ADDED_INGAME_MODIFIER -> buildConstructor(BlaseballFeedEvent::AddedInGameModifier)

                    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
                    BlaseballFeedEventType.REMOVED_INGAME_MODIFIER -> buildConstructor(BlaseballFeedEvent::RemovedInGameModifier)

                    BlaseballFeedEventType.MODIFIER_EXPIRES -> buildConstructor(BlaseballFeedEvent::ModifierExpires)

                    /** Including Postseason Births */
                    BlaseballFeedEventType.PLAYER_RECRUITED -> buildConstructor(BlaseballFeedEvent::PlayerRecruited)

                    /** Necromancy */
                    BlaseballFeedEventType.PLAYER_SENT_TO_SHADOWS_AND_REPLACED -> buildConstructor(BlaseballFeedEvent::PlayerSentToShadowsAndReplaced)

                    /** Returned */
                    BlaseballFeedEventType.PLAYER_REMOVED_AND_REPLACED_FROM_SHADOWS -> buildConstructor(BlaseballFeedEvent::PlayerRemovedAndReplacedFromShadows)
                    BlaseballFeedEventType.ECHO_PLAYER_STATIC_CHILD -> buildConstructor(BlaseballFeedEvent::EchoPlayerStaticChild)
                    BlaseballFeedEventType.PLAYER_TRADE -> buildConstructor(BlaseballFeedEvent::PlayerTrade)
                    BlaseballFeedEventType.PLAYER_CHANGING_POSITION -> buildConstructor(BlaseballFeedEvent::PlayerChangingPosition)
                    BlaseballFeedEventType.PLAYER_JOINING_TEAM -> buildConstructor(BlaseballFeedEvent::PlayerJoiningTeam)
                    BlaseballFeedEventType.NEW_PLAYER_AFTER_INCINERATION -> buildConstructor(BlaseballFeedEvent::NewPlayerAfterIncineration)
                    BlaseballFeedEventType.PLAYER_STAT_INCREASE -> buildConstructor(BlaseballFeedEvent::PlayerStatIncrease)
                    BlaseballFeedEventType.PLAYER_STAT_DECREASE -> buildConstructor(BlaseballFeedEvent::PlayerStatDecrease)
                    BlaseballFeedEventType.PLAYER_REROLL -> buildConstructor(BlaseballFeedEvent::PlayerReroll)

                    BlaseballFeedEventType.PLAYER_ENTERS_HALL_OF_FLAME -> buildConstructor(BlaseballFeedEvent::PlayerEntersHallOfFlame)
                    BlaseballFeedEventType.PLAYER_EXITS_HALL_OF_FLAME -> buildConstructor(BlaseballFeedEvent::PlayerExitsHallOfFlame)
                    BlaseballFeedEventType.PLAYER_GAINED_ITEM -> buildConstructor(BlaseballFeedEvent::PlayerGainedItem)
                    BlaseballFeedEventType.PLAYER_DROPPED_ITEM -> buildConstructor(BlaseballFeedEvent::PlayerDroppedItem)

                    BlaseballFeedEventType.REVERB_SHUFFLE_FULL -> buildConstructor(BlaseballFeedEvent::ReverbShuffleFull)
                    BlaseballFeedEventType.REVERB_SHUFFLE_ROTATION -> buildConstructor(BlaseballFeedEvent::ReverbShuffleRotation)

                    BlaseballFeedEventType.NEW_TEAM -> buildConstructor(BlaseballFeedEvent::NewTeam)

                    /** Excluding Incinerations */
                    BlaseballFeedEventType.NEW_PLAYER -> buildConstructor(BlaseballFeedEvent::NewPlayer)
                    BlaseballFeedEventType.PLAYER_HATCHED -> buildConstructor(BlaseballFeedEvent::PlayerHatched)

                    BlaseballFeedEventType.PLAYER_EVOLVES -> buildConstructor(BlaseballFeedEvent::PlayerEvolves)
                    BlaseballFeedEventType.TEAM_WINS_INTERNET_SERIES -> buildConstructor(BlaseballFeedEvent::TeamWinsInternetSeries)
                    BlaseballFeedEventType.POSTSEASON_SPOT -> buildConstructor(BlaseballFeedEvent::PostseasonSpot)
                    BlaseballFeedEventType.FINAL_STANDINGS -> buildConstructor(BlaseballFeedEvent::FinalStandings)
                    BlaseballFeedEventType.WIRED_TO_TIRED -> buildConstructor(BlaseballFeedEvent::ModificationTransition)
                    BlaseballFeedEventType.PLAYER_TO_ALTERNATE -> buildConstructor(BlaseballFeedEvent::PlayerBecomesAlternate)
                    BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_MODIFIER -> buildConstructor(BlaseballFeedEvent::AddedModifierDueToAnotherModifier)
                    BlaseballFeedEventType.REMOVED_MODIFIER_DUE_TO_MODIFIER -> buildConstructor(BlaseballFeedEvent::RemovedModifierDueToAnotherModifier)
                    BlaseballFeedEventType.SUPERYUMMY_TRANSITIONS -> buildConstructor(BlaseballFeedEvent::SuperyummyTransition)
                    BlaseballFeedEventType.NECROMANCY_NARRATION -> buildConstructor(BlaseballFeedEvent::NecromancyNarration)
                    BlaseballFeedEventType.RETURNED_PLAYER_PERMITTED_TO_STAY -> buildConstructor(BlaseballFeedEvent::ReturnedPlayerPermittedToStay)
                    BlaseballFeedEventType.DECREE_NARRATION -> buildConstructor(BlaseballFeedEvent::DecreeNarration)
                    BlaseballFeedEventType.WILL_RESULTS -> buildConstructor(BlaseballFeedEvent::WillResults)
                    BlaseballFeedEventType.BLESSING_RESULTS -> buildConstructor(BlaseballFeedEvent::BlessingResults)
                    BlaseballFeedEventType.TEAM_SHAMED -> buildConstructor(BlaseballFeedEvent::TeamShamed)
                    BlaseballFeedEventType.TEAM_SHAMES -> buildConstructor(BlaseballFeedEvent::TeamShames)
                    BlaseballFeedEventType.SUN_2_GRANTS_WIN -> buildConstructor(BlaseballFeedEvent::Sun2GrantsWin)
                    BlaseballFeedEventType.BLACK_HOLE_SWALLOWS -> buildConstructor(BlaseballFeedEvent::BlackHoleSwallows)
                    BlaseballFeedEventType.ELIMINATED_FROM_POSTSEASON -> buildConstructor(BlaseballFeedEvent::EliminatedFromPostseason)
                    BlaseballFeedEventType.POSTSEASON_ADVANCE -> buildConstructor(BlaseballFeedEvent::PostseasonAdvance)

                    BlaseballFeedEventType.PLAYER_GAINED_BLOOD_TYPE -> buildConstructor(BlaseballFeedEvent::PlayerGainedBloodType)

                    BlaseballFeedEventType.TEAM_OVERPERFORMING -> buildConstructor(BlaseballFeedEvent::TeamOverperforming)
                    BlaseballFeedEventType.LINEUP_OPTIMISED -> buildConstructor(BlaseballFeedEvent::LineupOrganised)

                    BlaseballFeedEventType.PEANUT_ALLERGY_CURED -> buildConstructor(BlaseballFeedEvent::PeanutAllergyCured)
                    BlaseballFeedEventType.PLAYER_ECHOED -> buildConstructor(BlaseballFeedEvent::PlayerEchoed)
                    BlaseballFeedEventType.ECHO_PLAYER_STATIC -> buildConstructor(BlaseballFeedEvent::EchoPlayerStatic)
                    BlaseballFeedEventType.ECHO_FADES -> buildConstructor(BlaseballFeedEvent::EchoFades)
                    BlaseballFeedEventType.ADDED_MODIFIER_DUE_TO_ECHO -> buildConstructor(BlaseballFeedEvent::AddedModifierDueToEcho)
                    BlaseballFeedEventType.PSYCHOACOUSTICS_ECHO -> buildConstructor(BlaseballFeedEvent::PsychoacousticsEcho)
                    BlaseballFeedEventType.ECHO_PLAYER_ECHO_PLAYER_ECHO -> buildConstructor(BlaseballFeedEvent::EchoPlayerStatic)
                    BlaseballFeedEventType.ALTERNATE_COIN_TEXT -> buildConstructor(BlaseballFeedEvent::AlternateCoinText)
                    BlaseballFeedEventType.INVESTIGATION_UNDERWAY -> buildConstructor(BlaseballFeedEvent::InvestigationUnderway)
                    BlaseballFeedEventType.PLAYER_OPENS_CRATE -> buildConstructor(BlaseballFeedEvent::PlayerOpensCrate)
                    BlaseballFeedEventType.TEAM_MIDDLING -> buildConstructor(BlaseballFeedEvent::TeamMiddling)
                    BlaseballFeedEventType.PLAYER_STAT_INCREASED_PERCENT -> buildConstructor(BlaseballFeedEvent::PlayerStatIncreaseByPercent)
                    BlaseballFeedEventType.PLAYER_STAT_DECREASED_PERCENT -> buildConstructor(BlaseballFeedEvent::PlayerStatDecreaseByPercent)
                    BlaseballFeedEventType.PLAYER_ENTERS_CRIME_SCENE_TO_INVESTIGATE -> buildConstructor(BlaseballFeedEvent::PlayerEntersCrimeScene)

                    BlaseballFeedEventType.PLAYERS_ITEM_BROKE -> buildConstructor(BlaseballFeedEvent::PlayersItemBroke)
                    BlaseballFeedEventType.PLAYERS_ITEM_DAMAGED -> buildConstructor(BlaseballFeedEvent::PlayersItemDamaged)
                    BlaseballFeedEventType.PLAYERS_ITEM_RESTORED -> buildConstructor(BlaseballFeedEvent::PlayersItemRestored)
                    BlaseballFeedEventType.PLAYERS_ITEM_REPAIRED -> buildConstructor(BlaseballFeedEvent::PlayersItemRepaired)

                    else -> buildConstructor(BlaseballFeedEvent::Unknown)
                }
            } + mapOf(-1 to buildConstructor(BlaseballFeedEvent::Unknown))
        }

        var id: FeedID? = null
        var playerTags: List<PlayerID>? = null
        var teamTags: List<TeamID>? = null
        var gameTags: List<GameID>? = null
        var created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz? = null
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
            val created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz = created!!
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
    created: @Serializable(BlaseballDateTimeSerialiser::class) DateTimeTz,
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

                BlaseballFeedEventType.BLACK_HOLE -> serializer<BlaseballFeedMetadata.BlackHoleInGame>()
                BlaseballFeedEventType.SUN_2 -> serializer<BlaseballFeedMetadata.Sun2InGame>()
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
                    descriptor.getElementIndex("created") -> builder.created = BLASEBALL_TIME_PATTERN.parse(decodeStringElement(descriptor, index))
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
            encodeStringElement(descriptor, descriptor.getElementIndex("created"), BLASEBALL_TIME_PATTERN.format(value.created))
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

