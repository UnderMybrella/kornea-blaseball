package dev.brella.kornea.blaseball.base.common.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.encodeToJsonElement

class UnwrappedSerialiser<T>(serialiser: KSerializer<T>) : KSerializer<List<T>> {
    val serialiser = ListSerializer((serialiser))

    /**
     * A descriptor for this transformation.
     * By default, it delegates to [tSerializer]'s descriptor.
     *
     * However, this descriptor can be overridden to achieve better representation of the resulting JSON shape
     * for schema generating or introspection purposes.
     */
    override val descriptor: SerialDescriptor get() = serialiser.descriptor

    override fun serialize(encoder: Encoder, value: List<T>) =
        serialiser.serialize(encoder, value)

    override fun deserialize(decoder: Decoder): List<T> =
        if (decoder is JsonDecoder) {
            val element = decoder.decodeJsonElement()
            decoder.json.decodeFromJsonElement(
                serialiser, when (element) {
                    is JsonNull -> JsonArray(emptyList())
                    is JsonArray -> element
                    else -> JsonArray(listOf(element))
                }
            )
        } else {
            serialiser.deserialize(decoder)
        }
}