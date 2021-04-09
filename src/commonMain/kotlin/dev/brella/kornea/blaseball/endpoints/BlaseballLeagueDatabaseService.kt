package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.DivisionID
import dev.brella.kornea.blaseball.LeagueID
import dev.brella.kornea.blaseball.PlayoffMatchupID
import dev.brella.kornea.blaseball.StandingsID
import dev.brella.kornea.blaseball.SubleagueID
import dev.brella.kornea.blaseball.TiebreakerID
import dev.brella.kornea.blaseball.beans.BlaseballDivision
import dev.brella.kornea.blaseball.beans.BlaseballLeague
import dev.brella.kornea.blaseball.beans.BlaseballPlayoffMatchup
import dev.brella.kornea.blaseball.beans.BlaseballSeason
import dev.brella.kornea.blaseball.beans.BlaseballStandings
import dev.brella.kornea.blaseball.beans.BlaseballSubleague
import dev.brella.kornea.blaseball.beans.BlaseballTiebreaker
import dev.brella.kornea.blaseball.joinParams
import dev.brella.kornea.blaseball.unwrap
import io.ktor.client.request.*

interface BlaseballLeagueDatabaseService: BlaseballDatabaseService {
    suspend fun getAllDivisions(): List<BlaseballDivision> =
        client.get("$databaseBaseUrl/allDivisions")

    suspend fun getDivision(divisionID: DivisionID): BlaseballDivision =
        client.get("$databaseBaseUrl/division") { parameter("id", divisionID.id) }

    suspend fun getLeague(leagueID: LeagueID): BlaseballLeague =
        client.get("$databaseBaseUrl/league") { parameter("id", leagueID.id) }

    suspend fun getPlayoffMatchup(matchupID: PlayoffMatchupID): BlaseballPlayoffMatchup =
        unwrap(client.get("$databaseBaseUrl/playoffMatchups") { parameter("ids", matchupID.id) })

    suspend fun getPlayoffMatchups(matchupIDs: Iterable<PlayoffMatchupID>): List<BlaseballPlayoffMatchup> =
        client.get("$databaseBaseUrl/playoffMatchups") { parameter("ids", matchupIDs.joinParams()) }

    suspend fun getSeason(season: Int): BlaseballSeason =
        client.get("$databaseBaseUrl/season") { parameter("number", season) }

    suspend fun getStandings(standingsID: StandingsID): BlaseballStandings =
        client.get("$databaseBaseUrl/standings") { parameter("id", standingsID.id) }

    suspend fun getSubleague(subleagueID: SubleagueID): BlaseballSubleague =
        client.get("$databaseBaseUrl/subleague") { parameter("id", subleagueID.id) }

    suspend fun getTiebreaker(tiebreakerID: TiebreakerID): BlaseballTiebreaker =
        unwrap(client.get("$databaseBaseUrl/tiebreakers") { parameter("id", tiebreakerID.id) })
}