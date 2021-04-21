package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.BlessingID
import dev.brella.kornea.blaseball.base.common.DecreeID
import dev.brella.kornea.blaseball.base.common.TidingID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseBlessingResult
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseDecreeResult
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseOffseasonRecap
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseOffseasonSetup
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseTidingResult
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.blaseball.unwrapResult
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballElectionDatabaseService : BlaseballDatabaseService {
    suspend fun getBlessingResults(blessingID: BlessingID): KorneaResult<BlaseballDatabaseBlessingResult> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/bonusResults") { parameter("ids", blessingID.id) })

    suspend fun getBlessingResults(blessingIDs: Iterable<BlessingID>): KorneaResult<List<BlaseballDatabaseBlessingResult>> =
        client.getAsResult("$databaseBaseUrl/bonusResults") { parameter("ids", blessingIDs.joinParams()) }

    suspend fun getDecreeResults(decreeID: DecreeID): KorneaResult<List<BlaseballDatabaseDecreeResult>> =
        client.getAsResult("$databaseBaseUrl/decreeResults") { parameter("ids", decreeID.id) }

    suspend fun getDecreeResults(decreeIDs: Iterable<DecreeID>): KorneaResult<List<BlaseballDatabaseDecreeResult>> =
        client.getAsResult("$databaseBaseUrl/decreeResults") { parameter("ids", decreeIDs.joinParams()) }

    suspend fun getElectionResults(season: Int): KorneaResult<BlaseballDatabaseOffseasonRecap> =
        client.getAsResult("$databaseBaseUrl/offseasonRecap") { parameter("season", season) }

    suspend fun getTidingResults(tidingID: TidingID): KorneaResult<BlaseballDatabaseTidingResult> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/eventResults") { parameter("ids", tidingID.id) })

    suspend fun getTidingResults(tidingID: Iterable<TidingID>): KorneaResult<List<BlaseballDatabaseTidingResult>> =
        client.getAsResult("$databaseBaseUrl/eventResults") { parameter("ids", tidingID.joinParams()) }

    suspend fun getElectionDetails(): KorneaResult<BlaseballDatabaseOffseasonSetup> =
        client.getAsResult("$databaseBaseUrl/offseasonSetup")
}