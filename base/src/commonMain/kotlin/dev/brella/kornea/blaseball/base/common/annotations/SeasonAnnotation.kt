package dev.brella.kornea.blaseball.base.common.annotations

annotation class AddedIn(val season: Int)
annotation class RemovedIn(val season: Int)
annotation class ValidThrough(val addedIn: Int, val removedIn: Int)