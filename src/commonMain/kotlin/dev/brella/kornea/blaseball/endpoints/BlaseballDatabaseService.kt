package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.PlayerID
import dev.brella.kornea.blaseball.TeamID
import dev.brella.kornea.blaseball.beans.*
import io.ktor.client.request.*

interface BlaseballDatabaseService : BlaseballService {
    companion object {
        const val YES_BRELLA_I_WOULD_LIKE_UNLIMITED_EVENTS = -6543583
    }

    val databaseBaseUrl: String
        get() = "$blaseballBaseUrl/database"

    suspend fun getFeedByPhase(phase: Int, season: Int): List<BlaseballFeedEvent> =
        client.get("$databaseBaseUrl/feedbyphase") {
            parameter("phase", phase)
            parameter("season", season)
        }

    suspend fun getGlobalFeed(category: Int? = null, limit: Int = 100, type: Int? = null, sort: Int? = null, start: String? = null): List<BlaseballFeedEvent> =
        client.get("$databaseBaseUrl/feed/global") {
            if (category != null) parameter("category", category)
            if (type != null) parameter("type", type)
            if (limit != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_EVENTS) parameter("limit", limit)
            if (sort != null) parameter("sort", sort)
            if (start != null) parameter("start", start)
        }

    suspend fun getPlayerFeed(playerID: PlayerID, category: Int? = null, limit: Int = 100, type: Int? = null, sort: Int? = null, start: String? = null): List<BlaseballFeedEvent> =
        client.get("$databaseBaseUrl/feed/player") {
            parameter("id", playerID.id)

            if (category != null) parameter("category", category)
            if (type != null) parameter("type", type)
            if (limit != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_EVENTS) parameter("limit", limit)
            if (sort != null) parameter("sort", sort)
            if (start != null) parameter("start", start)
        }

    suspend fun getTeamFeed(teamID: TeamID, category: Int? = null, limit: Int = 100, type: Int? = null, sort: Int? = null, start: String? = null): List<BlaseballFeedEvent> =
        client.get("$databaseBaseUrl/feed/team") {
            parameter("id", teamID.id)

            if (category != null) parameter("category", category)
            if (type != null) parameter("type", type)
            if (limit != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_EVENTS) parameter("limit", limit)
            if (sort != null) parameter("sort", sort)
            if (start != null) parameter("start", start)
        }
}