package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.PlayerID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.blaseball.unwrapResult
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballPlayersDatabaseService: BlaseballDatabaseService {
    suspend fun getPlayer(playerID: PlayerID): KorneaResult<BlaseballDatabasePlayer> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/players") { parameter("ids", playerID.id) })

    suspend fun getPlayers(playerIDs: Iterable<PlayerID>): KorneaResult<List<BlaseballDatabasePlayer>> =
        client.getAsResult("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinParams()) }
}