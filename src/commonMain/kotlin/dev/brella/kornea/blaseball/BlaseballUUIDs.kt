package dev.brella.kornea.blaseball

import kotlinx.serialization.Serializable

interface BlaseballUUID: CharSequence {
    val uuid: String

    override val length: Int
        get() = uuid.length

    override fun get(index: Int): Char = uuid[index]
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        uuid.subSequence(startIndex, endIndex)
}

@Serializable
inline class GameID(override val uuid: String): BlaseballUUID