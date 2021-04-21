package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.ItemID
import dev.brella.kornea.blaseball.base.common.ModificationID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballGlobalEvent
import dev.brella.kornea.blaseball.base.common.beans.BlaseballIdols
import dev.brella.kornea.blaseball.base.common.beans.BlaseballItem
import dev.brella.kornea.blaseball.base.common.beans.BlaseballMod
import dev.brella.kornea.blaseball.base.common.beans.BlaseballSimulationData
import dev.brella.kornea.blaseball.base.common.beans.BlaseballStreamData
import dev.brella.kornea.blaseball.base.common.beans.BlaseballStreamDataResponse
import dev.brella.kornea.blaseball.base.common.beans.BlaseballTribute
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.blaseball.readAsJson
import dev.brella.kornea.blaseball.unwrapResult
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.kornea.errors.common.map
import dev.brella.ktornea.common.getAsResult
import dev.brella.ktornea.common.stream
import dev.brella.ktornea.common.streamAsResult
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

interface BlaseballGlobalService: BlaseballDatabaseService {
    val globalApiBaseUrl: String
        get() = "$blaseballBaseUrl/api"

    suspend fun getIdolBoard(): KorneaResult<BlaseballIdols> =
        client.getAsResult("$globalApiBaseUrl/getIdols")
    suspend fun getHallOfFlamePlayers(): KorneaResult<List<BlaseballTribute>> =
        client.getAsResult("$globalApiBaseUrl/getTribute")

    suspend fun getBloodType(bloodID: String): KorneaResult<String> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/blood") { parameter("ids", bloodID) })
    suspend fun getCoffeePreference(coffeeID: String): KorneaResult<String> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/coffee") { parameter("ids", coffeeID) })

    suspend fun getBloodTypes(bloodIDs: Iterable<String>): KorneaResult<List<String>> =
        client.getAsResult("$databaseBaseUrl/blood") { parameter("ids", bloodIDs.joinToString(",")) }
    suspend fun getCoffeePreferences(coffeeIDs: Iterable<String>): KorneaResult<List<String>> =
        client.getAsResult("$databaseBaseUrl/coffee") { parameter("ids", coffeeIDs.joinToString(",")) }

    suspend fun getGlobalEvents(): KorneaResult<List<BlaseballGlobalEvent>> =
        client.getAsResult("$databaseBaseUrl/globalEvents")

    suspend fun getItems(itemIDs: Iterable<ItemID>): KorneaResult<List<BlaseballItem>> =
        client.getAsResult("$databaseBaseUrl/items") { parameter("ids", itemIDs.joinParams()) }

    suspend fun getModification(modID: ModificationID): KorneaResult<BlaseballMod> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/mods") { parameter("ids", modID.id) })
    suspend fun getModifications(modIDs: Iterable<ModificationID>): KorneaResult<List<BlaseballMod>> =
        client.getAsResult("$databaseBaseUrl/mods") { parameter("ids", modIDs.joinParams()) }
    suspend fun getSimulationData(): KorneaResult<BlaseballSimulationData> =
        client.getAsResult("$databaseBaseUrl/simulationData")

    suspend fun getLiveDataStream(): KorneaResult<Flow<BlaseballStreamData>> =
        client.streamAsResult {
            method = HttpMethod.Get
            url("$blaseballBaseUrl/events/streamData")

            timeout {
                this.requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
                this.socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            }
        }.map { flow ->
            flow.mapNotNull { str ->
                if (str.startsWith("data:"))
                    client.readAsJson<BlaseballStreamDataResponse>(str.substringAfter("data:").trim().encodeToByteArray()).value
                else null
            }
        }
}