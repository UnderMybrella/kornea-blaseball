package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.beans.BlaseballMod
import dev.brella.kornea.blaseball.beans.BlaseballTribute
import dev.brella.ktornea.apache.KtorneaApache
import dev.brella.ktornea.common.installGranularHttp
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballPipelineTests {
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

    @Test
    fun `League To Player`() = assertDoesNotThrow {
        runBlocking {
            val indenting = "\t"

            suspend fun Indented.dumpPlayer(player: BlaseballDatabasePlayer) {
                println("<Player ${player.name}>")
                indent(indenting) {

                    val gameAttributes =
                        if (player.gameAttr.isNotEmpty())
                            api.getModifications(*player.gameAttr.toTypedArray())
                        else
                            emptyList()

                    val permAttributes =
                        if (player.permAttr.isNotEmpty())
                            api.getModifications(*player.permAttr.toTypedArray())
                        else
                            emptyList()

                    val seasonAttributes =
                        if (player.seasAttr.isNotEmpty())
                            api.getModifications(*player.seasAttr.toTypedArray())
                        else
                            emptyList()
                    val weekAttributes =
                        if (player.weekAttr.isNotEmpty())
                            api.getModifications(*player.weekAttr.toTypedArray())
                        else
                            emptyList()

                    println("Game Attributes: ${gameAttributes.joinToString(transform = BlaseballMod::hoverText)}")
                    println("Perm Attributes: ${permAttributes.joinToString(transform = BlaseballMod::hoverText)}")
                    println("Season Attributes: ${seasonAttributes.joinToString(transform = BlaseballMod::hoverText)}")
                    println("Week Attributes: ${weekAttributes.joinToString(transform = BlaseballMod::hoverText)}")

                    println("Items: ${player.items}")
                }
                println("</Player>")
            }

            indent(indenting) {
                val leagueDetails = api.getLeague(LeagueID("d8545021-e9fc-48a3-af74-48685950a183"))
                println("<League ${leagueDetails.name}>")
                indent(indenting) {
                    leagueDetails.subleagues.map { api.getSubleague(it) }
                        .forEach { subleagueDetails ->
                            println("<Subleague ${subleagueDetails.name}>")
                            indent(indenting) {
                                subleagueDetails.divisions
                                    .map { api.getDivision(DivisionID(it)) }
                                    .forEach { divisionDetails ->
                                        println("<Division ${divisionDetails.name}>")

                                        indent(indenting) {
                                            divisionDetails.teams
                                                .map { api.getTeam(it) }
                                                .forEach { teamDetails ->
                                                    println("<Team ${teamDetails.fullName}>")
                                                    indent(indenting) {
                                                        val gameAttributes =
                                                            if (teamDetails.gameAttr.isNotEmpty())
                                                                api.getModifications(*teamDetails.gameAttr.toTypedArray())
                                                            else
                                                                emptyList()

                                                        val permAttributes =
                                                            if (teamDetails.permAttr.isNotEmpty())
                                                                api.getModifications(*teamDetails.permAttr.toTypedArray())
                                                            else
                                                                emptyList()

                                                        println("<Lineup>")
                                                        indent(indenting) {
                                                            api.getPlayers(teamDetails.lineup)
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Lineup>")

                                                        println("<Bullpen>")
                                                        indent(indenting) {
                                                            api.getPlayers(teamDetails.bullpen)
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Bullpen>")

                                                        println("<Bench>")
                                                        indent(indenting) {
                                                            api.getPlayers(teamDetails.bench)
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Bench>")

                                                        println("<Rotation>")
                                                        indent(indenting) {
                                                            api.getPlayers(teamDetails.rotation)
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Rotation>")

                                                        println("Game Attributes: ${gameAttributes.joinToString(transform = BlaseballMod::hoverText)}")
                                                        println("Perm Attributes: ${permAttributes.joinToString(transform = BlaseballMod::hoverText)}")
                                                    }
                                                    println("</Team>")
                                                }
                                        }

                                        println("</Division>")
                                    }
                            }
                            println("</Subleague>")
                        }
                }
                println("</League>")
            }
        }
    }

    @Test
    fun `Hall of Flame Players`() = assertDoesNotThrow {
        runBlocking {
            val tributeBoard = api.getHallOfFlamePlayers()
            val playerList = api.getPlayers(tributeBoard.map { it.playerId.uuid })
            tributeBoard.zip(playerList).forEach { (tribute, player) ->
                println("${player.name} has earned ${tribute.peanuts} peanuts")
            }
        }
    }
}