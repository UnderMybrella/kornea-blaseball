package dev.brella.kornea.blaseball.beans

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

@Serializable
data class BlaseballDatabaseLeague(
    val id: String,
    val subleagues: List<String>,
    val name: String,
    val tiebreakers: String
)

@Serializable
data class BlaseballDatabaseSubleague(
    val id: String,
    val divisions: List<String>,
    val name: String
)

@Serializable
data class BlaseballDatabaseDivision(
    val id: String,
    val teams: List<String>,
    val name: String
)

@Serializable
data class BlaseballDatabaseTeam(
    val id: String,
    val lineup: List<String>,
    val rotation: List<String>,
    val bullpen: List<String>,
    val bench: List<String>,
    val seasAttr: List<String>,
    val permAttr: List<String>,
    val fullName: String,
    val location: String,
    val mainColor: String,
    val nickname: String,
    val secondaryColor: String,
    val shorthand: String,
    val emoji: String,
    val slogan: String,
    val shameRuns: Double,
    val totalShames: Int,
    val totalShamings: Int,
    val seasonShames: Int,
    val seasonShamings: Int,
    val championships: Int,
    val weekAttr: JsonArray,
    val gameAttr: JsonArray,
    val rotationSlot: Int,
    val teamSpirit: Double,
    val card: Int,
    val tournamentWins: Int,
    val stadium: String?,
    val imPosition: Double,
    val eDensity: Double,
    val eVelocity: Double,
    val state: JsonObject,
    val evolution: Double,
    val winStreak: Double
)