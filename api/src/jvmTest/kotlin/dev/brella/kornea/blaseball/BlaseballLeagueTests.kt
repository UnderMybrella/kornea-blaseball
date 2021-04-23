package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.DivisionID
import dev.brella.kornea.blaseball.base.common.LeagueID
import dev.brella.kornea.blaseball.base.common.PlayoffMatchupID
import dev.brella.kornea.blaseball.base.common.SubleagueID
import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.TiebreakerID
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballLeagueTests {
    val api = buildBlaseballApiClient()

    @ParameterizedTest(name = "Get League Divisions, which should be {0} long and contain ''{1}''")
    @CsvSource(
        value = ["8,Wild High"]
    )
    fun `Get All Divisions`(size: Int, startsWith: String) = runTest {
        val divisions = assertSuccessful(api.getAllDivisions())
        divisions.size assertEquals size
        divisions.any { it.name == startsWith } assertEquals true
    }

    @ParameterizedTest(name = "League Division[{0}].name == ''{1}''")
    @CsvSource(
        value = [
            "d4cc18de-a136-4271-84f1-32516be91a80,Wild High",
            "98c92da4-0ea7-43be-bd75-c6150e184326,Wild Low",
            "456089f0-f338-4620-a014-9540868789c9,Mild High",
            "f711d960-dc28-4ae2-9249-e1f320fec7d7,Lawful Good",
            "5eb2271a-3e49-48dc-b002-9cb615288836,Chaotic Good",
            "765a1e03-4101-4e8e-b611-389e71d13619,Lawful Evil",
            "7fbad33c-59ab-4e80-ba63-347177edaa2e,Chaotic Evil",
            "fadc9684-45b3-47a6-b647-3be3f0735a84,Mild Low"
        ]
    )
    fun `Get League Division`(uuid: String, name: String) = runTest {
        assertSuccessful(api.getDivision(DivisionID(uuid))).name assertEquals name
    }

    @ParameterizedTest(name = "League[{0}].name == ''{1}''")
    @CsvSource(
        value = ["d8545021-e9fc-48a3-af74-48685950a183,Internet League Blaseball"]
    )
    fun `Get League`(uuid: String, name: String) = runTest {
        assertSuccessful(api.getLeague(LeagueID(uuid))).name assertEquals name
    }

    @ParameterizedTest(name = "Playoff Matchup[{0}]'s home team was ''{1}'', away team was ''{2}''")
    @CsvSource(
        value = [
            "3be7440a-7cd4-461c-ad22-fa256fe8d89b,747b8e4a-7e50-4638-a973-ea7950a3e739,",
            "0f62a795-06c2-4339-901b-9e42b12538cc,ca3f1c8c-c025-4d8e-8eef-5be6accbeb16,57ec08cc-0411-4643-b304-0e80dbc15ac7",
            "2b6550de-98b3-4a7d-a3da-59e62ddebf41,7966eb04-efcc-499b-8f03-d13916330531,3f8bbb15-61c0-4e3f-8e4a-907a5fb1565e"
        ]
    )
    fun `Get Playoff Matchup`(uuid: String, homeTeam: String?, awayTeam: String?) = runTest {
        assertSuccessful(api.getPlayoffMatchup(PlayoffMatchupID(uuid)))
            .homeTeam?.id assertEquals homeTeam?.takeUnless(String::isBlank)
    }

    @ParameterizedTest(name = "Season {0}''s UUID should be ''{1}''")
    @CsvSource(
        value = [
            "1,9dbb3119-7e0d-4fa7-bab9-caef1c19e03d",
            "5,9465ef83-c4c7-4aaa-83d0-d78854652618",
            "10,b837b5b6-7542-40fb-9873-dc9557bcb695",
            "15,645cdd84-175f-42f1-a9f3-d9014d97ae3b"
        ]
    )
    fun `Get Season Details`(season: Int, id: String) = runTest {
        val season = assertSuccessful(api.getSeason(season - 1))
        season.id.id assertEquals id
    }

    @ParameterizedTest(name = "In Season {0}, the {1} should be going {3}-{4}")
    @CsvSource(
        value = [
            "5,San Francisco Lovers,b72f3061-f573-40d7-832a-5ad475bd7909,65,34",
            "10,Yellowstone Magic,7966eb04-efcc-499b-8f03-d13916330531,32,67",
            "14,Houston Spies,9debc64f-74b7-4ae1-a4d6-fce0144b6ea5,49,50"
        ]
    )
    fun `Get Season Standings`(season: Int, name: String, teamUID: String, wins: Int, losses: Int) = runTest {
        val season = assertSuccessful(api.getSeason(season - 1))
        val standings = assertSuccessful(api.getStandings(season.standings))

        standings.wins[TeamID(teamUID)] assertEquals wins
        standings.losses[TeamID(teamUID)] assertEquals losses
    }

    @ParameterizedTest(name = "Subleague[{0}].name == ''{1}'' (Should have {2} divisions!)")
    @CsvSource(
        value = [
            "aabc11a1-81af-4036-9f18-229c759ca8a9,The Wild League,2",
            "4fe65afa-804f-4bb2-9b15-1281b2eab110,The Mild League,2"
        ]
    )
    fun `Get Subleague`(uid: String, name: String, divisions: Int) = runTest {
        val subleague = assertSuccessful(api.getSubleague(SubleagueID(uid)))

        subleague.name assertEquals name
        subleague.divisions.size assertEquals divisions
    }

    @ParameterizedTest(name = "Tiebreaker {0} should have {1} teams!")
    @CsvSource(
        value = [
            "de6896bf-02de-442a-87df-4b77b1d3f4b2,24"
        ]
    )
    fun `Get Tiebreaker`(uid: String, teams: Int) = runTest {
        val tiebreaker = assertSuccessful(api.getTiebreaker(TiebreakerID(uid)))

        tiebreaker.order.size assertEquals teams
    }
}