package dev.brella.kornea.blaseball.base.common

inline val dev.brella.kornea.blaseball.base.common.UUID.jvm
    get() = java.util.UUID(mostSigBits.toLong(), leastSigBits.toLong())