package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.GameStatsheetID
import dev.brella.kornea.blaseball.base.common.PlayerStatsheetID
import dev.brella.kornea.blaseball.base.common.SeasonStatsheetID
import dev.brella.kornea.blaseball.base.common.TeamStatsheetID
import dev.brella.ktornea.apache.KtorneaApache
import dev.brella.ktornea.common.installGranularHttp
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballStatsheetTests {
    val api = buildBlaseballApiClient()

    @ParameterizedTest(name = "Get Game Statsheets {0}; should have {1} home team batters and {2} away team batters (If this isn't 0 then pog)")
    @CsvSource(
        value = [
            "378468d8-426b-49f0-a276-5e0319b3c1f4,0,0",
            "6340c731-43c9-4fd9-843d-cb8b5af98437,0,0"
        ]
    )
    fun `Get Game Statsheets`(id: String, homeTeamTotalBatters: Int, awayTeamTotalBatters: Int) = runBlocking {
        val gameStatsheet = assertSuccessful(api.getGameStatsheet(GameStatsheetID(id)))
        gameStatsheet.homeTeamTotalBatters assertEquals homeTeamTotalBatters
        gameStatsheet.awayTeamTotalBatters assertEquals awayTeamTotalBatters
    }

    @ParameterizedTest(name = "Get {1}''s statsheets {0}; They've earned {1} runs!")
    @CsvSource(
        value = [
            "eb5f2baf-279c-4ab6-a872-1aecd754df9f,Patty Fox,2",
            "1022d0e9-802b-42af-bb4a-6e7bdea800f5,Stevenson Heat,0",
            "304c0003-9c60-4045-8108-2c799e003834,Beck Whitney,0",
            "2c29db37-6328-4600-86ec-a1b4990a8b71,Paul Barnes,9",
            "1ce419cd-e5ae-4f5f-9fff-1ae0a6dacae6,Helga Burton,0",
            "72d2e372-8231-4652-802f-d9784a4622bc,Mullen Peterson,4",
            "d3faddda-029f-4b02-a967-359fa73fc1c5,Edric Tosser,0"
        ]
    )
    fun `Get Player Statsheets`(id: String, name: String, earnedRuns: Int) = runBlocking {
        val gameStatsheet = assertSuccessful(api.getPlayerStatsheet(PlayerStatsheetID(id)))
        gameStatsheet.name assertEquals name
        gameStatsheet.earnedRuns assertEquals earnedRuns
    }

    @ParameterizedTest(name = "Get the statsheet for the {1}''s ({2} relevant players)")
    @CsvSource(
        value = [
            "7ec42af4-22fe-45d8-bc8d-9af9efa440f1,San Francisco Lovers,10",
            "416850be-936a-49e4-b0c5-cfb2e2f78538,Chicago Firefighters,10"
        ]
    )
    fun `Get Team Statsheets`(id: String, name: String, relevantPlayers: Int) = runBlocking {
        val teamStatsheet = assertSuccessful(api.getTeamStatsheet(TeamStatsheetID(id)))
        teamStatsheet.name assertEquals name
        teamStatsheet.playerStats.size assertEquals relevantPlayers
    }

    @ParameterizedTest(name = "Get the statsheet for Season {1}; Should have {2} teams")
    @CsvSource(
        value = [
            "8b0bb83b-ae1b-4b80-85a7-96eefc2d45cb,1,20",
            "6941ee36-4622-43a0-bbd7-3d71f0ada00a,10,20",
            "aeb9927a-89fd-434b-8924-d1b8f83ed372,15,24"
        ]
    )
    fun `Get Season Statsheets`(id: String, season: Int, expectedTeams: Int) = runBlocking {
        val seasonStatsheet = assertSuccessful(api.getSeasonStatsheet(SeasonStatsheetID(id)))
        seasonStatsheet.teamStats.size assertEquals expectedTeams
    }
}