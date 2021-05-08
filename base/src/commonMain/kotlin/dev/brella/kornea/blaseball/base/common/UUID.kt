package dev.brella.kornea.blaseball.base.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(UUID.Serialiser::class)
class UUID(val mostSigBits: ULong, val leastSigBits: ULong) {
    companion object {
        val digits = charArrayOf(
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
        )


        /**
         * Format a long (treated as unsigned) into a character buffer. If
         * {@code len} exceeds the formatted ASCII representation of {@code val},
         * {@code buf} will be padded with leading zeroes.
         *
         * @param val the unsigned long to format
         * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
         * @param buf the character buffer to write to
         * @param offset the offset in the destination buffer to start at
         * @param len the number of characters to write
         */

        /**
         * Format a long (treated as unsigned) into a character buffer. If
         * `len` exceeds the formatted ASCII representation of `val`,
         * `buf` will be padded with leading zeroes.
         *
         * @param val the unsigned long to format
         * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
         * @param buf the character buffer to write to
         * @param offset the offset in the destination buffer to start at
         * @param len the number of characters to write
         */
        /** byte[]/LATIN1 version     */
        fun formatUnsignedLong0(`val`: ULong, shift: Int, buf: CharArray, offset: Int, len: Int) {
            var value = `val`
            var charPos = offset + len
            val radix = 1 shl shift
            val mask = radix - 1
            do {
                buf[--charPos] = digits[value.toInt() and mask]
                value = value shr shift
            } while (charPos > offset)
        }

        fun toString(lsb: ULong, msb: ULong): String {
            val buf = CharArray(36)
            formatUnsignedLong0(lsb, 4, buf, 24, 12)
            formatUnsignedLong0(lsb shr 48, 4, buf, 19, 4)
            formatUnsignedLong0(msb, 4, buf, 14, 4)
            formatUnsignedLong0(msb shr 16, 4, buf, 9, 4)
            formatUnsignedLong0(msb shr 32, 4, buf, 0, 8)

            buf[23] = '-'
            buf[18] = '-'
            buf[13] = '-'
            buf[8] = '-'

            return buf.concatToString()
        }

        fun fromString(name: String): UUID {
            val len: Int = name.length
            if (len > 36) throw IllegalArgumentException("UUID string too large")

            val dash1: Int = name.indexOf('-', 0)
            val dash2: Int = name.indexOf('-', dash1 + 1)
            val dash3: Int = name.indexOf('-', dash2 + 1)
            val dash4: Int = name.indexOf('-', dash3 + 1)
            val dash5: Int = name.indexOf('-', dash4 + 1)

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5
            if (dash4 < 0 || dash5 >= 0) throw IllegalArgumentException("Invalid UUID string: $name")

            var mostSigBits: ULong = name.substring(0, dash1).toLong(16).toULong() and 0xffffffffuL
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash1 + 1, dash2).toLong(16).toULong() and 0xffffuL)
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash2 + 1, dash3).toLong(16).toULong() and 0xffffuL)

            var leastSigBits: ULong = name.substring(dash3 + 1, dash4).toLong(16).toULong() and 0xffffuL
            leastSigBits = leastSigBits shl 48
            leastSigBits = leastSigBits or (name.substring(dash4 + 1, len).toLong(16).toULong() and 0xffffffffffffuL)

            return UUID(mostSigBits, leastSigBits)
        }
        fun fromStringOrNull(name: String): UUID? {
            val len: Int = name.length
            if (len > 36) return null

            val dash1: Int = name.indexOf('-', 0)
            val dash2: Int = name.indexOf('-', dash1 + 1)
            val dash3: Int = name.indexOf('-', dash2 + 1)
            val dash4: Int = name.indexOf('-', dash3 + 1)
            val dash5: Int = name.indexOf('-', dash4 + 1)

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5
            if (dash4 < 0 || dash5 >= 0) return null

            var mostSigBits: ULong = name.substring(0, dash1).toLong(16).toULong() and 0xffffffffuL
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash1 + 1, dash2).toLong(16).toULong() and 0xffffuL)
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash2 + 1, dash3).toLong(16).toULong() and 0xffffuL)

            var leastSigBits: ULong = name.substring(dash3 + 1, dash4).toLong(16).toULong() and 0xffffuL
            leastSigBits = leastSigBits shl 48
            leastSigBits = leastSigBits or (name.substring(dash4 + 1, len).toLong(16).toULong() and 0xffffffffffffuL)

            return UUID(mostSigBits, leastSigBits)
        }
/*        fun fromStringAsResult(name: String): KorneaResult<UUID> {
            val len: Int = name.length
            if (len > 36) throw IllegalArgumentException("UUID string too large")

            val dash1: Int = name.indexOf('-', 0)
            val dash2: Int = name.indexOf('-', dash1 + 1)
            val dash3: Int = name.indexOf('-', dash2 + 1)
            val dash4: Int = name.indexOf('-', dash3 + 1)
            val dash5: Int = name.indexOf('-', dash4 + 1)

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5

            // For any valid input, dash1 through dash4 will be positive and dash5
            // negative, but it's enough to check dash4 and dash5:
            // - if dash1 is -1, dash4 will be -1
            // - if dash1 is positive but dash2 is -1, dash4 will be -1
            // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
            //   positive, but so will dash5
            if (dash4 < 0 || dash5 >= 0) throw IllegalArgumentException("Invalid UUID string: $name")

            var mostSigBits: ULong = name.substring(0, dash1).toLong(16).toULong() and 0xffffffffuL
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash1 + 1, dash2).toLong(16).toULong() and 0xffffuL)
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or (name.substring(dash2 + 1, dash3).toLong(16).toULong() and 0xffffuL)

            var leastSigBits: ULong = name.substring(dash3 + 1, dash4).toLong(16).toULong() and 0xffffuL
            leastSigBits = leastSigBits shl 48
            leastSigBits = leastSigBits or (name.substring(dash4 + 1, len).toLong(16).toULong() and 0xffffffffffffuL)

            return UUID(mostSigBits, leastSigBits)
        }*/
    }

    object Serialiser: KSerializer<UUID> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: UUID) =
            encoder.encodeString(value.toString())

        override fun deserialize(decoder: Decoder): UUID =
            fromString(decoder.decodeString())
    }

    /**
     * The version number associated with this `UUID`.  The version
     * number describes how this `UUID` was generated.
     *
     * The version number has the following meaning:
     *
     *  * 1    Time-based UUID
     *  * 2    DCE security UUID
     *  * 3    Name-based UUID
     *  * 4    Randomly generated UUID
     *
     *
     * @return  The version number of this `UUID`
     */
    fun version(): Int {
        // Version is bits masked by 0x000000000000F000 in MS long
        return (mostSigBits shr 12 and 0x0fu).toInt()
    }

    /**
     * The variant number associated with this `UUID`.  The variant
     * number describes the layout of the `UUID`.
     *
     * The variant number has the following meaning:
     *
     *  * 0    Reserved for NCS backward compatibility
     *  * 2    [IETF&nbsp;RFC&nbsp;4122](http://www.ietf.org/rfc/rfc4122.txt)
     * (Leach-Salz), used by this class
     *  * 6    Reserved, Microsoft Corporation backward compatibility
     *  * 7    Reserved for future definition
     *
     *
     * @return  The variant number of this `UUID`
     */
    fun variant(): Int {
        // This field is composed of a varying number of bits.
        // 0    -    -    Reserved for NCS backward compatibility
        // 1    0    -    The IETF aka Leach-Salz variant (used by this class)
        // 1    1    0    Reserved, Microsoft backward compatibility
        // 1    1    1    Reserved for future definition.

        return (leastSigBits shr (64u - (leastSigBits shr 62)).toInt()
                and (leastSigBits shr 63)).toInt()
    }

    /**
     * The timestamp value associated with this UUID.
     *
     *
     *  The 60 bit timestamp value is constructed from the time_low,
     * time_mid, and time_hi fields of this `UUID`.  The resulting
     * timestamp is measured in 100-nanosecond units since midnight,
     * October 15, 1582 UTC.
     *
     *
     *  The timestamp value is only meaningful in a time-based UUID, which
     * has version type 1.  If this `UUID` is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException
     * If this UUID is not a version 1 UUID
     * @return The timestamp of this `UUID`.
     */
    fun timestamp(): Long {
        if (version() != 1) {
            throw UnsupportedOperationException("Not a time-based UUID")
        }
        return ((mostSigBits and 0x0FFFuL) shl 48 or
                (((mostSigBits shr 16) and 0x0FFFFuL) shl 32) or
                (mostSigBits shr 32)).toLong()
    }

    /**
     * The clock sequence value associated with this UUID.
     *
     *
     *  The 14 bit clock sequence value is constructed from the clock
     * sequence field of this UUID.  The clock sequence field is used to
     * guarantee temporal uniqueness in a time-based UUID.
     *
     *
     *  The `clockSequence` value is only meaningful in a time-based
     * UUID, which has version type 1.  If this UUID is not a time-based UUID
     * then this method throws UnsupportedOperationException.
     *
     * @return  The clock sequence of this `UUID`
     *
     * @throws  UnsupportedOperationException
     * If this UUID is not a version 1 UUID
     */
    fun clockSequence(): Int {
        if (version() != 1) throw UnsupportedOperationException("Not a time-based UUID")
        return ((leastSigBits and 0x3FFF000000000000uL) shr 48).toInt()
    }

    /**
     * The node value associated with this UUID.
     *
     *
     *  The 48 bit node value is constructed from the node field of this
     * UUID.  This field is intended to hold the IEEE 802 address of the machine
     * that generated this UUID to guarantee spatial uniqueness.
     *
     *
     *  The node value is only meaningful in a time-based UUID, which has
     * version type 1.  If this UUID is not a time-based UUID then this method
     * throws UnsupportedOperationException.
     *
     * @return  The node value of this `UUID`
     *
     * @throws  UnsupportedOperationException
     * If this UUID is not a version 1 UUID
     */
    fun node(): Long {
        if (version() != 1) throw UnsupportedOperationException("Not a time-based UUID")
        return (leastSigBits and 0x0000FFFFFFFFFFFFuL).toLong()
    }

    override fun toString(): String = toString(leastSigBits, mostSigBits)

    /**
     * Returns a hash code for this `UUID`.
     *
     * @return  A hash code value for this `UUID`
     */
    override fun hashCode(): Int {
        val hilo = (mostSigBits xor leastSigBits).toLong()
        return (hilo shr 32).toInt() xor hilo.toInt()
    }

    /**
     * Compares this object to the specified object.  The result is `true` if and only if the argument is not `null`, is a `UUID`
     * object, has the same variant, and contains the same value, bit for bit,
     * as this `UUID`.
     *
     * @param  other
     * The object to be compared
     *
     * @return  `true` if the objects are the same; `false`
     * otherwise
     */
    override fun equals(other: Any?): Boolean = when (other) {
        is UUID -> mostSigBits == other.mostSigBits &&
                   leastSigBits == other.leastSigBits
        is String -> equals(fromStringOrNull(other))

        else -> false
    }

    fun compareTo(other: UUID): Int =
        if (mostSigBits < other.mostSigBits) -1 else if (mostSigBits > other.mostSigBits) 1 else if (leastSigBits < other.leastSigBits) -1 else if (leastSigBits > other.leastSigBits) 1 else 0
}