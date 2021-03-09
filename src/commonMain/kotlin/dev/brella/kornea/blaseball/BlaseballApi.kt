package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.endpoints.BlaseballDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballElectionDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballGamesDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballGlobalService
import dev.brella.kornea.blaseball.endpoints.BlaseballLeagueDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballPlayersDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballService
import dev.brella.kornea.blaseball.endpoints.BlaseballStatsheetsDatabaseService
import dev.brella.kornea.blaseball.endpoints.BlaseballTeamsDatabaseService
import io.ktor.client.*

class BlaseballApi(override val client: HttpClient, override val blaseballBaseUrl: String = "https://www.blaseball.com") :
    BlaseballService,
    BlaseballDatabaseService,
    BlaseballElectionDatabaseService,
    BlaseballGamesDatabaseService,
    BlaseballGlobalService,
    BlaseballLeagueDatabaseService,
    BlaseballPlayersDatabaseService,
    BlaseballStatsheetsDatabaseService,
    BlaseballTeamsDatabaseService