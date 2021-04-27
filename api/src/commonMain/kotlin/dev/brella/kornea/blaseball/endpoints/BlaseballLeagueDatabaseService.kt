package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.base.common.DivisionID
import dev.brella.kornea.blaseball.base.common.LeagueID
import dev.brella.kornea.blaseball.base.common.PlayoffMatchupID
import dev.brella.kornea.blaseball.base.common.StandingsID
import dev.brella.kornea.blaseball.base.common.SubleagueID
import dev.brella.kornea.blaseball.base.common.TiebreakerID
import dev.brella.kornea.blaseball.base.common.beans.BlaseballDivision
import dev.brella.kornea.blaseball.base.common.beans.BlaseballLeague
import dev.brella.kornea.blaseball.base.common.beans.BlaseballPlayoffMatchup
import dev.brella.kornea.blaseball.base.common.beans.BlaseballSeasonBean
import dev.brella.kornea.blaseball.base.common.beans.BlaseballStandings
import dev.brella.kornea.blaseball.base.common.beans.BlaseballSubleague
import dev.brella.kornea.blaseball.base.common.beans.BlaseballTiebreaker
import dev.brella.kornea.blaseball.base.common.joinParams
import dev.brella.kornea.blaseball.unwrapResult
import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.common.getAsResult
import io.ktor.client.request.*

interface BlaseballLeagueDatabaseService: BlaseballDatabaseService {
    suspend fun getAllDivisions(): KorneaResult<List<BlaseballDivision>> =
        client.getAsResult("$databaseBaseUrl/allDivisions")

    suspend fun getDivision(divisionID: DivisionID): KorneaResult<BlaseballDivision> =
        client.getAsResult("$databaseBaseUrl/division") { parameter("id", divisionID.id) }

    suspend fun getLeague(leagueID: LeagueID): KorneaResult<BlaseballLeague> =
        client.getAsResult("$databaseBaseUrl/league") { parameter("id", leagueID.id) }

    suspend fun getPlayoffMatchup(matchupID: PlayoffMatchupID): KorneaResult<BlaseballPlayoffMatchup> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/playoffMatchups") { parameter("ids", matchupID.id) })

    suspend fun getPlayoffMatchups(matchupIDs: Iterable<PlayoffMatchupID>): KorneaResult<List<BlaseballPlayoffMatchup>> =
        client.getAsResult("$databaseBaseUrl/playoffMatchups") { parameter("ids", matchupIDs.joinParams()) }

    suspend fun getSeason(season: Int): KorneaResult<BlaseballSeasonBean> =
        client.getAsResult("$databaseBaseUrl/season") { parameter("number", season) }

    suspend fun getStandings(standingsID: StandingsID): KorneaResult<BlaseballStandings> =
        client.getAsResult("$databaseBaseUrl/standings") { parameter("id", standingsID.id) }

    suspend fun getSubleague(subleagueID: SubleagueID): KorneaResult<BlaseballSubleague> =
        client.getAsResult("$databaseBaseUrl/subleague") { parameter("id", subleagueID.id) }

    suspend fun getTiebreaker(tiebreakerID: TiebreakerID): KorneaResult<BlaseballTiebreaker> =
        unwrapResult(client.getAsResult("$databaseBaseUrl/tiebreakers") { parameter("id", tiebreakerID.id) })
}