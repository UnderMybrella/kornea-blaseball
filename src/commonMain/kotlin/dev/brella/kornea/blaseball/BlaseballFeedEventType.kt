package dev.brella.kornea.blaseball

object BlaseballFeedEventType: Iterable<Int>, ClosedRange<Int> {
    const val LETS_GO = 0
    const val PLAY_BALL = 1
    const val HALF_INNING = 2
    const val PITCHER_CHANGE = 3
    const val STOLEN_BASE = 4
    const val WALK = 5
    const val STRIKEOUT = 6
    const val FLYOUT = 7
    const val GROUND_OUT = 8
    const val HOME_RUN = 9

    /** Single/Double/Triple */
    const val HIT = 10
    const val GAME_END_LOG = 11
    const val PLATE_APPEARANCE = 12

    /** Not including Foul Balls */
    const val STRIKE = 13
    const val BALL = 14
    const val FOUL_BALL = 15

    const val SOLAR_PANEL_OVERFLOW_RUNS = 20

    const val PLAYER_HITS_ANOTHER_WITH_PITCH = 22
    const val PLAYER_SKIPPED_DUE_TO_SHELL = 23
    const val PARTYING = 24
    const val STRIKE_ZAPPED_BY_ELECTRIC_BLOOD = 25
    const val MILD_PITCH = 27
    const val END_OF_INNING = 28
    const val SITE_TAKEOVER_TEXT = 29

    const val BLACK_HOLE = 30
    const val SUN_2 = 31
    const val BIRDS_FLAVOUR_TEXT = 33
    const val MURDER_OF_CROWS = 34
    const val BIRDS_FREE_SHELLED_PLAYER = 35
    const val TRIPLE_THREAD = 36
    const val FREE_REFILL = 37
    const val WIRED = 39
    const val FEEDBACK_BLOCKED = 40
    const val FEEDBACK = 41
    const val ALLERGIC_REACTION = 47
    const val REVERBERATING = 48
    const val REVERB_SHUFFLE = 49

    const val BLOODDRAIN = 51
    const val SIPHON = 52
    const val INCINERATION = 54
    const val FIRE_EATING = 55
    const val FLAG_PLANTED = 56
    const val RENOVATION_BUILT = 57
    const val DECREE_PASSED = 59
    const val BLESSING_WON = 60
    const val WILL_RECEIVED = 61
    const val FLOOD = 62
    const val SALMON_SWIM_UPSTREAM = 63

    const val PLAYER_ENTERS_SECRET_BASE = 65
    const val PLAYER_EXITS_SECRET_BASE = 66
    const val CONSUMERS_ATTACK_PLAYER = 67

    const val ECHO_CHAMBER_TRAPS_WAVE = 69
    const val PLAYER_HOPS_ON_GRIND_RAIL = 70

    const val PEANUT_MISTER = 72
    const val PEANUTS_FLAVOUR_TEXT = 73
    /** Shelling */
    const val TASTING_THE_INFINITE = 74
    const val SOLAR_PANEL_ALIGNMENT = 78
    const val SOLAR_PANEL_RUN_COLLECTION = 79

    const val TAROT_READING = 81
    const val EMERGENCY_ALERT = 82
    const val RETURN_FROM_ELSEWHERE = 84
    const val OVER_UNDER = 85
    const val UNDER_OVER = 86

    const val UNDERSEA = 88

    const val HOMESICK = 91
    const val SUPERYUMMY_TEXT = 92
    const val PERK = 93

    const val EARLBIRDS = 96
    const val LATE_TO_PARTY = 97

    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
    const val ADDED_INGAME_MODIFIER = 106

    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
    const val REMOVED_INGAME_MODIFIER = 107

    const val MODIFIER_EXPIRES = 108

    /** Including Postseason Births */
    const val PLAYER_RECRUITED = 109

    /** Necromancy */
    const val PLAYER_SENT_TO_SHADOWS_AND_REPLACED = 110

    /** Returned */
    const val PLAYER_REMOVED_AND_REPLACED_FROM_SHADOWS = 111
    const val ECHO_PLAYER_STATIC_CHILD = 112
    const val PLAYER_TRADE = 113
    const val PLAYER_CHANGING_POSITION = 114
    const val PLAYER_JOINING_TEAM = 115
    const val NEW_PLAYER_AFTER_INCINERATION = 116
    const val PLAYER_STAT_INCREASE = 117
    const val PLAYER_STAT_DECREASE = 118
    const val PLAYER_REROLL = 119

    const val PLAYER_ENTERS_HALL_OF_FAME = 125
    const val PLAYER_EXITS_HALL_OF_FAME = 126
    const val PLAYER_GAINED_ITEM = 127
    const val PLAYER_DROPPED_ITEM = 128

    const val REVERB_SHUFFLE_FULL = 130
    const val REVERB_SHUFFLE_ROTATION = 132

    const val NEW_TEAM = 135
    /** Excluding Incinerations */
    const val NEW_PLAYER = 136
    const val PLAYER_HATCHED = 137

    const val PLAYER_EVOLVES = 139
    const val TEAM_WINS_INTERNET_SERIES = 141
    const val POSTSEASON_SPOT = 142
    const val FINAL_STANDINGS = 143
    const val WIRED_TO_TIRED = 144
    const val PLAYER_TO_ALTERNATE = 145
    const val ADDED_MODIFIER_DUE_TO_MODIFIER = 146
    const val REMOVED_MODIFIER_DUE_TO_MODIFIER = 147
    const val SUPERYUMMY_TRANSITIONS = 148
    const val NECROMANCY_NARRATION = 149
    const val RETURNED_VALUE_PERMITTED_TO_STAY = 150
    const val DECREE_NARRATION = 151
    const val WILL_RESULTS = 152
    const val BLESSING_RESULTS = 153
    const val TEAM_SHAMED = 154
    const val TEAM_SHAMES = 155
    const val SUN_2_GRANTS_WIN = 156
    const val BLACK_HOLE_SWALLOWS = 157
    const val ELIMINATED_FROM_POSTSEASON = 158
    const val POSTSEASON_ADVANCE = 159

    const val PLAYER_GAINED_BLOOD_TYPE = 161

    const val TEAM_OVERPERFORMING = 165
    const val LINEUP_OPTIMISED = 166

    const val PEANUT_ALLERGY_CURED = 168
    const val PLAYER_ECHOED = 169
    const val ECHO_PLAYER_STATIC = 170
    const val ECHO_FADES = 171
    const val ADDED_MODIFIER_DUE_TO_ECHO = 172
    const val PSYCHOACOUSTICS_ECHO = 173
    const val ECHO_PLAYER_ECHO_PLAYER_ECHO = 174
    const val ALTERNATE_COIN_TEXT = 175
    const val INVESTIGATION_UNDERWAY = 176
    const val PLAYER_OPENS_CRATE = 177
    const val TEAM_MIDDLING = 178
    const val PLAYER_STAT_INCREASED_PERCENT = 179
    const val PLAYER_STAT_DECREASED_PERCENT = 180
    const val PLAYER_ENTERS_CRIME_SCENE_TO_INVESTIGATE = 181

    const val PLAYERS_ITEM_BROKE = 185
    const val PLAYERS_ITEM_DAMAGED = 186
    const val PLAYERS_ITEM_RESTORED = 187
    const val PLAYERS_ITEM_REPAIRED = 188

    private val RANGE = BlaseballFeedEventType.run {
        listOf(
            LETS_GO .. FOUL_BALL,
            SOLAR_PANEL_OVERFLOW_RUNS .. SOLAR_PANEL_OVERFLOW_RUNS,
            PLAYER_HITS_ANOTHER_WITH_PITCH .. STRIKE_ZAPPED_BY_ELECTRIC_BLOOD,
            MILD_PITCH .. SUN_2,
            BIRDS_FLAVOUR_TEXT .. FREE_REFILL,
            WIRED .. FEEDBACK,
            ALLERGIC_REACTION .. REVERB_SHUFFLE,
            BLOODDRAIN .. SIPHON,
            INCINERATION .. RENOVATION_BUILT,
            DECREE_PASSED .. SALMON_SWIM_UPSTREAM,
            PLAYER_ENTERS_SECRET_BASE .. CONSUMERS_ATTACK_PLAYER,
            ECHO_CHAMBER_TRAPS_WAVE .. PLAYER_HOPS_ON_GRIND_RAIL,
            PEANUT_MISTER .. TASTING_THE_INFINITE,
            SOLAR_PANEL_ALIGNMENT .. SOLAR_PANEL_RUN_COLLECTION,
            TAROT_READING .. EMERGENCY_ALERT,
            RETURN_FROM_ELSEWHERE .. UNDER_OVER,
            UNDERSEA .. UNDERSEA,
            HOMESICK .. PERK,
            EARLBIRDS .. LATE_TO_PARTY,
            ADDED_INGAME_MODIFIER .. PLAYER_REROLL,
            PLAYER_ENTERS_HALL_OF_FAME .. PLAYER_DROPPED_ITEM,
            REVERB_SHUFFLE .. REVERB_SHUFFLE_FULL,
            REVERB_SHUFFLE_ROTATION .. REVERB_SHUFFLE_ROTATION,
            NEW_TEAM .. PLAYER_HATCHED,
            PLAYER_EVOLVES .. PLAYER_EVOLVES,
            TEAM_WINS_INTERNET_SERIES .. POSTSEASON_ADVANCE,

            PLAYER_GAINED_BLOOD_TYPE .. PLAYER_GAINED_BLOOD_TYPE,

            TEAM_OVERPERFORMING .. LINEUP_OPTIMISED,
            PEANUT_ALLERGY_CURED .. PLAYER_ENTERS_CRIME_SCENE_TO_INVESTIGATE,

            PLAYERS_ITEM_BROKE .. PLAYERS_ITEM_REPAIRED
        ).flatten().toIntArray()
    }

    override fun iterator() = RANGE.iterator()

    override val start: Int = RANGE.first()
    override val endInclusive: Int = RANGE.last()

    override infix fun contains(value: Int): Boolean = RANGE.contains(value)

    fun textFromType(type: Int): String =
        when (type) {
            0 -> "Let's Go!"
            1 -> "Play ball!"
            2 -> "Half-inning"
            3 -> "Pitcher change"
            4 -> "Stolen base"
            5 -> "Walk"
            6 -> "Strikeout"
            7 -> "Flyout"
            8 -> "Ground out"
            9 -> "Home run"
            10 -> "Hit (single/double/triple)"
            11 -> "Game end log"
            12 -> "Plate appearance"
            13 -> "Strike (not including Foul Balls)"
            14 -> "Ball"
            15 -> "Foul Ball"
            20 -> "Solar Panel overflow runs"
            22 -> "Player hits another with a pitch"
            23 -> "Player skipped due to being Shelled or Elsewhere"
            24 -> "Partying"
            25 -> "Strike zapped by Electric blood"
            27 -> "Mild pitch"
            28 -> "End of inning"
            29 -> "Site takeover text"
            30 -> "Black hole (in-game)"
            31 -> "Sun 2 (in-game)"
            33 -> "Birds flavor text"
            34 -> "Murder of crows"
            35 -> "Birds free shelled player"
            36 -> "Triple Threat"
            37 -> "Free Refill"
            39 -> "Wired"
            40 -> "Feedback blocked"
            41 -> "Feedback"
            47 -> "Allergic reaction"
            48 -> "Reverberating"
            49 -> "Reverb shuffle"
            51 -> "Blooddrain"
            52 -> "Siphon"
            54 -> "Incineration"
            55 -> "Fire eating"
            56 -> "Flag planted"
            57 -> "Renovation built"
            59 -> "Decree passed"
            60 -> "Blessing won"
            61 -> "Will received"
            62 -> "Flood"
            63 -> "Salmon swim upstream"
            65 -> "Player enters secret base"
            66 -> "Player exits secret base"
            67 -> "Consumers attack Player"
            69 -> "Echo Chamber traps a wave"
            70 -> "Player hops on the grind rail"
            72 -> "Peanut Mister"
            73 -> "Peanuts flavor text"
            74 -> "Tasting the infinite (Shelling)"
            78 -> "Solar Panel alignment"
            79 -> "Solar Panel run collection"
            81 -> "Tarot reading"
            82 -> "Emergency Alert"
            84 -> "Return from Elsewhere"
            85 -> "Over Under"
            86 -> "Under Over"
            88 -> "Undersea"
            91 -> "Homesick"
            92 -> "Superyummy text"
            93 -> "Perk"
            96 -> "Earlbirds"
            97 -> "Late to the Party"
            106 -> "Added in-game modifier (Triple Threat, Free Refill, Magmatic, Inhabiting, etc)"
            107 -> "Removed in-game modifier (same as above, but when they disappear)"
            108 -> "Modifier expires"
            109 -> "Player recruited (including Postseason Births)"
            110 -> "Player sent to shadows and replaced (necromancy)"
            111 -> "Player removed and replaced from shadows (Returned)"
            112 -> "ECHO player STATIC (child event)"
            113 -> "Player trade"
            114 -> "Player changing position"
            115 -> "Player joining team"
            116 -> "New player after incineration"
            117 -> "Player stat increase"
            118 -> "Player stat decrease"
            119 -> "Player reroll"
            125 -> "Player enters the Hall of Flame"
            126 -> "Player exits the Hall of Flame"
            127 -> "Player gained item"
            128 -> "Player dropped item"
            130 -> "Reverb shuffle (full)"
            132 -> "Reverb shuffle (rotation)"
            135 -> "New team"
            136 -> "New player (excluding incinerations)"
            137 -> "Player hatched"
            139 -> "Player Evolves"
            141 -> "Team wins Internet Series"
            142 -> "Postseason spot"
            143 -> "Final standings"
            144 -> "Wired -> Tired transitions"
            145 -> "Player becomes an Alternate"
            146 -> "Added modifier due to another modifier(?) (under/over, Perk)"
            147 -> "Removed modifier added due to another modifier(?) (under/over, Perk)"
            148 -> "Superyummy transitions (Overperforming -> Underperforming, vice versa, may be general modifier-induced mod swaps)"
            149 -> "Necromancy narration"
            150 -> "Returned player is permitted to stay"
            151 -> "Decree narration"
            152 -> "Will results"
            153 -> "Blessing results"
            154 -> "Team shamed"
            155 -> "Team shames"
            156 -> "Sun 2 grants win"
            157 -> "Black Hole swallows"
            158 -> "Eliminated from postseason"
            159 -> "Postseason advance"
            161 -> "Player gained blood type"
            165 -> "Team is/not Overperforming"
            166 -> "Lineup optimized"
            168 -> "Peanut Allergy cured"
            169 -> "Player echoed"
            170 -> "ECHO player STATIC"
            171 -> "Echo Fades"
            172 -> "Added modifier due to Player echo"
            173 -> "Psychoacoustics Echo"
            174 -> "ECHO player ECHO player ECHO"
            175 -> "Alternate Coin text (hidden from Book, includes player)"
            176 -> "The Investigation is Underway"
            177 -> "Player opens crate (Gains item, ditches another)"
            178 -> "Team is/not Middling"
            179 -> "Player stat increased by %"
            180 -> "Player stat decreased by %"
            181 -> "Player enters the Crime Scene to Investigate"
            185 -> "Player's item broke"
            186 -> "Player's item was damaged"
            187 -> "Player's item was restored (Salmon swam upstream)"
            188 -> "Player's item was repaired (Salmon swam upstream)"

            else -> "UNK_$type"
        }
}