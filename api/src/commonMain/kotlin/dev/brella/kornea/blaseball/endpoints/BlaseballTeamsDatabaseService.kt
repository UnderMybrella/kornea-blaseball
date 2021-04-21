package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballTeam
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballTeamsDatabaseService : BlaseballDatabaseService {
    suspend fun getAllTeams(): KorneaResult<List<BlaseballTeam>> =
        client.getAsResult("$databaseBaseUrl/allTeams")

    suspend fun getTeam(teamID: TeamID): KorneaResult<BlaseballTeam> =
        client.getAsResult("$databaseBaseUrl/team") { parameter("id", teamID.id) }
}