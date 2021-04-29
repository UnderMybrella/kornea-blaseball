package dev.brella.kornea.blaseball.base.common.beans

import dev.brella.kornea.blaseball.base.common.*
import dev.brella.kornea.blaseball.base.common.json.CoercedIntSerialiser
import dev.brella.kornea.blaseball.base.common.json.UnwrappedSerialiser
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer

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
        val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>?

        interface AlwaysPresent : WithChildren {
            override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>
        }
    }

    interface WithParent {
        val parent: FeedID?

        interface AlwaysPresent : WithParent {
            override val parent: FeedID
        }
    }

    abstract var upnut: Boolean?

    @Serializable
    class AddedInGameModifier(override val play: Int? = null, override val subPlay: Int? = null, val parent: FeedID? = null, val mod: ModificationID, val type: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay

    @Serializable
    class AddedModifierDueToAnotherModifier(
        override val play: Int,
        override val subPlay: Int,
        val mod: ModificationID,
        val type: Int,
        val parent: FeedID,
        val source: ModificationID,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class AddedModifierDueToEcho(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val source: ModificationID,
        val adds: @Serializable(UnwrappedSerialiser::class) List<EchoModAddition>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent {
        @Serializable
        data class EchoModAddition(val mod: ModificationID, val type: Int)
    }

    @Serializable
    class AllergicReaction(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class AlternateCoinText(override val play: Int? = null, override val subPlay: Int? = null, override val parent: FeedID? = null, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class Ball(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class BirdsFlavourText(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class BirdsFreeShelledPlayer(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class BlackHoleInGame(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class BlessingWon(
        val id: BlessingID,
        val title: String,
        val votes: Int? = null,
        val teamId: TeamID,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        val teamName: String,
        val totalVotes: Int? = null,
        val highestTeam: TeamID,
        val highestVotes: Int? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class BlessingResults(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class BloodDrain(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ConsumersAttack(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class DecreeNarration(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class DecreePassed(
        val id: DecreeID,
        val title: String,
        val votes: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        val totalVotes: Int,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class EarlBirds(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoChamberTrapsWave(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoFades(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val source: ModificationID,
        val removes: @Serializable(UnwrappedSerialiser::class) List<EchoFadeRemoved>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay {
        @Serializable
        data class EchoFadeRemoved(val mod: ModificationID, val type: Int)
    }

    @Serializable
    class EchoPlayerReceiver(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoPlayerStatic(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class EchoPlayerStaticChild(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val teamId: TeamID,
        val playerId: PlayerID,
        val teamName: String,
        val playerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class EndOfInning(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Feedback(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class FeedbackBlocked(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class FinalStandings(val place: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class FireEating(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FlagPlanted(val title: String, val votes: Int, val renoId: @Serializable(CoercedIntSerialiser::class) Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class Flooding(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Flyout(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FoulBall(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class FreeRefill(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class GameEndLog(
        override val play: Int,
        override val subPlay: Int,
        val winner: TeamID,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class GroundOut(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class HalfInning(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Hit(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class HomeRun(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Homesick(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Incineration(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override val parent: FeedID? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren, WithParent

    @Serializable
    class LateToTheParty(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class LetsGo(val home: TeamID, val away: TeamID, override val play: Int, override val subPlay: Int, val stadium: StadiumID? = null, val weather: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class LineupOrganised(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class MildPitch(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class ModifierExpires(val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>, val type: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class MurderOfCrows(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class NecromancyNarration(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class NewPlayer(val id: PlayerID, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

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
        val outPlayerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class NewTeam(val teamId: TeamID, val teamName: String, val divisionID: DivisionID, val divisionName: String, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class OverUnder(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Partying(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PeanutAllergyCured(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PeanutMister(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class PeanutFlavourText(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Perk(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PitcherChange(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlateAppearance(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Play(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class PlayerBecomesAlternate(val type: Int, val before: Double, val after: Double, override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

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
        val bPlayerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PlayerDroppedItem(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        override val parent: FeedID? = null,
        val playerRating: Double,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerEchoed(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerEntersSecretBase(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerExitsSecretBase(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerEntersCrimeScene(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerEntersHallOfFlame(override val play: Int, override val subPlay: Int, override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class PlayerExitsHallOfFlame(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerEvolves(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerGainedBloodType(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerGainedItem(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        override val parent: FeedID? = null,
        val playerRating: Double,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerHatched(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val id: PlayerID, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerHitsAnotherWithPitch(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerHopsOnGrindRail(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

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
        val receiveLocation: Int, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerOpensCrate(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class PlayerRecruited(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val teamId: TeamID,
        val teamName: String,
        val location: Int,
        val playerId: PlayerID,
        val playerName: String, override var upnut: Boolean? = null
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
        val promotePlayerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata()

    @Serializable
    class PlayerReroll(
        val type: Int,
        val before: Double,
        val after: Double,
        override val parent: FeedID, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayersItemBroke(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class PlayersItemDamaged(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class PlayersItemRepaired(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class PlayersItemRestored(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mods: @Serializable(UnwrappedSerialiser::class) List<ModificationID>? = null,
        val itemId: ItemID,
        val itemName: String,
        val playerRating: Double,
        val itemDurability: Int,
        val itemHealthBefore: Int,
        val itemHealthAfter: Int,
        val playerItemRatingBefore: Double,
        val playerItemRatingAfter: Double, override var upnut: Boolean? = null
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
        val retreatPlayerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class PlayerSkippedShelledOrElsewhere(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class PlayerStatDecrease(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val type: Int,
        val before: Double,
        val after: Double,
        override val parent: FeedID? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerStatDecreaseByPercent(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val before: Double,
        val after: Double,
        val type: Int,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerStatIncrease(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        val type: Int,
        val before: Double,
        val after: Double,
        override val parent: FeedID? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class PlayerStatIncreaseByPercent(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val before: Double,
        val after: Double,
        val type: Int,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

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
        val bPlayerName: String, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent.AlwaysPresent

    @Serializable
    class PsychoacousticsEcho(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class RemovedInGameModifier(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val mod: ModificationID,
        val type: Int,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class RemovedModifierDueToAnotherModifier(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val mod: ModificationID,
        val type: Int,
        val source: ModificationID, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class RenovationBuiltInt(val title: String, val votes: @Serializable(CoercedIntSerialiser::class) Int, val renoId: String, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class ReturnFromElsewhere(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class ReverbShuffle(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ReverbShuffleFull(override val play: Int, override val subPlay: Int, override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class ReverbShuffleRotation(override val play: Int, override val subPlay: Int, override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class Reverberating(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class SalmonSwimUpstream(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Siphon(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SiteTakeoverText(val being: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class SolarPanelAlignment(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class SolarPanelOverflowRuns(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class SolarPanelRunCollection(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class StolenBase(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Strike(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class StrikeZappedElectricBlood(override val play: Int, override val subPlay: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent

    @Serializable
    class Strikeout(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class Sun2InGame(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SuperyummyText(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class SuperyummyTransition(
        override val play: Int,
        override val subPlay: Int,
        override val parent: FeedID,
        val from: ModificationID,
        val to: ModificationID,
        val type: Int,
        val source: ModificationID, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithParent.AlwaysPresent

    @Serializable
    class TarotReading(@Serializable(CoercedIntSerialiser::class) val count: Int, val spread: @Serializable(UnwrappedSerialiser::class) List<Int>, val tarotId: String? = null, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class TastingTheInfinite(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class TeamMiddling(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class TeamOverperforming(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class TeamShamed(val totalShames: Int, val totalShamings: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class TeamShames(val totalShames: Int, val totalShamings: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class TeamWinsInternetSeries(val championships: Int, override var upnut: Boolean? = null) : BlaseballFeedMetadata()

    @Serializable
    class InvestigationUnderway(override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class TripleThreat(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class UnderOver(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Undersea(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class Walk(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren

    @Serializable
    class WillReceived(
        val id: WillID,
        val title: String,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        val dataVotes: Int? = null,
        val willVotes: Int? = null,
        val totalVotes: Int? = null,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithChildren.AlwaysPresent

    @Serializable
    class WillResults(override val parent: FeedID, override var upnut: Boolean? = null) : BlaseballFeedMetadata(), WithParent.AlwaysPresent

    @Serializable
    class Wired(
        override val play: Int,
        override val subPlay: Int,
        override val children: @Serializable(UnwrappedSerialiser::class) List<FeedID>,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay.AlwaysPresent, WithChildren.AlwaysPresent

    @Serializable
    class ModificationTransition(
        override val play: Int? = null,
        override val subPlay: Int? = null,
        override val parent: FeedID? = null,
        val from: ModificationID,
        val to: ModificationID,
        val type: Int, override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata(), WithPlay, WithParent

    @Serializable
    class REDACTED(
        val redacted: Boolean,
        override var upnut: Boolean? = null
    ) : BlaseballFeedMetadata()

    @Serializable(NoMetadataSerialiser::class)
    data class None(override var upnut: Boolean? = null) : BlaseballFeedMetadata() {
        override val serialiser: KSerializer<BlaseballFeedMetadata> by lazy { coerce(NoMetadataSerialiser) }
    }

    @Serializable(UnknownMetadataSerialiser::class)
    class Unknown(val contents: MutableMap<String, JsonElement>) : BlaseballFeedMetadata(), Map<String, JsonElement> by contents {
        override var upnut: Boolean?
            get() = contents["upnut"]?.jsonPrimitive?.booleanOrNull
            set(value) {
                if (value == null) contents.remove("upnut")
                else contents["upnut"] = JsonPrimitive(value)
            }
        override val serialiser: KSerializer<BlaseballFeedMetadata> by lazy { coerce(UnknownMetadataSerialiser) }
    }

//    @OptIn(InternalSerializationApi::class)
//    fun test() {
//        println("This: $this / ${this::class} / ${this::class.serializer() as KSerializer<BlaseballFeedMetadata>}}")
//    }

    @OptIn(InternalSerializationApi::class)
    open val serialiser: KSerializer<BlaseballFeedMetadata> by lazy { coerce(this::class.serializer()) }

    open fun serialise(encoder: CompositeEncoder, descriptor: SerialDescriptor, index: Int) {
//        val serializer =
        encoder.encodeSerializableElement(descriptor, index, serialiser, this)
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
        if (decoder is JsonDecoder) {
            return when (val element = decoder.decodeJsonElement()) {
                is JsonNull -> BlaseballFeedMetadata.Unknown(HashMap())
                is JsonObject -> BlaseballFeedMetadata.Unknown(element.toMutableMap())

                else -> throw SerializationException("Unexpected JSON token $element")
            }
        } else {
            throw SerializationException("Unknown decoder type ${decoder::class} ($decoder)")
        }

//        return BlaseballFeedMetadata.Unknown(MapSerializer(String.serializer(), JsonElement.serializer()).deserialize(decoder).toMutableMap())
    }
}

object NoMetadataSerialiser : KSerializer<BlaseballFeedMetadata.None> {
    // technically, JsonNull is an object, but it does not call beginStructure/endStructure at all
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("NoMetadata", SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: BlaseballFeedMetadata.None) {
        value.upnut?.let {
            if (encoder is JsonEncoder) {
                encoder.encodeJsonElement(buildJsonObject { put("upnut", it) })
            } else {
                encoder.encodeNotNullMark()
                encoder.encodeBoolean(it)
            }
        } ?: run {
            encoder.encodeNotNullMark()
            encoder.encodeNull()
        }
    }

    override fun deserialize(decoder: Decoder): BlaseballFeedMetadata.None {
        if (decoder is JsonDecoder) {
            return when (val element = decoder.decodeJsonElement()) {
                is JsonObject -> BlaseballFeedMetadata.None(element["upnut"]?.jsonPrimitive?.booleanOrNull)
                is JsonPrimitive -> BlaseballFeedMetadata.None(element.booleanOrNull)
                else -> BlaseballFeedMetadata.None(null)
            }
        } else {
            if (decoder.decodeNotNullMark()) return BlaseballFeedMetadata.None(decoder.decodeBoolean())
            else decoder.decodeNull()
        }

        return BlaseballFeedMetadata.None(null)
    }
}

inline fun <T, R : T> coerce(value: KSerializer<out T>): KSerializer<R> =
    value as KSerializer<R>