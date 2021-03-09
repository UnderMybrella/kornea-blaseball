package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.GameID
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseGame
import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayoffRound
import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayoffs
import io.ktor.client.request.*

interface BlaseballGamesDatabaseService: BlaseballDatabaseService {
    suspend fun getGameById(gameID: GameID): BlaseballDatabaseGame =
        client.get("$databaseBaseUrl/gameById/${gameID.uuid}")

    suspend fun getGamesByDate(day: Int, season: Int): List<BlaseballDatabaseGame> =
        client.get("$databaseBaseUrl/games") {
            parameter("season", season)
            parameter("day", day)
        }

    suspend fun getGamesByDate(day: Int, season: Int, tournament: Int): List<BlaseballDatabaseGame> =
        client.get("$databaseBaseUrl/games") {
            parameter("season", season)
            parameter("day", day)
            parameter("tournament", tournament)
        }

    suspend fun getPlayoffRound(id: String): BlaseballDatabasePlayoffRound =
        client.get("$databaseBaseUrl/playoffRound") { parameter("id", id) }

    suspend fun getPlayoff(season: Int): BlaseballDatabasePlayoffs =
        client.get("$databaseBaseUrl/playoffs") { parameter("number", season) }
}

suspend inline fun BlaseballGamesDatabaseService.getGameById(gameID: String): BlaseballDatabaseGame = getGameById(GameID(gameID))