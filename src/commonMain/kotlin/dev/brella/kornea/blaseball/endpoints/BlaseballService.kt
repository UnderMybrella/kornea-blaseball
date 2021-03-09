package dev.brella.kornea.blaseball.endpoints

import io.ktor.client.*

interface BlaseballService {
    val client: HttpClient

    val blaseballBaseUrl: String
        get() = "https://www.blaseball.com"
}