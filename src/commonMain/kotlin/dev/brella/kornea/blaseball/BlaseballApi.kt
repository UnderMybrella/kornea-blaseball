package dev.brella.kornea.blaseball

import dev.brella.kornea.blaseball.endpoints.BlaseballEventsService
import dev.brella.kornea.blaseball.endpoints.BlaseballService
import io.ktor.client.*

class BlaseballApi(override val client: HttpClient): BlaseballService, BlaseballEventsService {
}