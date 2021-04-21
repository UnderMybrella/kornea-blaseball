package dev.brella.kornea.blaseball.base.common.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ItemAttrSerialiser: KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("String?", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String?) {
        if (value == null) encoder.encodeString("NONE")
        else encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String? =
        decoder.decodeString().takeUnless { it == "NONE" }
}