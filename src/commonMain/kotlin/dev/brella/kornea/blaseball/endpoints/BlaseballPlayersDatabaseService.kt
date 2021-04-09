package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.unwrap
import io.ktor.client.request.*

interface BlaseballPlayersDatabaseService: BlaseballDatabaseService {
    suspend fun getPlayer(playerID: String): BlaseballDatabasePlayer =
        unwrap(client.get("$databaseBaseUrl/players") { parameter("ids", playerID) })

    suspend fun getPlayers(vararg playerIDs: String): List<BlaseballDatabasePlayer> =
        client.get("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinToString(",")) }

    suspend fun getPlayers(playerIDs: Iterable<String>): List<BlaseballDatabasePlayer> =
        client.get("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinToString(",")) }
}