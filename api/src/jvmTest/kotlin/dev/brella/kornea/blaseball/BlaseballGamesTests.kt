package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.PlayoffRoundID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballGamesTests {
    val api = buildBlaseballApiClient()

    @ParameterizedTest(name = "Game[{0}].playCount == {1}")
    @CsvSource(
        value = [
            "de2542b4-d7ba-4a07-9f1b-d1d036fed483,328",
            "db9310b7-f8f8-4e89-bda7-5d1cf716cc94,288",
            "9f8105f8-8d69-4c7d-8f01-2d33be6abaef,252"
        ]
    )
    fun `Get Game By ID`(id: String, playCount: Int) = runBlocking {
        assertSuccessful(api.getGameById(GameID(id))).playCount assertEquals playCount
    }

    @ParameterizedTest(name = "Season {0}, Day {1} - {2} games running")
    @CsvSource(
        value = [
            "1,15,10",
            "2,14,10",
            "3,13,10",
            "4,12,10",
            "5,11,10",
            "6,10,10",
            "7,9,10",
            "8,8,10",
            "9,7,10",
            "10,6,10",
            "11,5,10",
            "12,4,10",
            "13,3,12",
            "14,2,12",
            "15,1,12"
        ]
    )
    fun `Get Games By Date`(season: Int, day: Int, gamesRunning: Int) = runBlocking {
        assertSuccessful(api.getGamesByDate(season = season - 1, day = day - 1)).size assertEquals gamesRunning
    }

    @ParameterizedTest(name = "Season {0}, Day {1}, Tournament {2} - {3} games running")
    @CsvSource(
        value = [
            "10,1,1,8",
            "10,11,1,2",
            "12,1,1,8",
            "12,11,1,2"
        ]
    )
    fun `Get Games By Date With Tournament`(season: Int, day: Int, tournament: Int, gamesRunning: Int) = runBlocking {
        assertSuccessful(api.getGamesByDate(season = season - 1, day = day - 1, tournament = tournament - 1)).size assertEquals gamesRunning
    }

    @ParameterizedTest(name = "{1} of {2} has {3} sets of games listed")
    @CsvSource(
        value = [
            "02e8ac8f-68c1-4f1b-beac-609a655f414b,Round 1,☕ The Coffee Cup ☕,6",
            "31611140-5299-47bb-8b3a-8b92809de348,Round 1,Postseason 12,3",
            "42dad4ad-9993-4049-b4c7-e2ca1a6ab710,Round 1,Postseason 13,3",
            "b89526a8-42d6-4796-8e3b-3299f802e3e2,The Internet Series,Postseason 14,3"
        ]
    )
    fun `Get Playoff Round`(id: String, name: String, seasonName: String, setCount: Int) = runBlocking {
        val playoff = assertSuccessful(api.getPlayoffRound(PlayoffRoundID(id)))
        playoff.name assertEquals name
        playoff.games.size assertEquals setCount
    }

    @ParameterizedTest(name = "{1} had the winner declared as ''{2}''")
    @CsvSource(
        value = [
            "-1,☕ The Coffee Cup ☕,d8f82163-2e74-496b-8e4b-2ab35b2d3ff1",
            "11,Postseason 12,747b8e4a-7e50-4638-a973-ea7950a3e739",
            "12,Postseason 13,8d87c468-699a-47a8-b40d-cfb73a5660ad",
            "13,Postseason 14,eb67ae5e-c4bf-46ca-bbbc-425cd34182ff"
        ]
    )
    fun `Get Playoff Details`(season: Int, name: String, winner: String) = runBlocking {
        val playoff = assertSuccessful(api.getPlayoff(season))
        playoff.name assertEquals name
        playoff.winner assertEquals winner
    }
}