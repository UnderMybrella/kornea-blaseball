package dev.brella.kornea.blaseball

object BlaseballFeedEventType {
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
    const val TRIPLE_THREAD = 36
    const val FREE_REFILL = 37
    const val WIRED = 39
    const val FEEDBACK_BLOCKED = 40
    const val FEEDBACK = 41
    const val ALLERGIC_REACTION = 47
    const val REVERBERATING = 48
    const val REVERB_SHUFFLE = 49
    const val SIPHON = 52
    const val INCINERATION = 54
    const val FIRE_EATING = 55
    const val FLAG_PLANTED = 56
    const val DECREE_PASSED = 59
    const val BLESSING_WON = 60
    const val WILL_RECEIVED = 61
    const val FLOOD = 62
    const val PEANUTS_FLAVOUR_TEXT = 73

    /** Shelling */
    const val TASTING_THE_INFINITE = 74
    const val TAROT_READING = 81
    const val EMERGENCY_ALERT = 82
    const val RETURN_FROM_ELSEWHERE = 84
    const val OVER_UNDER = 85
    const val UNDER_OVER = 86
    const val SUPERYUMMY_TEXT = 92
    const val PERK = 93

    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
    const val ADDED_INGAME_MODIFIER = 106

    /** Triple Threat, Free REfill, Magmatic, Inhibiting, etc */
    const val REMOVED_INGAME_MODIFIER = 107

    const val MODIFIER_EXPIRES = 108

    /** Including Postseason Births */
    const val PLAYER_RECRUITED = 109

    const val PLAYER_TRADE = 113
    const val PLAYER_CHANGING_POSITION = 114
    const val PLAYER_JOINING_TEAM = 115
    const val NEW_PLAYER_AFTER_INCINERATION = 116
    const val PLAYER_STAT_INCREASE = 117
    const val PLAYER_STAT_DECREASE = 118
    const val PLAYER_REROLL = 119
    const val PLAYER_ENTERS_THE_HALL_OF_FAME = 125
    const val REVERB_SHUFFLE_2 = 132
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
    const val RETURNED_VALUE_PERMITTED_TO_STAY = 150
    const val DECREE_NARRATION = 151
    const val WILL_RESULTS = 152
    const val TEAM_SHAMED = 154
    const val TEAM_SHAMES = 155
    const val SUN_2_GRANTS_WIN = 156
    const val BLACK_HOLE_SWALLOWS = 157
    const val ELIMINATED_FROM_POSTSEASON = 158
    const val POSTSEASON_ADVANCE = 159

    fun textFromType(type: Int): String =
        when (type) {
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

            23 -> "Player skipped due to being Shelled"
            24 -> "Partying"
            25 -> "Strike zapped by Electric blood"

            27 -> "Mild pitch"
            28 -> "End of inning"
            29 -> "Site takeover text"
            30 -> "Black hole (in-game)"
            31 -> "Sun 2 (in-game)"

            33 -> "Birds flavor text"
            34 -> "Murder of crows"

            36 -> "Triple Threat"
            37 -> "Free Refill"

            39 -> "Wired"
            40 -> "Feedback blocked"
            41 -> "Feedback"

            47 -> "Allergic reaction"
            48 -> "Reverberating"
            49 -> "Reverb shuffle"

            52 -> "Siphon"

            54 -> "Incineration"
            55 -> "Fire eating"
            56 -> "Flag planted"

            59 -> "Decree passed"
            60 -> "Blessing won"
            61 -> "Will received"
            62 -> "Flood"

            73 -> "Peanuts flavor text"
            74 -> "Tasting the infinite (Shelling)"

            81 -> "Tarot reading"
            82 -> "Emergency Alert"

            84 -> "Return from Elsewhere"
            85 -> "Over Under"
            86 -> "Under Over"

            92 -> "Superyummy text"
            93 -> "Perk"

            106 -> "Added in-game modifier (Triple Threat, Free Refill, Magmatic, Inhabiting, etc)"
            107 -> "Removed in-game modifier (same as above, but when they disappear)"
            108 -> "Modifier expires"
            109 -> "Player recruited (including Postseason Births)"

            113 -> "Player trade"
            114 -> "Player changing position"
            115 -> "Player joining team"
            116 -> "New player after incineration"
            117 -> "Player stat increase"
            118 -> "Player stat decrease"
            119 -> "Player reroll"

            125 -> "Player enters the Hall of Flame"

            132 -> "Reverb shuffle"

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

            150 -> "Returned player is permitted to stay"
            151 -> "Decree narration"
            152 -> "Will results"

            154 -> "Team shamed"
            155 -> "Team shames"
            156 -> "Sun 2 grants win"
            157 -> "Black Hole swallows"
            158 -> "Eliminated from postseason"
            159 -> "Postseason advance"

            else -> "UNK_$type"
        }
}