package dev.brella.kornea.blaseball

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.utils.io.core.*

public inline fun <reified T> HttpClient.readAsJson(data: ByteArray): T =
    this[JsonFeature].serializer.read(typeInfo<T>(), ByteReadPacket(data)) as T