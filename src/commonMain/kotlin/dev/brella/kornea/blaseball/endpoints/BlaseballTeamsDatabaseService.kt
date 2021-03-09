package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.TeamID
import dev.brella.kornea.blaseball.beans.BlaseballTeam
import io.ktor.client.request.*

interface BlaseballTeamsDatabaseService : BlaseballDatabaseService {
    suspend fun getAllTeams(): List<BlaseballTeam> =
        client.get("$databaseBaseUrl/allTeams")

    suspend fun getTeam(teamID: TeamID): BlaseballTeam =
        client.get("$databaseBaseUrl/team") { parameter("id", teamID.uuid) }
}