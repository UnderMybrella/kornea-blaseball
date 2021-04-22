package dev.brella.kornea.blaseball.base.common.json

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.parse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BlaseballDateTimeSerialiser: KSerializer<DateTimeTz> {
    //2021-04-10T03:41:12.046Z
    val format = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlaseballDateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTimeTz) {
        encoder.encodeString(format.format(value))
    }

    override fun deserialize(decoder: Decoder): DateTimeTz =
        format.parse(decoder.decodeString())
}