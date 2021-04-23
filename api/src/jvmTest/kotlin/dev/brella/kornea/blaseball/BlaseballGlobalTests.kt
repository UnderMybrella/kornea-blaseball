package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.ModificationID
import dev.brella.kornea.blaseball.base.common.beans.Colour
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballGlobalTests {
    val api = buildBlaseballApiClient()

    @Test
    fun `Test Idol Board`() = runTest {
        assertSuccessful(api.getIdolBoard()).idols.isNotEmpty() assertEquals true
    }

    @Test
    fun `Test Hall of Flame`() = runTest {
        assertSuccessful(api.getHallOfFlamePlayers()).isNotEmpty() assertEquals true
    }

    @ParameterizedTest(name = "Blood Type ''{0}'' == ''{1}''")
    @CsvSource(value = ["0,A"])
    fun `Get Blood Type`(id: String, bloodType: String) = runTest {
        assertSuccessful(api.getBloodType(id)) assertEquals bloodType
    }

    @ParameterizedTest(name = "Coffee Preference ''{0}'' == ''{1}''")
    @CsvSource(value = ["0,Black"])
    fun `Get Coffee Preference`(id: String, coffeeName: String) = runTest {
        assertSuccessful(api.getCoffeePreference(id)) assertEquals coffeeName
    }

    @Test
    fun `Get Global Events`() = runTest {
        assertSuccessful(api.getGlobalEvents()).isNotEmpty() assertEquals true
    }

/*    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Item")
    inner class `Get Item` {
        val item = runTest {
            arrayOf(api.getItems("GRAPPLING_HOOK")[0])
        }

        @ParameterizedTest(name = "Item[{0}] ID is ''{1}''")
        @CsvSource(value = ["0,GRAPPLING_HOOK"])
        fun `Item ID`(index: Int, itemID: String?) {
            item[index].id assertEquals itemID
        }

        @ParameterizedTest(name = "Item[{0}] Name is ''{1}''")
        @CsvSource(value = ["0,Grappling Hook"])
        fun `Item Name`(index: Int, itemName: String?) {
            item[index].name assertEquals itemName
        }

        @ParameterizedTest(name = "Item[{0}] Attr is ''{1}''")
        @CsvSource(value = ["0,null"], nullValues = ["null"])
        fun `Item Attr`(index: Int, itemAttr: String?) {
            item[index].attr assertEquals itemAttr
        }
    }*/

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Get Mod")
    inner class `Get Mod` {
        val mod = runTest {
            arrayOf(assertSuccessful(api.getModification(ModificationID("FIREPROOF"))))
        }

        @ParameterizedTest(name = "Mod[{0}] ID is ''{1}''")
        @CsvSource(value = ["0,FIREPROOF"])
        fun `Mod ID`(index: Int, modID: String) {
            mod[index].id.id assertEquals modID
        }

        @ParameterizedTest(name = "Mod[{0}] Colour is ''{1}''")
        @CsvSource(value = ["0,#a5c5f0"])
        fun `Mod Colour`(index: Int, modColour: String) {
            mod[index].color assertEquals Colour.fromHex(modColour)
        }

        @ParameterizedTest(name = "Mod[{0}] Background Colour is ''{1}''")
        @CsvSource(value = ["0,#4c77b0"])
        fun `Mod Background Colour`(index: Int, modBackground: String) {
            mod[index].background assertEquals Colour.fromHex(modBackground)
        }

        @ParameterizedTest(name = "Mod[{0}] Text Colour is ''{1}''")
        @CsvSource(value = ["0,#a5c5f0"])
        fun `Mod Text Colour`(index: Int, textColour: String) {
            mod[index].textColor assertEquals Colour.fromHex(textColour)
        }

        @ParameterizedTest(name = "Mod[{0}] Title is ''{1}''")
        @CsvSource(value = ["0,Fireproof"])
        fun `Mod Title`(index: Int, title: String) {
            mod[index].title assertEquals title
        }

        @ParameterizedTest(name = "Mod[{0}] Description is ''{1}''")
        @CsvSource(value = ["0,A Fireproof player can not be incinerated."])
        fun `Mod Description`(index: Int, description: String) {
            mod[index].description assertEquals description
        }
    }

    @Test
    fun `Get Simulation Data`() =
        assertDoesNotThrow { runTest { api.getSimulationData() } }

    @Test
    @Disabled
    fun `Get Live Data Stream`() =
        assertDoesNotThrow {
            runTest {
                val liveDataStream = assertSuccessful(api.getLiveDataStream())
                //No clue why this takes 20s?? In testing only??
                withTimeout(30_000) { liveDataStream.firstOrNull() }
            }
        }
}