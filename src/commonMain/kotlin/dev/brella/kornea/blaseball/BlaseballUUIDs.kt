package dev.brella.kornea.blaseball

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.mapSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface BlaseballID: CharSequence {
    val id: String

    override val length: Int
        get() = id.length

    override fun get(index: Int): Char = id[index]
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        id.subSequence(startIndex, endIndex)
}

@Serializable
inline class SimulationAttributeID(override val id: String): BlaseballID

interface BlaseballUUID: BlaseballID

@Serializable
inline class PlayerID(override val id: String): BlaseballUUID
@Serializable
inline class TeamID(override val id: String): BlaseballUUID
@Serializable
inline class LeagueID(override val id: String): BlaseballUUID
@Serializable
inline class SubleagueID(override val id: String): BlaseballUUID
@Serializable
inline class DivisionID(override val id: String): BlaseballUUID
@Serializable
inline class PlayoffMatchupID(override val id: String): BlaseballUUID
@Serializable
inline class SeasonID(override val id: String): BlaseballUUID
@Serializable
inline class RulesID(override val id: String): BlaseballUUID
@Serializable
inline class ScheduleID(override val id: String): BlaseballUUID
@Serializable
inline class StandingsID(override val id: String): BlaseballUUID
@Serializable
inline class TerminologyID(override val id: String): BlaseballUUID
@Serializable
inline class TiebreakerID(override val id: String): BlaseballUUID


@Serializable
inline class SimulationID(override val id: String): BlaseballUUID
@Serializable
inline class GameID(override val id: String): BlaseballUUID

@Serializable
inline class StadiumID(override val id: String): BlaseballUUID
@Serializable
inline class PlayoffID(override val id: String): BlaseballUUID
@Serializable
inline class PlayoffRoundID(override val id: String): BlaseballUUID
@Serializable
inline class ModificationID(override val id: String): BlaseballID
@Serializable
inline class OffseasonRecapID(override val id: String): BlaseballUUID

@Serializable
inline class DecreeID(override val id: String): BlaseballUUID
@Serializable
inline class BlessingID(override val id: String): BlaseballID
@Serializable
inline class WillID(override val id: String): BlaseballUUID
@Serializable
inline class TidingID(override val id: String): BlaseballUUID
@Serializable
inline class FeedID(override val id: String): BlaseballUUID

@Serializable
inline class ItemID(override val id: String): BlaseballUUID

@Serializable
inline class GameStatsheetID(override val id: String): BlaseballUUID
@Serializable
inline class PlayerStatsheetID(override val id: String): BlaseballUUID
@Serializable
inline class SeasonStatsheetID(override val id: String): BlaseballUUID
@Serializable
inline class TeamStatsheetID(override val id: String): BlaseballUUID



//@Serializable(with = UUIDMapSerialiser::class)
//interface UUIDMap<K: BlaseballUUID, V: Any?>: Map<K, V>

//typealias UUIDMap<K, V> = Map<K, V>

@Serializable(with = UUIDMapSerialiser::class)
inline class UUIDMap<K: BlaseballUUID, V: Any?>(val backing: Map<K, V>): Map<K, V> {
    override val entries: Set<Map.Entry<K, V>>
        inline get() = backing.entries
    override val keys: Set<K>
        inline get() = backing.keys
    override val size: Int
        inline get() = backing.size
    override val values: Collection<V>
        inline get() = backing.values

    override inline fun containsKey(key: K): Boolean = backing.containsKey(key)
    override inline fun containsValue(value: V): Boolean = backing.containsValue(value)
    override inline fun get(key: K): V? = backing.get(key)
    override inline fun isEmpty(): Boolean = backing.isEmpty()
}

public fun <K: BlaseballUUID, V: Any?> emptyUUIDMap(): UUIDMap<K, V> = UUIDMap(emptyMap())

class UUIDMapSerialiser<K : BlaseballUUID, V : Any?>(val keySerialiser: KSerializer<K>, val valueSerialiser: KSerializer<V>) : KSerializer<Map<K, V>> {
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        mapSerialDescriptor(String.serializer().descriptor, valueSerialiser.descriptor)

    @OptIn(ExperimentalStdlibApi::class)
    override fun deserialize(decoder: Decoder): Map<K, V> {
        val compositeDecoder = decoder.beginStructure(descriptor)
//        if (compositeDecoder.decodeSequentially()) {
//            readAll(compositeDecoder, builder, startIndex, readSize(compositeDecoder, builder))
//        } else {
        val map: MutableMap<K, V> = LinkedHashMap()

        while (true) {
            val index = compositeDecoder.decodeElementIndex(descriptor)
            if (index == CompositeDecoder.DECODE_DONE) break

            val key = compositeDecoder.decodeInlineElement(descriptor, keySerialiser, index)
            val vIndex = compositeDecoder.decodeElementIndex(descriptor).also {
                require(it == index + 1) { "Value must follow key in a map, index for key: $index, returned index for value: $it" }
            }
            val value = if (map.containsKey(key) && valueSerialiser.descriptor.kind !is PrimitiveKind) {
                compositeDecoder.decodeSerializableElement(descriptor, vIndex, valueSerialiser, map.getValue(key))
            } else {
                compositeDecoder.decodeSerializableElement(descriptor, vIndex, valueSerialiser)
            }

            map[key] = value
        }
//        }
        compositeDecoder.endStructure(descriptor)

        return UUIDMap(map)
    }

    override fun serialize(encoder: Encoder, value: Map<K, V>) {
        val size = value.size
        val composite = encoder.beginCollection(descriptor, size)
        var index = 0
        value.forEach { (k, v) ->
            composite.encodeInlineElement(descriptor, index++, keySerialiser, k)
            composite.encodeSerializableElement(descriptor, index++, valueSerialiser, v)
        }
        composite.endStructure(descriptor)
    }
}

inline fun <T> CompositeEncoder.encodeInlineElement(descriptor: SerialDescriptor, index: Int, serialiser: KSerializer<T>, element: T) = serialiser.serialize(encodeInlineElement(descriptor, index), element)
inline fun <T> CompositeDecoder.decodeInlineElement(descriptor: SerialDescriptor, serialiser: KSerializer<T>, index: Int): T = serialiser.deserialize(decodeInlineElement(descriptor, index))

inline fun Iterable<BlaseballID>.joinParams(): String = joinToString(",", transform = BlaseballID::id)