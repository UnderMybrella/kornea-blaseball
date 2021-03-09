package dev.brella.kornea.blaseball

import org.junit.jupiter.api.Assertions

inline infix fun <T> T.assertEquals(other: T) = Assertions.assertEquals(other, this)
inline infix fun <T> T.assertNotEquals(other: T) = Assertions.assertNotEquals(other, this)