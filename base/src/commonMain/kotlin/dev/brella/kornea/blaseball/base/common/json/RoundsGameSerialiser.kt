package dev.brella.kornea.blaseball.json

import dev.brella.kornea.blaseball.GameID
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
        return GameID(str)
    }
}