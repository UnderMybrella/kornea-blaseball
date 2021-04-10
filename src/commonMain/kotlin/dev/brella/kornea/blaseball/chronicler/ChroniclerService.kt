package dev.brella.kornea.blaseball.chronicler

import io.ktor.client.*

interface ChroniclerService {
    val client: HttpClient

    val chroniclerBaseUrl: String
        get() = "https://api.sibr.dev/chronicler"
}