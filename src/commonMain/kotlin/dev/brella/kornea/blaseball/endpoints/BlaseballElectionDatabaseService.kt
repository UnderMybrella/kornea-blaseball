package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.DecreeID
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseBlessingResult
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseDecreeResult
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseOffseasonRecap
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseOffseasonSetup
import io.ktor.client.request.*

interface BlaseballElectionDatabaseService: BlaseballDatabaseService {
    suspend fun getBlessingResults(vararg blessingIDs: String): List<BlaseballDatabaseBlessingResult> =
        client.get("$databaseBaseUrl/bonusResults") { parameter("ids", blessingIDs.joinToString(",")) }

    suspend fun getBlessingResults(blessingIDs: Iterable<String>): List<BlaseballDatabaseBlessingResult> =
        client.get("$databaseBaseUrl/bonusResults") { parameter("ids", blessingIDs.joinToString(",")) }

    suspend fun getDecreeResults(decreeID: DecreeID): List<BlaseballDatabaseDecreeResult> =
        client.get("$databaseBaseUrl/decreeResults") { parameter("ids", decreeID.uuid) }

    suspend fun getDecreeResults(decreeIDs: Iterable<DecreeID>): List<BlaseballDatabaseDecreeResult> =
        client.get("$databaseBaseUrl/decreeResults") { parameter("ids", decreeIDs.joinToString(",", transform = DecreeID::uuid)) }

    suspend fun getElectionResults(season: Int): BlaseballDatabaseOffseasonRecap =
        client.get("$databaseBaseUrl/offseasonRecap") { parameter("season", season) }

    suspend fun getElectionDetails(): BlaseballDatabaseOffseasonSetup =
        client.get("$databaseBaseUrl/offseasonSetup")
}