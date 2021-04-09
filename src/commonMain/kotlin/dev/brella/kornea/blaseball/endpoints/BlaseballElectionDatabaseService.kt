package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.BlaseballID
import dev.brella.kornea.blaseball.BlessingID
import dev.brella.kornea.blaseball.DecreeID
import dev.brella.kornea.blaseball.TidingID
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseBlessingResult
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseDecreeResult
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseTidingResult
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseOffseasonRecap
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseOffseasonSetup
import dev.brella.kornea.blaseball.joinParams
import dev.brella.kornea.blaseball.unwrap
import io.ktor.client.request.*

interface BlaseballElectionDatabaseService : BlaseballDatabaseService {
    suspend fun getBlessingResults(blessingID: BlessingID): BlaseballDatabaseBlessingResult =
        unwrap(client.get("$databaseBaseUrl/bonusResults") { parameter("ids", blessingID.id) })

    suspend fun getBlessingResults(blessingIDs: Iterable<BlessingID>): List<BlaseballDatabaseBlessingResult> =
        client.get("$databaseBaseUrl/bonusResults") { parameter("ids", blessingIDs.joinParams()) }

    suspend fun getDecreeResults(decreeID: DecreeID): List<BlaseballDatabaseDecreeResult> =
        client.get("$databaseBaseUrl/decreeResults") { parameter("ids", decreeID.id) }

    suspend fun getDecreeResults(decreeIDs: Iterable<DecreeID>): List<BlaseballDatabaseDecreeResult> =
        client.get("$databaseBaseUrl/decreeResults") { parameter("ids", decreeIDs.joinParams()) }

    suspend fun getElectionResults(season: Int): BlaseballDatabaseOffseasonRecap =
        client.get("$databaseBaseUrl/offseasonRecap") { parameter("season", season) }

    suspend fun getTidingResults(tidingID: TidingID): BlaseballDatabaseTidingResult =
        unwrap(client.get("$databaseBaseUrl/eventResults") { parameter("ids", tidingID.id) })

    suspend fun getTidingResults(tidingID: Iterable<TidingID>): List<BlaseballDatabaseTidingResult> =
        client.get("$databaseBaseUrl/eventResults") { parameter("ids", tidingID.joinParams()) }

    suspend fun getElectionDetails(): BlaseballDatabaseOffseasonSetup =
        client.get("$databaseBaseUrl/offseasonSetup")
}