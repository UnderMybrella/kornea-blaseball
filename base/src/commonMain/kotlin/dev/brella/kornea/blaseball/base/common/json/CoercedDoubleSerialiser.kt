package dev.brella.kornea.blaseball.base.common.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

object CoercedDoubleSerialiser : KSerializer<Double> {
    override val descriptor: SerialDescriptor = Double.serializer().descriptor

    override fun deserialize(decoder: Decoder): Double =
        when (decoder) {
            is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.doubleOrNull ?: 0.0
            else -> try {
                decoder.decodeDouble()
            } catch (serial: SerializationException) {
                //NOTE: Don't *know* if this will actually work
                decoder.decodeString().toDouble()
            }
        }

    override fun serialize(encoder: Encoder, value: Double) {
        encoder.encodeDouble(value)
    }
}