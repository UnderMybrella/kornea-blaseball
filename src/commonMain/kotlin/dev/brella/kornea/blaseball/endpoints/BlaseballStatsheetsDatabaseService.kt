package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.beans.BlaseballGameStatsheet
import io.ktor.client.request.*

interface BlaseballStatsheetsDatabaseService: BlaseballDatabaseService {
    suspend fun getGameStatsheets(): BlaseballGameStatsheet =
        client.get("$databaseBaseUrl/gameStatsheets") { parameter("ids", "") }

    suspend fun getPlayerStatsheets()
    suspend fun getSeasonStatsheets()
    suspend fun getTeamStatsheets()
}