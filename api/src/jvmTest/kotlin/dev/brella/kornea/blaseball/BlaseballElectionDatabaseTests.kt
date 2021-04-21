package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.BlessingID
import dev.brella.kornea.blaseball.base.common.DecreeID
import dev.brella.ktornea.apache.KtorneaApache
import dev.brella.ktornea.common.installGranularHttp
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballElectionDatabaseTests {
    val api = BlaseballApi(HttpClient(KtorneaApache) {
        installGranularHttp()

        install(ContentEncoding) {
            gzip()
            deflate()
            identity()
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
//                ignoreUnknownKeys = true
            })
        }

        expectSuccess = true

        defaultRequest {
            userAgent("kornea-blaseball v1.0.0")
        }
    })

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Blessing Results")
    inner class `Get Blessing Results` {
        val blessing = runBlocking {
            val list = assertSuccessful(
                api.getBlessingResults(listOf(BlessingID("f0f15969-aecb-4f9e-8eb9-00f05ece732c"), BlessingID("cbb567c0-d770-4d22-92f6-ff16ebb94758"), BlessingID("winniehess_mystery")))
            )

            arrayOf(
                list.first { it.id.id == "f0f15969-aecb-4f9e-8eb9-00f05ece732c" },
                list.first { it.id.id == "cbb567c0-d770-4d22-92f6-ff16ebb94758" },
                list.first { it.id.id == "winniehess_mystery" }
            )
        }

        @ParameterizedTest(name = "Blessing[{0}] ID is ''{1}''")
        @CsvSource(value = ["0,f0f15969-aecb-4f9e-8eb9-00f05ece732c", "1,cbb567c0-d770-4d22-92f6-ff16ebb94758"])
        fun `Get Blessing ID`(index: Int, id: String) {
            blessing[index].id.id assertEquals id
        }

        @ParameterizedTest(name = "Blessing[{0}] Bonus ID is ''{1}''")
        @CsvSource(value = ["0,popular_lineup_order", "1,team_improve_hitting"])
        fun `Get Blessing Bonus ID`(index: Int, bonusID: String) {
            blessing[index].bonusId assertEquals bonusID
        }

        @ParameterizedTest(name = "Blessing[{0}] Bonus Title is ''{1}''")
        @CsvSource(value = ["0,Headliners", "1,Hitting Boost"])
        fun `Get Blessing Bonus Title`(index: Int, bonusTitle: String) {
            blessing[index].bonusTitle assertEquals bonusTitle
        }

        @ParameterizedTest(name = "Blessing[{0}] Team ID is ''{1}''")
        @CsvSource(value = ["0,b72f3061-f573-40d7-832a-5ad475bd7909", "1,7966eb04-efcc-499b-8f03-d13916330531"])
        fun `Get Blessing Team ID`(index: Int, teamID: String) {
            blessing[index].teamId.id assertEquals teamID
        }

        @ParameterizedTest(name = "Blessing[{0}] Total Votes is ''{1}''")
        @CsvSource(value = ["0,15631", "1,96"])
        fun `Get Blessing Total Votes`(index: Int, totalVotes: Int) {
            blessing[index].totalVotes assertEquals totalVotes
        }

        @ParameterizedTest(name = "Blessing[{0}] Description is ''{1}''")
        @CsvSource(
            value = [
                "0,Arranged San Francisco Lovers's lineup in order of their Idolatry.\\n1 - Knight Urlacher\\n2 - Don Mitchell\\n3 - Kichiro Guerra\\n4 - Helga Burton\\n5 - Alexander Horne\\n6 - Ortiz Lopez\\n7 - Helga Moreno\\n8 - Theo King\\n9 - Kennedy Meh",
                "1,Improved the Yellowstone Magic's hitting by 10%"
            ]
        )
        fun `Get Blessing Description`(index: Int, description: String) {
            blessing[index].description assertEquals description.replace("\\n", "\n")
        }

        @ParameterizedTest(name = "Blessing[{0}] Highest Team is ''{1}''")
        @CsvSource(value = ["0,b72f3061-f573-40d7-832a-5ad475bd7909", "1,null"], nullValues = ["null"])
        fun `Get Blessing Highest Team`(index: Int, teamID: String?) {
            blessing[index].highestTeam assertEquals teamID
        }

        @ParameterizedTest(name = "Blessing[{0}] Highest Team Votes is ''{1}''")
        @CsvSource(value = ["0,11204", "1,null"], nullValues = ["null"])
        fun `Get Blessing Highest Team Votes`(index: Int, highestTeamVotes: Int?) {
            blessing[index].highestTeamVotes assertEquals highestTeamVotes
        }

        @ParameterizedTest(name = "Blessing[{0}] ID is ''{1}''")
        @CsvSource(value = ["0,11204", "1,null"], nullValues = ["null"])
        fun `Get Blessing Team Votes`(index: Int, teamVotes: Int?) {
            blessing[index].teamVotes assertEquals teamVotes
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Decree Results")
    inner class `Get Decree Results` {
        val decree = runBlocking {
            val list = assertSuccessful(
                api.getDecreeResults(listOf(DecreeID("b090fdfc-7d9d-414b-a4a5-bbc698028c15"), DecreeID("94ca62b2-fbba-4d32-a682-18b524ddd4a8")))
            )

            arrayOf(
                list.first { it.id.id == "b090fdfc-7d9d-414b-a4a5-bbc698028c15" },
                list.first { it.id.id == "94ca62b2-fbba-4d32-a682-18b524ddd4a8" }
            )
        }

        @ParameterizedTest(name = "Decree[{0}] UUID is ''{1}''")
        @CsvSource(value = ["0,b090fdfc-7d9d-414b-a4a5-bbc698028c15", "1,94ca62b2-fbba-4d32-a682-18b524ddd4a8"])
        fun `Get Decree UUID`(index: Int, uuid: String) {
            decree[index].id.id assertEquals uuid
        }

        @ParameterizedTest(name = "Decree[{0}] ID is ''{1}''")
        @CsvSource(value = ["0,forbidden_book", "1,bless_off"])
        fun `Get Decree ID`(index: Int, id: String) {
            decree[index].decreeId assertEquals id
        }

        @ParameterizedTest(name = "Decree[{0}] Title is ''{1}''")
        @CsvSource(value = ["0,Open the Forbidden Book", "1,Bless Off"])
        fun `Get Decree Title`(index: Int, title: String) {
            decree[index].decreeTitle assertEquals title
        }

        @ParameterizedTest(name = "Decree[{0}] Description is ''{1}''")
        @CsvSource(
            value = [
                "0,The Book Opens.\\nSolar Eclipse.\\nUmpires' eyes turn white.\\nStar player Jaylen Hotdogfingers is incinerated.\\nHellmouth swallows the Moab Desert.\\nTHE DISCIPLINE ERA BEGINS",
                "1,BLESS OFF PASSES\\nSILENCE FALLS\\nTHE CRABS ARE DENIED\\nTHE BREATH MINTS ARE DENIED\\nTHE SHOE THIEVES ARE DENIED\\nTHE JAZZ HANDS ARE DENIED\\nIN THE QUIET\\nTHE MICROPHONE CRANKS THE GAIN"
            ]
        )
        fun `Get Decree Description`(index: Int, description: String) {
            decree[index].description assertEquals description.replace("\\n", "\n")
        }

        @ParameterizedTest(name = "Decree[{0}] Total Votes is ''{1}''")
        @CsvSource(value = ["0,566", "1,227274"])
        fun `Get Decree Total Votes`(index: Int, totalVotes: Int) {
            decree[index].totalVotes assertEquals totalVotes
        }
    }
}