package dev.brella.kornea.blaseball.base.common.beans

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline class Colour(val rgba: Int) {
    companion object {
        private val HEX_CHAR_ENCODE = "0123456789ABCDEF".toCharArray()
        private val HEX_CHAR_DECODE = IntArray(16) { HEX_CHAR_ENCODE[it].toInt() or 0x20 }

        fun fromHex(str: String): Colour {
            require(str[0] == '#')

            var rgb = (HEX_CHAR_DECODE.indexOf(str[1].toInt() or 0x20) and 0xFF) shl 20
            rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[2].toInt() or 0x20) and 0xFF) shl 16)
            rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[3].toInt() or 0x20) and 0xFF) shl 12)
            rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[4].toInt() or 0x20) and 0xFF) shl 8)
            rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[5].toInt() or 0x20) and 0xFF) shl 4)
            rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[6].toInt() or 0x20) and 0xFF) shl 0)

            if (str.length > 7) {
                rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[7].toInt() or 0x20) and 0xFF) shl 28)
                rgb = rgb or ((HEX_CHAR_DECODE.indexOf(str[8].toInt() or 0x20) and 0xFF) shl 24)
            }

            return Colour(rgb)
        }
    }

    constructor(red: Int, green: Int, blue: Int, alpha: Int = 0xFF) : this(
        ((alpha and 0xFF) shl 24)
                or ((red and 0xFF) shl 16)
                or ((green and 0xFF) shl 8)
                or ((blue and 0xFF) shl 0)
    )

    inline val red: Int
        get() = (rgba shr 16) and 0xFF
    inline val green: Int
        get() = (rgba shr 8) and 0xFF
    inline val blue: Int
        get() = (rgba shr 0) and 0xFF
    inline val alpha: Int
        get() = (rgba shr 24) and 0xFF

    inline val redHigher: Int
        get() = (rgba shr 20) and 0xF
    inline val redLower: Int
        get() = (rgba shr 16) and 0xF
    inline val greenHigher: Int
        get() = (rgba shr 12) and 0xF
    inline val greenLower: Int
        get() = (rgba shr 8) and 0xF
    inline val blueHigher: Int
        get() = (rgba shr 4) and 0xF
    inline val blueLower: Int
        get() = (rgba shr 0) and 0xF
}

object ColourAsHexSerialiser : KSerializer<Colour> {
    private val HEX_CHAR_ENCODE = "0123456789ABCDEF".toCharArray()
    private val HEX_CHAR_DECODE = IntArray(16) { HEX_CHAR_ENCODE[it].toInt() or 0x20 }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ColourAsHex", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Colour) {
        val alpha = value.alpha

        encoder.encodeString(buildString {
            append('#')
            append(HEX_CHAR_ENCODE[value.redHigher])
            append(HEX_CHAR_ENCODE[value.redLower])
            append(HEX_CHAR_ENCODE[value.greenHigher])
            append(HEX_CHAR_ENCODE[value.greenLower])
            append(HEX_CHAR_ENCODE[value.blueHigher])
            append(HEX_CHAR_ENCODE[value.blueLower])
            if (alpha == 0xFF) {
                append(HEX_CHAR_ENCODE[(alpha shr 4) and 0xF])
                append(HEX_CHAR_ENCODE[(alpha shr 0) and 0xF])
            }
        })
    }

    override fun deserialize(decoder: Decoder): Colour {
        val str = decoder.decodeString()
        require(str[0] == '#')
        /*
        ((alpha and 0xFF) shl 24)
                or ((red and 0xFF) shl 16)
                or ((green and 0xFF) shl 8)
                or ((blue and 0xFF) shl 0)
        */

        return Colour.fromHex(str)
    }
}
object ColourAsNullableHexSerialiser : KSerializer<Colour?> {
    private val HEX_CHAR_ENCODE = "0123456789ABCDEF".toCharArray()
    private val HEX_CHAR_DECODE = IntArray(16) { HEX_CHAR_ENCODE[it].toInt() or 0x20 }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ColourAsHex", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Colour?) {
        if (value == null) {
            encoder.encodeNull()
            return
        }

        encoder.encodeNotNullMark()
        val alpha = value.alpha

        encoder.encodeString(buildString {
            append('#')
            append(HEX_CHAR_ENCODE[value.redHigher])
            append(HEX_CHAR_ENCODE[value.redLower])
            append(HEX_CHAR_ENCODE[value.greenHigher])
            append(HEX_CHAR_ENCODE[value.greenLower])
            append(HEX_CHAR_ENCODE[value.blueHigher])
            append(HEX_CHAR_ENCODE[value.blueLower])
            if (alpha == 0xFF) {
                append(HEX_CHAR_ENCODE[(alpha shr 4) and 0xF])
                append(HEX_CHAR_ENCODE[(alpha shr 0) and 0xF])
            }
        })
    }

    override fun deserialize(decoder: Decoder): Colour? {
        if (!decoder.decodeNotNullMark()) {
            decoder.decodeNull()
            return null
        }

        val str = decoder.decodeString()
        if (str.isBlank()) return null
        require(str[0] == '#')
        /*
        ((alpha and 0xFF) shl 24)
                or ((red and 0xFF) shl 16)
                or ((green and 0xFF) shl 8)
                or ((blue and 0xFF) shl 0)
        */

        return Colour.fromHex(str)
    }
}