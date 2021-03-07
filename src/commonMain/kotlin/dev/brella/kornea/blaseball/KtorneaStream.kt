package dev.brella.kornea.blaseball

import dev.brella.kornea.io.common.BinaryDataPool
import dev.brella.kornea.io.common.flow.InputFlow
import dev.brella.kornea.toolkit.common.clearToString
import dev.brella.ktornea.common.cleanup
import dev.brella.ktornea.common.executeStatement
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

suspend inline fun HttpClient.stream(builder: HttpRequestBuilder.() -> Unit): Flow<String>? =
    executeStatement(builder).let { call ->
        val response = call.response

        if (response.contentType()?.match("text/event-stream") != true) {
            response.cleanup()
            return@let null
        } else channelFlow<String> {
            val pool = BinaryDataPool(null, null, 1)
            val content = response.content
            val buffer = ByteArray(8192)

            val binaryInput = pool.openInputFlow().get()
            val binaryOutput = pool.openOutputFlow().get()

            val readContent = launch {
                while (isActive && !content.isClosedForRead) {
                    if (content.availableForRead > 0) {
                        val read = content.readAvailable(buffer)
                        binaryOutput.write(buffer, 0, read)
                    }

                    yield()
                }

                println("Closed for read!")
            }

            val writeContent = launch {
                val content = content
                val builder = StringBuilder()
                var c: Char?

                while (isActive) {
                    c = binaryInput.readUtf8Character()

                    when (c) {
                        null -> {
                            if (!readContent.isActive) {
                                if (builder.isNotBlank()) send(builder.clearToString())
                                break
                            }

                            yield()
                            continue
                        }
                        '\n' -> if (builder.isNotBlank()) send(builder.clearToString())
                        else -> builder.append(c)
                    }
                }
            }

            joinAll(readContent, writeContent)

            response.cleanup()
        }
    }

suspend fun InputFlow.readUtf8Character(): Char? {
    val a = read() ?: return null

    when {
        a and 0xF0 == 0xF0 -> {
            val b = read() ?: return null
            val c = read() ?: return null
            val d = read() ?: return null

            return (((a and 0xF) shl 18) or
                    ((b and 0x3F) shl 12) or
                    ((c and 0x3F) shl 6) or
                    ((d and 0x3F) shl 0)).toChar()
        }
        a and 0xE0 == 0xE0 -> {
            val b = read() ?: return null
            val c = read() ?: return null

            return (((a and 0xF) shl 12) or
                    ((b and 0x3F) shl 6) or
                    ((c and 0x3F) shl 0)).toChar()
        }
        a and 0xC0 == 0xC0 -> {
            val b = read() ?: return null

            return (((a and 0xF) shl 6) or
                    ((b and 0x3F) shl 0)).toChar()
        }
        a and 0x80 == 0x80 -> return null
        else -> return a.toChar()
    }
}