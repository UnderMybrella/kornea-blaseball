package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.GameStatsheetID
import dev.brella.kornea.blaseball.PlayerStatsheetID
import dev.brella.kornea.blaseball.SeasonStatsheetID
import dev.brella.kornea.blaseball.TeamStatsheetID
import dev.brella.kornea.blaseball.beans.BlaseballGameStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballPlayerStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballSeasonStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballTeamStatsheet
import dev.brella.kornea.blaseball.unwrap
import io.ktor.client.request.*

interface BlaseballStatsheetsDatabaseService : BlaseballDatabaseService {
    suspend fun getGameStatsheet(gameStatsheetID: GameStatsheetID): BlaseballGameStatsheet =
        unwrap(client.get("$databaseBaseUrl/gameStatsheets") { parameter("ids", gameStatsheetID.id) })

    suspend fun getPlayerStatsheet(playerStatsheetID: PlayerStatsheetID): BlaseballPlayerStatsheet =
        unwrap(client.get("$databaseBaseUrl/playerStatsheets") { parameter("ids", playerStatsheetID.id) })

    suspend fun getSeasonStatsheet(seasonStatsheetID: SeasonStatsheetID): BlaseballSeasonStatsheet =
        unwrap(client.get("$databaseBaseUrl/seasonStatsheets") { parameter("ids", seasonStatsheetID.id) })

    suspend fun getTeamStatsheet(teamStatsheetID: TeamStatsheetID): BlaseballTeamStatsheet =
        unwrap(client.get("$databaseBaseUrl/teamStatsheets") { parameter("ids", teamStatsheetID.id) })
}