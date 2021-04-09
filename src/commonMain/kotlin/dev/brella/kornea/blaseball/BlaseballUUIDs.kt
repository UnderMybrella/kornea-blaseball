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
import java.util.function.BiConsumer

interface BlaseballUUID: CharSequence {
    val uuid: String

    override val length: Int
        get() = uuid.length

    override fun get(index: Int): Char = uuid[index]
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        uuid.subSequence(startIndex, endIndex)
}

@Serializable
inline class PlayerID(override val uuid: String): BlaseballUUID
@Serializable
inline class TeamID(override val uuid: String): BlaseballUUID
@Serializable
inline class LeagueID(override val uuid: String): BlaseballUUID
@Serializable
inline class DivisionID(override val uuid: String): BlaseballUUID
@Serializable
inline class MatchupID(override val uuid: String): BlaseballUUID
@Serializable
inline class SeasonID(override val uuid: String): BlaseballUUID
@Serializable
inline class RulesID(override val uuid: String): BlaseballUUID
@Serializable
inline class ScheduleID(override val uuid: String): BlaseballUUID
@Serializable
inline class StandingsID(override val uuid: String): BlaseballUUID
@Serializable
inline class TerminologyID(override val uuid: String): BlaseballUUID
@Serializable
inline class TiebreakerID(override val uuid: String): BlaseballUUID


@Serializable
inline class GameID(override val uuid: String): BlaseballUUID

@Serializable
inline class StadiumID(override val uuid: String): BlaseballUUID

@Serializable
inline class DecreeID(override val uuid: String): BlaseballUUID

@Serializable
inline class GameStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class PlayerStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class SeasonStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class TeamStatsheetID(override val uuid: String): BlaseballUUID

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
    override inline fun forEach(action: BiConsumer<in K, in V>) = backing.forEach(action)
    override inline fun get(key: K): V? = backing.get(key)
    override inline fun getOrDefault(key: K, defaultValue: V): V = backing.getOrDefault(key, defaultValue)
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