package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.PlayoffRoundID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabaseGame
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabasePlayoffRound
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabasePlayoffs
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballGamesDatabaseService: BlaseballDatabaseService {
    suspend fun getGameById(gameID: GameID): KorneaResult<BlaseballDatabaseGame> =
        client.getAsResult("$databaseBaseUrl/gameById/${gameID.id}")

    suspend fun getGamesByDate(season: Int, day: Int): KorneaResult<List<BlaseballDatabaseGame>> =
        client.getAsResult("$databaseBaseUrl/games") {
            parameter("season", season)
            parameter("day", day)
        }

    suspend fun getGamesByDate(season: Int, day: Int, tournament: Int): KorneaResult<List<BlaseballDatabaseGame>> =
        client.getAsResult("$databaseBaseUrl/games") {
            parameter("season", season)
            parameter("day", day)
            parameter("tournament", tournament)
        }

    suspend fun getPlayoffRound(id: PlayoffRoundID): KorneaResult<BlaseballDatabasePlayoffRound> =
        client.getAsResult("$databaseBaseUrl/playoffRound") { parameter("id", id.id) }

    suspend fun getPlayoff(season: Int): KorneaResult<BlaseballDatabasePlayoffs> =
        client.getAsResult("$databaseBaseUrl/playoffs") { parameter("number", season) }
}