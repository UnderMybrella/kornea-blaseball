package dev.brella.kornea.blaseball.chronicler

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

class ChroniclerApi(override val client: HttpClient, override val chroniclerBaseUrl: String = "https://api.sibr.dev/chronicler") :
    ChroniclerService, ChroniclerV1Service