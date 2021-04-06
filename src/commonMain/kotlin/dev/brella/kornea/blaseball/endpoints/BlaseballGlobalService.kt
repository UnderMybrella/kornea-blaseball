package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.beans.BlaseballGlobalEvent
import dev.brella.kornea.blaseball.beans.BlaseballIdols
import dev.brella.kornea.blaseball.beans.BlaseballItem
import dev.brella.kornea.blaseball.beans.BlaseballMod
import dev.brella.kornea.blaseball.beans.BlaseballSimulationData
import dev.brella.kornea.blaseball.beans.BlaseballStreamData
import dev.brella.kornea.blaseball.beans.BlaseballStreamDataResponse
import dev.brella.kornea.blaseball.beans.BlaseballTribute
import dev.brella.kornea.blaseball.readAsJson
import dev.brella.kornea.blaseball.stream
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

interface BlaseballGlobalService: BlaseballDatabaseService {
    val globalApiBaseUrl: String
        get() = "$blaseballBaseUrl/api"

    suspend fun getIdolBoard(): BlaseballIdols =
        client.get("$globalApiBaseUrl/getIdols")
    suspend fun getHallOfFlamePlayers(): List<BlaseballTribute> =
        client.get("$globalApiBaseUrl/getTribute")

    suspend fun getBloodTypes(vararg bloodIDs: String): List<String> =
        client.get("$databaseBaseUrl/blood") { parameter("ids", bloodIDs.joinToString(",")) }
    suspend fun getCoffeePreferences(vararg coffeeIDs: String): List<String> =
        client.get("$databaseBaseUrl/coffee") { parameter("ids", coffeeIDs.joinToString(",")) }
    suspend fun getGlobalEvents(): List<BlaseballGlobalEvent> =
        client.get("$databaseBaseUrl/globalEvents")
    
    @Deprecated("Item IDs are now UUIDs, but no items are available yet", level = DeprecationLevel.ERROR)
    suspend fun getItems(vararg itemIDs: String): List<BlaseballItem> =
        client.get("$databaseBaseUrl/items") { parameter("ids", itemIDs.joinToString(",")) }
    suspend fun getModifications(vararg modIDs: String): List<BlaseballMod> =
        client.get("$databaseBaseUrl/mods") { parameter("ids", modIDs.joinToString(",")) }
    suspend fun getSimulationData(): BlaseballSimulationData =
        client.get("$databaseBaseUrl/simulationData")

    suspend fun getLiveDataStream(): Flow<BlaseballStreamData>? =
        client.stream {
            method = HttpMethod.Get
            url("$blaseballBaseUrl/events/streamData")

            timeout {
                this.requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                this.socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            }
        }?.mapNotNull { str -> if (str.startsWith("data:")) client.readAsJson<BlaseballStreamDataResponse>(str.substringAfter("data:").trim().encodeToByteArray()).value else null }
}