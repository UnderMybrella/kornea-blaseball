package dev.brella.kornea.blaseball

import org.junit.jupiter.api.Assertions

inline infix fun <T> T.assertEquals(other: T) = Assertions.assertEquals(other, this)
inline infix fun <T> T.assertNotEquals(other: T) = Assertions.assertNotEquals(other, this)

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