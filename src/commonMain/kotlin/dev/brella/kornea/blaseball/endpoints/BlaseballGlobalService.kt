package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.ModificationID
import dev.brella.kornea.blaseball.beans.BlaseballGlobalEvent
import dev.brella.kornea.blaseball.beans.BlaseballIdols
import dev.brella.kornea.blaseball.beans.BlaseballOldItem
import dev.brella.kornea.blaseball.beans.BlaseballMod
import dev.brella.kornea.blaseball.beans.BlaseballSimulationData
import dev.brella.kornea.blaseball.beans.BlaseballStreamData
import dev.brella.kornea.blaseball.beans.BlaseballStreamDataResponse
import dev.brella.kornea.blaseball.beans.BlaseballTribute
import dev.brella.kornea.blaseball.readAsJson
import dev.brella.kornea.blaseball.stream
import dev.brella.kornea.blaseball.unwrap
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

    suspend fun getBloodType(bloodID: String): String =
        unwrap(client.get("$databaseBaseUrl/blood") { parameter("ids", bloodID) })
    suspend fun getCoffeePreference(coffeeID: String): String =
        unwrap(client.get("$databaseBaseUrl/coffee") { parameter("ids", coffeeID) })

    suspend fun getBloodTypes(bloodIDs: Iterable<String>): List<String> =
        client.get("$databaseBaseUrl/blood") { parameter("ids", bloodIDs.joinToString(",")) }
    suspend fun getCoffeePreferences(coffeeIDs: Iterable<String>): List<String> =
        client.get("$databaseBaseUrl/coffee") { parameter("ids", coffeeIDs.joinToString(",")) }

    suspend fun getGlobalEvents(): List<BlaseballGlobalEvent> =
        client.get("$databaseBaseUrl/globalEvents")
    
    @Deprecated("Item IDs are now UUIDs, but no items are available yet", level = DeprecationLevel.ERROR)
    @Suppress("DEPRECATION")
    suspend fun getItems(vararg itemIDs: String): List<BlaseballOldItem> =
        client.get("$databaseBaseUrl/items") { parameter("ids", itemIDs.joinToString(",")) }

    suspend fun getModification(modID: ModificationID): BlaseballMod =
        unwrap(client.get("$databaseBaseUrl/mods") { parameter("ids", modID.id) })
    suspend fun getModifications(modIDs: Iterable<ModificationID>): List<BlaseballMod> =
        client.get("$databaseBaseUrl/mods") { parameter("ids", modIDs.joinToString(",", transform = ModificationID::id)) }
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