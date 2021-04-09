package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.BlaseballUUID
import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.unwrap
import io.ktor.client.request.*

interface BlaseballPlayersDatabaseService: BlaseballDatabaseService {
    suspend fun getPlayer(playerID: PlayerID): BlaseballDatabasePlayer =
        unwrap(client.get("$databaseBaseUrl/players") { parameter("ids", playerID.id) })

    suspend fun getPlayers(playerIDs: Iterable<PlayerID>): List<BlaseballDatabasePlayer> =
        client.get("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinToString(",", transform = BlaseballUUID::id)) }
}