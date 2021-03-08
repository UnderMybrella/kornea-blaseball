package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.beans.BlaseballDatabaseDivision
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseLeague
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseSubleague
import dev.brella.kornea.blaseball.beans.BlaseballDatabaseTeam
import io.ktor.client.request.*

interface BlaseballDatabaseService : BlaseballService {
    val databaseBaseUrl: String
        get() = "https://www.blaseball.com/database"

    suspend fun getLeague(leagueID: String): BlaseballDatabaseLeague =
        client.get("$databaseBaseUrl/league") { parameter("id", leagueID) }

    suspend fun getSubleague(subleagueID: String): BlaseballDatabaseSubleague =
        client.get("$databaseBaseUrl/subleague") { parameter("id", subleagueID) }

    suspend fun getDivision(divisionID: String): BlaseballDatabaseDivision =
        client.get("$databaseBaseUrl/division") { parameter("id", divisionID) }

    suspend fun getTeam(teamID: String): BlaseballDatabaseTeam =
        client.get("$databaseBaseUrl/team") { parameter("id", teamID) }
}