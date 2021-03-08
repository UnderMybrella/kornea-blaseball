package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.GameID
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseDivision
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseGame
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseLeague
import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseSubleague
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseTeam
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

interface BlaseballDatabaseService : BlaseballService {
    val databaseBaseUrl: String
        get() = "https://www.blaseball.com/database"

    suspend fun getLeague(leagueID: String): BlaseballDatabaseLeague =
        client.get("$databaseBaseUrl/league") { parameter("id", leagueID) }

    suspend fun getSubleague(subleagueID: String): BlaseballDatabaseSubleague =
        client.get("$databaseBaseUrl/subleague") { parameter("id", subleagueID) }

    suspend fun getDivision(divisionID: String): BlaseballDatabaseDivision =
        client.get("$databaseBaseUrl/division") { parameter("id", divisionID) }

    suspend fun getAllTeams(): List<BlaseballDatabaseTeam> =
        client.get("$databaseBaseUrl/allTeams")

    suspend fun getTeam(teamID: String): BlaseballDatabaseTeam =
        client.get("$databaseBaseUrl/team") { parameter("id", teamID) }

    suspend fun getPlayers(vararg playerIDs: String): List<BlaseballDatabasePlayer> =
        client.get("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinToString(",")) }

    suspend fun getPlayers(playerIDs: Iterable<String>): List<BlaseballDatabasePlayer> =
        client.get("$databaseBaseUrl/players") { parameter("ids", playerIDs.joinToString(",")) }

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
}

suspend inline fun BlaseballDatabaseService.getGameById(gameID: String): BlaseballDatabaseGame = getGameById(GameID(gameID))