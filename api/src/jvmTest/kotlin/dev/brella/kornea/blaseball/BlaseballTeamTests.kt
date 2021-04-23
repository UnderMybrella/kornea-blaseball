package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.TeamID
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballTeamTests {
    val api = buildBlaseballApiClient()

    @ParameterizedTest(name = "Get All Teams (Should be {0})")
    @CsvSource("41")
    fun `Get All Teams`(teamSize: Int) = runTest {
        assertSuccessful(api.getAllTeams()).size assertEquals teamSize
    }

    @ParameterizedTest(name = "Team[{0}].name == ''{1}''")
    @CsvSource(
        value = [
            "747b8e4a-7e50-4638-a973-ea7950a3e739,Hades Tigers",
            "105bc3ff-1320-4e37-8ef0-8d595cb95dd0,Seattle Garages",
            "b63be8c2-576a-4d6e-8daf-814f8bcea96f,Miami Dale",
            "7966eb04-efcc-499b-8f03-d13916330531,Yellowstone Magic"
        ]
    )
    fun `Get Teams`(teamID: String, teamName: String) = runTest {
        assertSuccessful(api.getTeam(TeamID(teamID))).fullName assertEquals teamName
    }
}