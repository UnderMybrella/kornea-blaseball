package dev.brella.kornea.blaseball

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*

public inline fun <reified T> HttpClient.readAsJson(data: ByteArray): T =
    this[JsonFeature].serializer.read(typeInfo<T>(), ByteReadPacket(data)) as T

public inline fun HttpClient.writeAsJson(obj: Any): ByteArray =
    when (val outgoing = this[JsonFeature].serializer.write(obj)) {
        is OutgoingContent.NoContent -> byteArrayOf()
        is OutgoingContent.ByteArrayContent -> outgoing.bytes()
        else -> throw IllegalStateException("Can't handle $outgoing (${outgoing::class})")
    }

inline fun <T> unwrap(list: List<T>): T = list.first()