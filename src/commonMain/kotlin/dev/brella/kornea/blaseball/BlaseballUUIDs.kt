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
inline class PlayerID(override val uuid: String): BlaseballUUID
@Serializable
inline class TeamID(override val uuid: String): BlaseballUUID
@Serializable
inline class LeagueID(override val uuid: String): BlaseballUUID
@Serializable
inline class DivisionID(override val uuid: String): BlaseballUUID
@Serializable
inline class MatchupID(override val uuid: String): BlaseballUUID
@Serializable
inline class SeasonID(override val uuid: String): BlaseballUUID
@Serializable
inline class RulesID(override val uuid: String): BlaseballUUID
@Serializable
inline class ScheduleID(override val uuid: String): BlaseballUUID
@Serializable
inline class StandingsID(override val uuid: String): BlaseballUUID
@Serializable
inline class TerminologyID(override val uuid: String): BlaseballUUID
@Serializable
inline class TiebreakerID(override val uuid: String): BlaseballUUID


@Serializable
inline class GameID(override val uuid: String): BlaseballUUID

@Serializable
inline class StadiumID(override val uuid: String): BlaseballUUID

@Serializable
inline class DecreeID(override val uuid: String): BlaseballUUID

@Serializable
inline class GameStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class PlayerStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class SeasonStatsheetID(override val uuid: String): BlaseballUUID
@Serializable
inline class TeamStatsheetID(override val uuid: String): BlaseballUUID
