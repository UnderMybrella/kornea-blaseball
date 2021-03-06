package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.PlayerID
import dev.brella.kornea.blaseball.base.common.TeamID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballDatabaseTests {
    val api = buildBlaseballApiClient()

    @ParameterizedTest(name = "Get Feed, Season ''{1}'' Phase ''{0}''")
    @CsvSource(value = ["1,11"])
    fun `Get Feed By Phase`(phase: Int, season: Int) {
        assertDoesNotThrow {
            runTest {
                assertSuccessful(api.getFeedByPhase(phase, season))
            }
        }
    }

    @Test
    fun `Get Global Feed`() = runTest {
        assertSuccessful(api.getGlobalFeed(limit = 5))
            .isNotEmpty() assertEquals true
    }

    @ParameterizedTest(name = "Get {1}''s Feed")
    @CsvSource(
        value = [
            "80e474a3-7d2b-431d-8192-2f1e27162607,Summers Preston",
            "0f61d948-4f0c-4550-8410-ae1c7f9f5613,Tamara Crankit",
            "4b3e8e9b-6de1-4840-8751-b1fb45dc5605,Thomas Dracaena",
            "740d5fef-d59f-4dac-9a75-739ec07f91cf,Conner Haley",
            "03b80a57-77ea-4913-9be4-7a85c3594745,Halexandrey Walton",
            "d8ee256f-e3d0-46cb-8c77-b1f88d8c9df9,Comfort Septemberish",
            "90768354-957e-4b4c-bb6d-eab6bbda0ba3,Eugenia Garbage",
            "9965eed5-086c-4977-9470-fe410f92d353,Bates Bentley"
        ]
    )
    fun `Get Player Feed`(playerID: String, playerName: String) =
        runTest {
            assertSuccessful(api.getPlayerFeed(PlayerID(playerID), limit = 5))
                .isNotEmpty() assertEquals true
        }

    @ParameterizedTest(name = "Get {1}''s Feed")
    @CsvSource(
        value = [
            "d9f89a8a-c563-493e-9d64-78e4f9a55d4a,Atlantis Georgias",
            "c73b705c-40ad-4633-a6ed-d357ee2e2bcf,Tokyo Lift",
            "23e4cbc1-e9cd-47fa-a35b-bfa06f726cb7,Philly Pies",
            "b72f3061-f573-40d7-832a-5ad475bd7909,San Francisco Lovers",
            "f02aeae2-5e6a-4098-9842-02d2273f25c7,Hellmouth Sunbeams",
            "bb4a9de5-c924-4923-a0cb-9d1445f1ee5d,Ohio Worms",
            "7966eb04-efcc-499b-8f03-d13916330531,Yellowstone Magic",
            "adc5b394-8f76-416d-9ce9-813706877b84,Kansas City Breath Mints"
        ]
    )
    fun `Get Team Feed`(teamID: String, teamName: String) =
        runTest {
            assertSuccessful(api.getTeamFeed(TeamID(teamID), limit = 5))
                .isNotEmpty() assertEquals true
        }
}