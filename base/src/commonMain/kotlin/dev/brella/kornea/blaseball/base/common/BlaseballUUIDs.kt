package dev.brella.kornea.blaseball.base.common

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
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
import kotlinx.serialization.serializer
import kotlin.jvm.JvmInline

interface BlaseballID : CharSequence {
    val id: String

    override val length: Int
        get() = id.length

    override fun get(index: Int): Char = id[index]
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        id.subSequence(startIndex, endIndex)
}

@Serializable
@JvmInline
value class SimulationAttributeID(override val id: String) : BlaseballID

interface BlaseballUUID : BlaseballID, Comparable<BlaseballUUID> {
    val uuid: UUID
    override val id: String get() = uuid.toString()

    override fun compareTo(other: BlaseballUUID): Int =
        uuid.compareTo(other.uuid)
}

@Serializable
@JvmInline
value class PlayerID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class TeamID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class LeagueID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class SubleagueID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class DivisionID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class PlayoffMatchupID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class SeasonID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class RulesID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class ScheduleID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class StandingsID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class TerminologyID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class TiebreakerID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}


@Serializable
@JvmInline
value class SimulationID(override val id: String) : BlaseballID

@Serializable
@JvmInline
value class GameID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class StadiumID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class PlayoffID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class PlayoffRoundID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class ModificationID(override val id: String) : BlaseballID

@Serializable
@JvmInline
value class OffseasonRecapID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class DecreeID(override val id: String) : BlaseballID

@Serializable
@JvmInline
value class BlessingID(override val id: String) : BlaseballID

@Serializable
@JvmInline
value class WillID(override val id: String) : BlaseballID

@Serializable
@JvmInline
value class TidingID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class FeedID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class ItemID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class GameStatsheetID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class PlayerStatsheetID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class SeasonStatsheetID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}

@Serializable
@JvmInline
value class TeamStatsheetID(override val uuid: UUID) : BlaseballUUID {
    constructor(uuid: String): this(UUID.fromString(uuid))
}


//@Serializable(with = UUIDMapSerialiser::class)
//interface UUIDMap<K: BlaseballUUID, V: Any?>: Map<K, V>

//typealias UUIDMap<K, V> = Map<K, V>

@Serializable(with = UUIDMapSerialiser::class)
@JvmInline
value class UUIDMap<K : BlaseballUUID, V : Any?>(val backing: Map<K, V>) : Map<K, V> {
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

public fun <K : BlaseballUUID, V : Any?> emptyUUIDMap(): UUIDMap<K, V> = UUIDMap(emptyMap())

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


@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any> CompositeEncoder.encodeInlineElement(descriptor: SerialDescriptor, index: Int, element: T) = T::class.serializer().serialize(encodeInlineElement(descriptor, index), element)
inline fun <T> CompositeEncoder.encodeInlineElement(descriptor: SerialDescriptor, index: Int, serialiser: KSerializer<T>, element: T) = serialiser.serialize(encodeInlineElement(descriptor, index), element)
inline fun <T> CompositeDecoder.decodeInlineElement(descriptor: SerialDescriptor, serialiser: KSerializer<T>, index: Int): T = serialiser.deserialize(decodeInlineElement(descriptor, index))

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any> CompositeDecoder.decodeInlineElement(descriptor: SerialDescriptor, index: Int): T = T::class.serializer().deserialize(decodeInlineElement(descriptor, index))

inline fun Iterable<BlaseballID>.joinParams(): String = joinToString(",", transform = BlaseballID::id)