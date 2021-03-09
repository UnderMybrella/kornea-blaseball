package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.GameStatsheetID
import dev.brella.kornea.blaseball.PlayerStatsheetID
import dev.brella.kornea.blaseball.SeasonStatsheetID
import dev.brella.kornea.blaseball.TeamStatsheetID
import dev.brella.kornea.blaseball.beans.BlaseballGameStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballPlayerStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballSeasonStatsheet
import dev.brella.kornea.blaseball.beans.BlaseballTeamStatsheet
import io.ktor.client.request.*

interface BlaseballStatsheetsDatabaseService : BlaseballDatabaseService {
    suspend fun getGameStatsheets(gameStatsheetID: GameStatsheetID): BlaseballGameStatsheet =
        client.get("$databaseBaseUrl/gameStatsheets") { parameter("ids", gameStatsheetID.uuid) }

    suspend fun getPlayerStatsheets(playerStatsheetID: PlayerStatsheetID): BlaseballPlayerStatsheet =
        client.get("$databaseBaseUrl/playerSeasonStats") { parameter("ids", playerStatsheetID.uuid) }

    suspend fun getSeasonStatsheets(seasonStatsheetID: SeasonStatsheetID): BlaseballSeasonStatsheet =
        client.get("$databaseBaseUrl/seasonStatsheets") { parameter("ids", seasonStatsheetID.uuid) }

    suspend fun getTeamStatsheets(teamStatsheetID: TeamStatsheetID): BlaseballTeamStatsheet =
        client.get("$databaseBaseUrl/teamStatsheets") { parameter("ids", teamStatsheetID.uuid) }
}