package dev.brella.kornea.blaseball

import dev.brella.kornea.errors.common.KorneaResult
import dev.brella.ktornea.apache.KtorneaApache
import dev.brella.ktornea.common.installGranularHttp
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline infix fun <T> T.assertEquals(other: T) = Assertions.assertEquals(other, this)
inline infix fun <T> T.assertNotEquals(other: T) = Assertions.assertNotEquals(other, this)

@OptIn(ExperimentalContracts::class)
inline fun <reified T> assertInstance(any: Any?) {
    contract {
        returns() implies (any is T)
    }

    Assertions.assertEquals(any is T, true)
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T> assertSuccessful(result: KorneaResult<T>): T {
    contract {
        returns() implies (result is KorneaResult.Success<T>)
    }

    Assertions.assertEquals(true, result is KorneaResult.Success<T>) { "Result not successful: Is type $result" }

    return result.get()
}

@DslMarker
annotation class IndentBuilder

@IndentBuilder
inline class Indented(val indent: String) {
    constructor(indentLevel: Int, indentCharacter: String = "\t", prefix: String = "") : this(buildString {
        append(prefix)
        repeat(indentLevel) { append(indentCharacter) }
    })

    inline fun println(str: String) = kotlin.io.println("$indent$str")
    inline fun println(builder: StringBuilder.() -> Unit) = kotlin.io.println(buildString {
        append(indent)
        builder()
    })
}

inline fun <T> indent(with: String = "\t", block: Indented.() -> T): T = Indented(with).block()
inline fun <T> Indented.indent(with: String = "\t", block: Indented.() -> T): T = Indented(indent + with).block()

inline fun <T> indent(level: Int, character: String = "\t", block: Indented.() -> T): T = Indented(level, character).block()
inline fun <T> Indented.indent(level: Int, character: String, block: Indented.() -> T): T = Indented(level, character, indent).block()

fun buildBlaseballApiClient() =
    BlaseballApi(HttpClient(OkHttp) {
        installGranularHttp()

        install(ContentEncoding) {
            gzip()
            deflate()
            identity()
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
//                ignoreUnknownKeys = true
            })
        }

        expectSuccess = true

        defaultRequest {
            userAgent("kornea-blaseball v1.0.0")

            timeout {
                this.connectTimeoutMillis = 20_000L
                this.requestTimeoutMillis = 20_000L
                this.socketTimeoutMillis = 20_000L
            }
        }
    })

inline fun <T> runTest(noinline block: suspend CoroutineScope.() -> T): T =
    GlobalScope.async(Dispatchers.IO, block = block).let { runBlocking { it.await() } }