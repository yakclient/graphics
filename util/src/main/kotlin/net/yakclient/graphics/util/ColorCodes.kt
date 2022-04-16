package net.yakclient.graphics.util

/**
 * A full list of HTML color codes can be found at:
 * https://htmlcolorcodes.com/color-names/
 */


public object ColorCodes {
    private const val COLOR_MAX: Float = 255f

    public fun rgb(red: Int, green: Int, blue: Int): Color = Color(red / COLOR_MAX, green / COLOR_MAX, blue / COLOR_MAX)

    //Red color codes
    public val INDIAN_RED: Color = rgb(205, 92, 92)
    public val LIGHT_CORAL: Color = rgb(240, 128, 128)
    public val SALMON: Color = rgb(250, 128, 114)
    public val DARK_SALMON: Color = rgb(233, 150, 122)
    public val LIGHT_SALMON: Color = rgb(255, 160, 122)
    public val CRIMSON: Color = rgb(220, 20, 60)
    public val RED: Color = rgb(255, 0, 0)
    public val FIRE_BRICK: Color = rgb(178, 34, 34)
    public val DARK_RED: Color = rgb(139, 0, 0)

    //Pink color codes
    public val PINK: Color = rgb(255, 192, 203)
    public val LIGHT_PINK: Color = rgb(255, 182, 193)
    public val HOT_PINK: Color = rgb(255, 105, 180)
    public val DEEP_PINK: Color = rgb(255, 20, 147)
    public val MEDIUM_VIOLET_RED: Color = rgb(199, 21, 133)
    public val PALE_VIOLET_RED: Color = rgb(219, 112, 147)

    //Orange color codes
    public val CORAL: Color = rgb(255, 127, 80)
    public val TOMATO: Color = rgb(255, 99, 71)
    public val ORANGE_RED: Color = rgb(255, 69, 0)
    public val DARK_ORANGE: Color = rgb(255, 140, 0)
    public val ORANGE: Color = rgb(255, 165, 0)

    //Yellow color codes
    public val GOLD: Color = rgb(255, 215, 0)
    public val YELLOW: Color = rgb(255, 255, 0)
    public val LIGHT_YELLOW: Color = rgb(255, 255, 224)
    public val LEMON_CHIFFON: Color = rgb(255, 250, 205)
    public val LIGHT_GOLDENROD_YELLOW: Color = rgb(250, 250, 210)
    public val PAPAYA_WHIP: Color = rgb(255, 239, 213)
    public val MOCCASIN: Color = rgb(255, 228, 181)
    public val PEACH_PUFF: Color = rgb(255, 218, 185)
    public val PALE_GOLDENROD: Color = rgb(238, 232, 170)
    public val KHAKI: Color = rgb(240, 230, 140)
    public val DARK_KHAKI: Color = rgb(189, 183, 107)

    //Purple color codes
    public val LAVENDER: Color = rgb(230, 230, 250)
    public val THISTLE: Color = rgb(216, 191, 216)
    public val PLUM: Color = rgb(221, 160, 221)
    public val VIOLET: Color = rgb(238, 130, 238)
    public val ORCHID: Color = rgb(218, 112, 214)
    public val FUCHSIA: Color = rgb(255, 0, 255)
    public val MAGENTA: Color = rgb(255, 0, 255)
    public val MEDIUM_ORCHID: Color = rgb(186, 85, 211)
    public val MEDIUM_PURPLE: Color = rgb(147, 112, 219)
    public val REBECCA_PURPLE: Color = rgb(102, 51, 153)
    public val BLUE_VIOLET: Color = rgb(138, 43, 226)
    public val DARK_VIOLET: Color = rgb(148, 0, 211)
    public val DARK_ORCHID: Color = rgb(153, 50, 204)
    public val DARK_MAGENTA: Color = rgb(139, 0, 139)
    public val PURPLE: Color = rgb(128, 0, 128)
    public val INDIGO: Color = rgb(75, 0, 130)

    //Green color codes
    public val GREEN_YELLOW: Color = rgb(173, 255, 47)
    public val CHARTREUSE: Color = rgb(127, 255, 0)
    public val LAWN_GREEN: Color = rgb(124, 252, 0)
    public val LIME: Color = rgb(0, 255, 0)
    public val LIME_GREEN: Color = rgb(50, 205, 50)
    public val PALE_GREEN: Color = rgb(152, 251, 152)
    public val LIGHT_GREEN: Color = rgb(144, 238, 144)
    public val MEDIUM_SPRING_GREEN: Color = rgb(0, 250, 154)
    public val SPRING_GREEN: Color = rgb(0, 255, 127)
    public val MEDIUM_SEA_GREEN: Color = rgb(60, 179, 113)
    public val SEA_GREEN: Color = rgb(46, 139, 87)
    public val FOREST_GREEN: Color = rgb(34, 139, 34)
    public val GREEN: Color = rgb(0, 128, 0)
    public val DARK_GREEN: Color = rgb(0, 100, 0)
    public val YELLOW_GREEN: Color = rgb(154, 205, 50)
    public val OLIVE_DRAB: Color = rgb(107, 142, 35)
    public val OLIVE: Color = rgb(128, 128, 0)
    public val DARK_OLIVE_GREEN: Color = rgb(85, 107, 47)
    public val MEDIUM_AQUAMARINE: Color = rgb(102, 205, 170)
    public val DARK_SEA_GREEN: Color = rgb(143, 188, 139)
    public val LIGHT_SEA_GREEN: Color = rgb(32, 178, 170)
    public val DARK_CYAN: Color = rgb(0, 139, 139)
    public val TEAL: Color = rgb(0, 128, 128)

    //Blue color codes
    public val AQUA: Color = rgb(0, 255, 255)
    public val CYAN: Color = rgb(0, 255, 255)
    public val LIGHT_CYAN: Color = rgb(224, 255, 255)
    public val PALE_TURQUOISE: Color = rgb(175, 238, 238)
    public val AQUA_MARINE: Color = rgb(127, 255, 212)
    public val TURQUOISE: Color = rgb(64, 224, 208)
    public val MEDIUM_TURQUOISE: Color = rgb(72, 209, 204)
    public val DARK_TURQUOISE: Color = rgb(0, 206, 209)
    public val CADET_BLUE: Color = rgb(95, 158, 160)
    public val STEEL_BLUE: Color = rgb(70, 130, 180)
    public val LIGHT_STEEL_BLUE: Color = rgb(176, 196, 222)
    public val POWDER_BLUE: Color = rgb(176, 224, 230)
    public val LIGHT_BLUE: Color = rgb(173, 216, 230)
    public val SKY_BLUE: Color = rgb(135, 206, 235)
    public val LIGHT_SKY_BLUE: Color = rgb(135, 206, 250)
    public val DEEP_SKY_BLUE: Color = rgb(0, 191, 255)
    public val DODGER_BLUE: Color = rgb(30, 144, 255)
    public val CORN_FLOWER_BLUE: Color = rgb(100, 149, 237)
    public val MEDIUM_SLATE_BLUE: Color = rgb(123, 104, 238)
    public val ROYAL_BLUE: Color = rgb(65, 105, 225)
    public val BLUE: Color = rgb(0, 0, 255)
    public val MEDIUM_BLUE: Color = rgb(0, 0, 205)
    public val DARK_BLUE: Color = rgb(0, 0, 139)
    public val NAVY: Color = rgb(0, 0, 128)
    public val MIDNIGHT_BLUE: Color = rgb(25, 25, 112)

    //Brown color codes
    public val CORNSILK: Color = rgb(255, 248, 220)
    public val BLANCHED_ALMOND: Color = rgb(255, 235, 205)
    public val BISQUE: Color = rgb(255, 228, 196)
    public val NAVAJO_WHITE: Color = rgb(255, 222, 173)
    public val WHEAT: Color = rgb(245, 222, 179)
    public val BURLY_WOOD: Color = rgb(222, 184, 135)
    public val TAN: Color = rgb(210, 180, 140)
    public val ROSY_BROWN: Color = rgb(188, 143, 143)
    public val SANDY_BROWN: Color = rgb(244, 164, 96)
    public val GOLDENROD: Color = rgb(218, 165, 32)
    public val DARK_GOLDENROD: Color = rgb(184, 134, 11)
    public val PERU: Color = rgb(205, 133, 63)
    public val CHOCOLATE: Color = rgb(210, 105, 30)
    public val SADDLE_COLOR: Color = rgb(139, 69, 19)
    public val SIENNA: Color = rgb(160, 82, 45)
    public val BROWN: Color = rgb(165, 42, 42)
    public val MAROON: Color = rgb(128, 0, 0)

    //White color codes
    public val WHITE: Color = rgb(255, 255, 255)
    public val SNOW: Color = rgb(255, 250, 250)
    public val HONEY_DEW: Color = rgb(240, 255, 240)
    public val MINT_CREAM: Color = rgb(245, 255, 250)
    public val AZURE: Color = rgb(240, 255, 255)
    public val ALICE_BLUE: Color = rgb(240, 248, 255)
    public val GHOST_WHITE: Color = rgb(248, 248, 255)
    public val WHITE_SMOKE: Color = rgb(245, 245, 245)
    public val SEA_SHELL: Color = rgb(255, 245, 238)
    public val BEIGE: Color = rgb(245, 245, 220)
    public val OLD_LACE: Color = rgb(253, 245, 230)
    public val FLORAL_WHITE: Color = rgb(255, 250, 240)
    public val IVORY: Color = rgb(255, 255, 240)
    public val ANTIQUE_WHITE: Color = rgb(250, 235, 215)
    public val LINEN: Color = rgb(250, 240, 230)
    public val LAVENDER_BLUSH: Color = rgb(255, 240, 245)
    public val MISTY_ROSE: Color = rgb(255, 228, 225)

    //Gray color codes
    public val GAINSBORO: Color = rgb(220, 220, 220)
    public val LIGHT_GRAY: Color = rgb(211, 211, 211)
    public val SILVER: Color = rgb(192, 192, 192)
    public val DARK_GRAY: Color = rgb(169, 169, 169)
    public val GRAY: Color = rgb(128, 128, 128)
    public val DIM_GRAY: Color = rgb(105, 105, 105)
    public val LIGHT_SLATE_GRAY: Color = rgb(119, 136, 153)
    public val SLATE_GRAY: Color = rgb(112, 128, 144)
    public val DARK_SLATE_GRAY: Color = rgb(47, 79, 79)
    public val BLACK: Color = rgb(0, 0, 0)
}