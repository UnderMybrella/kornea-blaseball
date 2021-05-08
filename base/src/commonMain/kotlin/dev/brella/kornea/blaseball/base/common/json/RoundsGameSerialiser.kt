package dev.brella.kornea.blaseball.base.common.json

import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RoundsGameSerialiser: KSerializer<GameID?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GameID?", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: GameID?) {
        if (value == null) encoder.encodeString("none")
        else encoder.encodeString(value.id)
    }

    override fun deserialize(decoder: Decoder): GameID? {
        val str = decoder.decodeString()
        if (str == "none") return null
        return UUID.fromStringOrNull(str)?.let(::GameID)
    }
}

object RoundsTeamSerialiser: KSerializer<TeamID?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TeamID?", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: TeamID?) {
        if (value == null) encoder.encodeString("none")
        else encoder.encodeString(value.id)
    }

    override fun deserialize(decoder: Decoder): TeamID? {
        val str = decoder.decodeString()
        if (str == "none") return null
        return UUID.fromStringOrNull(str)?.let(::TeamID)
    }
}