package dev.brella.kornea.blaseball.base.common.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

object CoercedIntSerialiser : KSerializer<Int> {
    override val descriptor: SerialDescriptor = Int.serializer().descriptor

    override fun deserialize(decoder: Decoder): Int =
        when (decoder) {
            is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.intOrNull ?: 0
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