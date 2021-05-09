package dev.brella.kornea.blaseball.base.common.json

import dev.brella.kornea.blaseball.base.common.beans.BlaseballFeedEvent
import dev.brella.kornea.blaseball.base.common.beans.BlaseballFeedEventSerialiser
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonArray

@Serializable(with = EventuallyFeedListSerialiser::class)
inline class EventuallyFeedList(val backing: List<BlaseballFeedEvent>) : List<BlaseballFeedEvent> {
    override val size: Int
        get() = backing.size

    override fun contains(element: BlaseballFeedEvent): Boolean =
        backing.contains(element)

    override fun containsAll(elements: Collection<BlaseballFeedEvent>): Boolean =
        backing.containsAll(elements)

    override fun get(index: Int): BlaseballFeedEvent =
        backing.get(index)

    override fun indexOf(element: BlaseballFeedEvent): Int =
        backing.indexOf(element)

    override fun isEmpty(): Boolean =
        backing.isEmpty()

    override fun iterator(): Iterator<BlaseballFeedEvent> =
        backing.iterator()

    override fun lastIndexOf(element: BlaseballFeedEvent): Int =
        backing.lastIndexOf(element)

    override fun listIterator(): ListIterator<BlaseballFeedEvent> =
        backing.listIterator()

    override fun listIterator(index: Int): ListIterator<BlaseballFeedEvent> =
        backing.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<BlaseballFeedEvent> =
        backing.subList(fromIndex, toIndex)
}

object EventuallyFeedListSerialiser : KSerializer<EventuallyFeedList> {
    val backup = ListSerializer(BlaseballFeedEventSerialiser)
    override val descriptor: SerialDescriptor = backup.descriptor

    override fun deserialize(decoder: Decoder): EventuallyFeedList =
        EventuallyFeedList(when (decoder) {
            is JsonDecoder -> {
                val list: List<JsonElement> = when (val element = decoder.decodeJsonElement()) {
                    is JsonNull -> emptyList()
                    is JsonArray -> element
                    else -> listOf(element)
                }

                list.mapNotNull { element ->
                    try {
                        decoder.json.decodeFromJsonElement(BlaseballFeedEventSerialiser, element)
                    } catch (se: SerializationException) {
//                        se.printStackTrace()
                        null
                    }
                }
            }

            else -> backup.deserialize(decoder)
        })

    override fun serialize(encoder: Encoder, value: EventuallyFeedList) =
        backup.serialize(encoder, value)
}