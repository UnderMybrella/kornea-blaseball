package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.base.common.LeagueID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDatabasePlayer
import dev.brella.kornea.blaseball.base.common.beans.BlaseballMod
import dev.brella.kornea.blaseball.base.common.joinParams
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlaseballPipelineTests {
    val api = buildBlaseballApiClient()

    @Test
    fun `League To Player`() = assertDoesNotThrow {
        runTest {
            val indenting = "\t"

            suspend fun Indented.dumpPlayer(player: BlaseballDatabasePlayer) {
                println("<Player ${player.name}>")
                indent(indenting) {

                    val gameAttributes =
                        if (player.gameAttr.isNotEmpty())
                            assertSuccessful(api.getModifications(player.gameAttr))
                        else
                            emptyList()

                    val permAttributes =
                        if (player.permAttr.isNotEmpty())
                            assertSuccessful(api.getModifications(player.permAttr))
                        else
                            emptyList()

                    val seasonAttributes =
                        if (player.seasAttr.isNotEmpty())
                            assertSuccessful(api.getModifications(player.seasAttr))
                        else
                            emptyList()
                    val weekAttributes =
                        if (player.weekAttr.isNotEmpty())
                            assertSuccessful(api.getModifications(player.weekAttr))
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
                val leagueDetails = assertSuccessful(api.getLeague(LeagueID("d8545021-e9fc-48a3-af74-48685950a183")))
                println("<League ${leagueDetails.name}>")
                indent(indenting) {
                    leagueDetails.subleagues.map { assertSuccessful(api.getSubleague(it)) }
                        .forEach { subleagueDetails ->
                            println("<Subleague ${subleagueDetails.name}>")
                            indent(indenting) {
                                subleagueDetails.divisions
                                    .map { assertSuccessful(api.getDivision(it)) }
                                    .forEach { divisionDetails ->
                                        println("<Division ${divisionDetails.name}>")

                                        indent(indenting) {
                                            divisionDetails.teams
                                                .map { assertSuccessful(api.getTeam(it)) }
                                                .forEach { teamDetails ->
                                                    println("<Team ${teamDetails.fullName}>")
                                                    indent(indenting) {
                                                        val gameAttributes =
                                                            if (teamDetails.gameAttr.isNotEmpty())
                                                                assertSuccessful(api.getModifications(teamDetails.gameAttr))
                                                            else
                                                                emptyList()

                                                        val permAttributes =
                                                            if (teamDetails.permAttr.isNotEmpty())
                                                                assertSuccessful(api.getModifications(teamDetails.permAttr))
                                                            else
                                                                emptyList()

                                                        println("<Lineup>")
                                                        indent(indenting) {
                                                            println(teamDetails.lineup.joinParams())
                                                            assertSuccessful(api.getPlayers(teamDetails.lineup))
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Lineup>")

                                                        println("<Bullpen>")
                                                        indent(indenting) {
                                                            assertSuccessful(api.getPlayers(teamDetails.bullpen))
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Bullpen>")

                                                        println("<Bench>")
                                                        indent(indenting) {
                                                            assertSuccessful(api.getPlayers(teamDetails.bench))
                                                                .forEach { player ->
                                                                    dumpPlayer(player)
                                                                }
                                                        }
                                                        println("</Bench>")

                                                        println("<Rotation>")
                                                        indent(indenting) {
                                                            assertSuccessful(api.getPlayers(teamDetails.rotation))
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
        runTest {
            val tributeBoard = assertSuccessful(api.getHallOfFlamePlayers())
            val playerList = assertSuccessful(api.getPlayers(tributeBoard.map { it.playerId }))
            tributeBoard.zip(playerList).forEach { (tribute, player) ->
                println("${player.name} has earned ${tribute.peanuts} peanuts")
            }
        }
    }
}