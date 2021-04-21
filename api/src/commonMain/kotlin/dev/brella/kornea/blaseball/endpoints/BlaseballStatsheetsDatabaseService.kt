package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.GameStatsheetID
import dev.brella.kornea.blaseball.base.common.PlayerStatsheetID
import dev.brella.kornea.blaseball.base.common.SeasonStatsheetID
import dev.brella.kornea.blaseball.base.common.TeamStatsheetID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballGameStatsheet
import dev.brella.kornea.blaseball.base.common.beans.BlaseballPlayerStatsheet
import dev.brella.kornea.blaseball.base.common.beans.BlaseballSeasonStatsheet
import dev.brella.kornea.blaseball.base.common.beans.BlaseballTeamStatsheet
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.blaseball.unwrapResult
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballStatsheetsDatabaseService : BlaseballDatabaseService {
    suspend fun getGameStatsheet(gameStatsheetID: GameStatsheetID): KorneaResult<BlaseballGameStatsheet> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/gameStatsheets") { parameter("ids", gameStatsheetID.id) })

    suspend fun getGameStatsheets(gameStatsheetIDs: Iterable<GameStatsheetID>): KorneaResult<List<BlaseballGameStatsheet>> =
        client.getAsResult("$databaseBaseUrl/gameStatsheets") { parameter("ids", gameStatsheetIDs.joinParams()) }


    suspend fun getPlayerStatsheet(playerStatsheetID: PlayerStatsheetID): KorneaResult<BlaseballPlayerStatsheet> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/playerStatsheets") { parameter("ids", playerStatsheetID.id) })

    suspend fun getPlayerStatsheets(playerStatsheetIDs: Iterable<PlayerStatsheetID>): KorneaResult<List<BlaseballPlayerStatsheet>> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/playerStatsheets") { parameter("ids", playerStatsheetIDs.joinParams()) })


    suspend fun getSeasonStatsheet(seasonStatsheetID: SeasonStatsheetID): KorneaResult<BlaseballSeasonStatsheet> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/seasonStatsheets") { parameter("ids", seasonStatsheetID.id) })

    suspend fun getSeasonStatsheets(seasonStatsheetIDs: Iterable<SeasonStatsheetID>): KorneaResult<List<BlaseballSeasonStatsheet>> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/seasonStatsheets") { parameter("ids", seasonStatsheetIDs.joinParams()) })


    suspend fun getTeamStatsheet(teamStatsheetID: TeamStatsheetID): KorneaResult<BlaseballTeamStatsheet> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/teamStatsheets") { parameter("ids", teamStatsheetID.id) })

    suspend fun getTeamStatsheets(teamStatsheetIDs: Iterable<TeamStatsheetID>): KorneaResult<List<BlaseballTeamStatsheet>> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/teamStatsheets") { parameter("ids", teamStatsheetIDs.joinParams()) })
}