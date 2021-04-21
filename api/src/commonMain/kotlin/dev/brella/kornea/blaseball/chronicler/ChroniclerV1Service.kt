package dev.brella.kornea.blaseball.chronicler

import dev.brella.kornea.blaseball.base.common.GameID
import dev.brella.kornea.blaseball.base.common.PlayerID
import dev.brella.kornea.blaseball.base.common.TeamID
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*
import kotlin.math.absoluteValue

interface ChroniclerV1Service : ChroniclerService {
    companion object {
        const val YES_BRELLA_I_WOULD_LIKE_UNLIMITED_GAMES = -9143638
    }

    val chroniclerV1BaseUrl: String
        get() = "$chroniclerBaseUrl/v1"

    suspend fun getGames(
        count: Int,
        order: EnumOrder? = null,
        before: String? = null,
        after: String? = null,
        season: Int? = null,
        day: Int? = null,
        started: Boolean? = null,
        finished: Boolean? = null,
        outcomes: Boolean? = null,
        weather: Iterable<Int>? = null,
        team: Iterable<TeamID>? = null,
        pitcher: Iterable<PlayerID>? = null
    ): KorneaResult<List<ChroniclerGameWrapper>> {
        //0 also counts as infinite from the looks of things, which is **not** what we want
        if (count < 0 && count != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_GAMES) return KorneaResult.empty()

        return unwrapResultData(client.getAsResult("$chroniclerV1BaseUrl/games") {
            if (count != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_GAMES) {

                //Negative values seem to break it
                parameter("count", count.absoluteValue)
            }

            order?.let { parameter("order", it) }
            before?.let { parameter("before", it) }
            after?.let { parameter("after", it) }
            season?.let { parameter("season", it) }
            day?.let { parameter("day", it) }
            started?.let { parameter("started", it) }
            finished?.let { parameter("finished", it) }
            outcomes?.let { parameter("outcomes", it) }
            weather?.let { parameter("weather", it.joinToString(",")) }
            team?.let { parameter("team", it.joinParams()) }
            pitcher?.let { parameter("pitcher", it.joinParams()) }
        })
    }

    suspend fun getGameUpdates(
        count: Int,
        order: EnumOrder? = null,
        before: String? = null,
        after: String? = null,
        page: String? = null,
        game: Iterable<GameID>? = null,
        search: String? = null,
        started: Boolean? = null,
        season: Int? = null
    ): KorneaResult<ChroniclerUpdateResponse> {
        //0 also counts as infinite from the looks of things, which is **not** what we want
        if (count < 0 && count != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_GAMES) return KorneaResult.empty()

        return client.getAsResult("$chroniclerV1BaseUrl/games/updates") {
            if (count != YES_BRELLA_I_WOULD_LIKE_UNLIMITED_GAMES) {

                //Negative values seem to break it
                parameter("count", count.absoluteValue)
            }

            order?.let { parameter("order", it) }
            before?.let { parameter("before", it) }
            after?.let { parameter("after", it) }
            page?.let { parameter("page", it) }
            game?.let { parameter("game", it.joinParams()) }
            search?.let { parameter("search", it) }
            started?.let { parameter("started", it) }
            season?.let { parameter("season", it) }
        }
    }
}