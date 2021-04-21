package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.PlayerID
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
class BlaseballPlayersTests {
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

    @ParameterizedTest(name = "Player[{0}].name == ''{1}''")
    @CsvSource(
        value = [
            "c0732e36-3731-4f1a-abdc-daa9563b6506,Nagomi Mcdaniel",
            "86d4e22b-f107-4bcf-9625-32d387fcb521,York Silk",
            "3d4545ed-6217-4d7a-9c4a-209265eb6404,Tiana Cash",
            "70ccff1e-6b53-40e2-8844-0a28621cb33e,Moody Cookbook"
        ]
    )
    //Player testing is a bit rough since we can't actually guarantee anything
    fun `Get Players`(uuid: String, name: String) = runBlocking {
        assertSuccessful(api.getPlayer(PlayerID(uuid))).name assertEquals name
    }
}