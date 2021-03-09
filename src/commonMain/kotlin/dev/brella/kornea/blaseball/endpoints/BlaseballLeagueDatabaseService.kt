package dev.brella.kornea.blaseball.endpoints

import dev.brella.kornea.blaseball.DivisionID
import dev.brella.kornea.blaseball.LeagueID
import dev.brella.kornea.blaseball.StandingsID
import dev.brella.kornea.blaseball.beans.BlaseballDivision
import dev.brella.kornea.blaseball.beans.BlaseballLeague
import dev.brella.kornea.blaseball.beans.BlaseballPlayoffMatchup
import dev.brella.kornea.blaseball.beans.BlaseballSeason
import dev.brella.kornea.blaseball.beans.BlaseballStandings
import dev.brella.kornea.blaseball.beans.BlaseballSubleague
import dev.brella.kornea.blaseball.beans.BlaseballTiebreaker
import io.ktor.client.request.*

interface BlaseballLeagueDatabaseService: BlaseballDatabaseService {
    suspend fun getAllDivisions(): List<BlaseballDivision> =
        client.get("$databaseBaseUrl/allDivisions")

    suspend fun getDivision(divisionID: DivisionID): BlaseballDivision =
        client.get("$databaseBaseUrl/division") { parameter("id", divisionID.uuid) }

    suspend fun getLeague(leagueID: LeagueID): BlaseballLeague =
        client.get("$databaseBaseUrl/league") { parameter("id", leagueID.uuid) }

    suspend fun getPlayoffMatchups(vararg matchupIDs: String): List<BlaseballPlayoffMatchup> =
        client.get("$databaseBaseUrl/playoffMatchups") { parameter("ids", matchupIDs.joinToString(",")) }

    suspend fun getSeason(season: Int): BlaseballSeason =
        client.get("$databaseBaseUrl/season") { parameter("number", season) }

    suspend fun getStandings(standingsID: StandingsID): BlaseballStandings =
        client.get("$databaseBaseUrl/standings") { parameter("id", standingsID.uuid) }

    suspend fun getSubleague(subleagueID: String): BlaseballSubleague =
        client.get("$databaseBaseUrl/subleague") { parameter("id", subleagueID) }

    suspend fun getTiebreakers(tiebreakerID: String): BlaseballTiebreaker =
        client.get("$databaseBaseUrl/tiebreakers") { parameter("id", tiebreakerID) }
}