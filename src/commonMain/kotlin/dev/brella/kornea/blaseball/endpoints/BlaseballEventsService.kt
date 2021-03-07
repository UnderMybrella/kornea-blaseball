package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.beans.BlaseballStreamData
import dev.brella.kornea.blaseball.beans.BlaseballStreamDataResponse
import dev.brella.kornea.blaseball.readAsJson
import dev.brella.kornea.blaseball.stream
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

interface BlaseballEventsService: BlaseballService {
    suspend fun streamData(): Flow<BlaseballStreamData>? =
        client.stream {
            method = HttpMethod.Get
            url("https://www.blaseball.com/events/streamData")

            timeout {
                this.requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                this.socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            }
        }?.mapNotNull { str -> if (str.startsWith("data:")) client.readAsJson<BlaseballStreamDataResponse>(str.substringAfter("data:").trim().encodeToByteArray()).value else null }
}