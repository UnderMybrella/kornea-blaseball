package dev.brella.kornea.blaseball.base.common.json

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.parse
import dev.brella.kornea.blaseball.base.common.BLASEBALL_TIME_PATTERN
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BlaseballDateTimeSerialiser: KSerializer<DateTimeTz> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlaseballDateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTimeTz) {
        encoder.encodeString(BLASEBALL_TIME_PATTERN.format(value))
    }

    override fun deserialize(decoder: Decoder): DateTimeTz =
        BLASEBALL_TIME_PATTERN.parse(decoder.decodeString())
}