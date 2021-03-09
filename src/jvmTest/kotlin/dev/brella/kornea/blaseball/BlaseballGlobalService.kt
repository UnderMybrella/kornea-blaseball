package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.beans.Colour
import dev.brella.ktornea.apache.KtorneaApache
import dev.brella.ktornea.common.installGranularHttp
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertTimeout
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballGlobalService {
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

    inline infix fun <T> T.assertEquals(other: T) = assertEquals(other, this)
    inline infix fun <T> T.assertNotEquals(other: T) = assertNotEquals(other, this)

    @Test
    fun `Test Idol Board`() = runBlocking {
        api.getIdolBoard().idols.isNotEmpty() assertEquals true
    }

    @Test
    fun `Test Hall of Flame`() = runBlocking {
        api.getHallOfFlamePlayers().isNotEmpty() assertEquals true
    }

    @ParameterizedTest(name = "Blood Type ''{0}'' == ''{1}''")
    @CsvSource(value = ["0,A"])
    fun `Get Blood Type`(id: String, bloodType: String) = runBlocking {
        api.getBloodTypes(id)[0] assertEquals bloodType
    }

    @ParameterizedTest(name = "Coffee Preference ''{0}'' == ''{1}''")
    @CsvSource(value = ["0,Black"])
    fun `Get Coffee Preference`(id: String, coffeeName: String) = runBlocking {
        api.getCoffeePreferences(id)[0] assertEquals coffeeName
    }

    @Test
    fun `Get Global Events`() = runBlocking {
        api.getGlobalEvents().isNotEmpty() assertEquals true
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Item")
    inner class `Get Item` {
        val hook = runBlocking { api.getItems("GRAPPLING_HOOK")[0] }

        @ParameterizedTest(name = "Item ID is ''{0}''")
        @ValueSource(strings = ["GRAPPLING_HOOK"])
        fun `Item ID`(itemID: String?) {
            hook.id assertEquals itemID
        }

        @ParameterizedTest(name = "Item Name is ''{0}''")
        @ValueSource(strings = ["Grappling Hook"])
        fun `Item Name`(itemName: String?) {
            hook.name assertEquals itemName
        }

        @ParameterizedTest(name = "Item Attr is ''{0}''")
        @NullSource
        fun `Item Attr`(itemAttr: String?) {
            hook.attr assertEquals itemAttr
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Mod")
    inner class `Get Mod` {
        val fireproof = runBlocking { api.getModifications("FIREPROOF")[0] }

        @ParameterizedTest(name = "Mod ID is ''{0}''")
        @ValueSource(strings = ["FIREPROOF"])
        fun `Mod ID`(modID: String) {
            fireproof.id assertEquals modID
        }

        @ParameterizedTest(name = "Mod Colour is ''{0}''")
        @ValueSource(strings = ["#a5c5f0"])
        fun `Mod Colour`(modColour: String) {
            fireproof.color assertEquals Colour.fromHex(modColour)
        }

        @ParameterizedTest(name = "Mod Background Colour is ''{0}''")
        @ValueSource(strings = ["#4c77b0"])
        fun `Mod Background Colour`(modBackground: String) {
            fireproof.background assertEquals Colour.fromHex(modBackground)
        }

        @ParameterizedTest(name = "Mod Text Colour is ''{0}''")
        @ValueSource(strings = ["#a5c5f0"])
        fun `Mod Text Colour`(textColour: String) {
            fireproof.textColor assertEquals Colour.fromHex(textColour)
        }

        @ParameterizedTest(name = "Mod Title is ''{0}''")
        @ValueSource(strings = ["Fireproof"])
        fun `Mod Title`(title: String) {
            fireproof.title assertEquals title
        }

        @ParameterizedTest(name = "Mod Description is ''{0}''")
        @ValueSource(strings = ["A Fireproof player can not be incinerated."])
        fun `Mod Description`(description: String) {
            fireproof.description assertEquals description
        }
    }

    @Test
    fun `Get Simulation Data`() =
        assertDoesNotThrow { runBlocking { api.getSimulationData() } }

    @Test
    fun `Get Live Data Stream`() =
        assertDoesNotThrow {
            runBlocking {
                val liveDataStream = api.getLiveDataStream()
                liveDataStream assertNotEquals null
                withTimeout(5_000) { liveDataStream!!.first() }
            }
        }
}