package net.yakclient.graphics.util;

/**
 * A full list of HTML color codes can be found at:
 * https://htmlcolorcodes.com/color-names/
 */
public interface ColorCodes {
    float COLOR_MAX = 255f;

    static Color rgb(int red, int green, int blue) {
        return new Color(red / COLOR_MAX, green / COLOR_MAX, blue / COLOR_MAX);
    }

    //Red color codes
    Color INDIAN_RED = rgb(205, 92, 92);
    Color LIGHT_CORAL = rgb(240, 128, 128);
    Color SALMON = rgb(250, 128, 114);
    Color DARK_SALMON = rgb(233, 150, 122);
    Color LIGHT_SALMON = rgb(255, 160, 122);
    Color CRIMSON = rgb(220, 20, 60);
    Color RED = rgb(255, 0, 0);
    Color FIRE_BRICK = rgb(178, 34, 34);
    Color DARK_RED = rgb(139, 0, 0);

    //Pink color codes
    Color PINK = rgb(255, 192, 203);
    Color LIGHT_PINK = rgb(255, 182, 193);
    Color HOT_PINK = rgb(255, 105, 180);
    Color DEEP_PINK = rgb(255, 20, 147);
    Color MEDIUM_VIOLET_RED = rgb(199, 21, 133);
    Color PALE_VIOLET_RED = rgb(219, 112, 147);

    //Orange color codes
    Color CORAL = rgb(255, 127, 80);
    Color TOMATO = rgb(255, 99, 71);
    Color ORANGE_RED = rgb(255, 69, 0);
    Color DARK_ORANGE = rgb(255, 140, 0);
    Color ORANGE = rgb(255, 165, 0);

    //Yellow color codes
    Color GOLD = rgb(255, 215, 0);
    Color YELLOW = rgb(255, 255, 0);
    Color LIGHT_YELLOW = rgb(255, 255, 224);
    Color LEMON_CHIFFON = rgb(255, 250, 205);
    Color LIGHT_GOLDENROD_YELLOW = rgb(250, 250, 210);
    Color PAPAYA_WHIP = rgb(255, 239, 213);
    Color MOCCASIN = rgb(255, 228, 181);
    Color PEACH_PUFF = rgb(255, 218, 185);
    Color PALE_GOLDENROD = rgb(238, 232, 170);
    Color KHAKI = rgb(240, 230, 140);
    Color DARK_KHAKI = rgb(189, 183, 107);

    //Purple color codes
    Color LAVENDER = rgb(230, 230, 250);
    Color THISTLE = rgb(216, 191, 216);
    Color PLUM = rgb(221, 160, 221);
    Color VIOLET = rgb(238, 130, 238);
    Color ORCHID = rgb(218, 112, 214);
    Color FUCHSIA = rgb(255, 0, 255);
    Color MAGENTA = rgb(255, 0, 255);
    Color MEDIUM_ORCHID = rgb(186, 85, 211);
    Color MEDIUM_PURPLE = rgb(147, 112, 219);
    Color REBECCA_PURPLE = rgb(102, 51, 153);
    Color BLUE_VIOLET = rgb(138, 43, 226);
    Color DARK_VIOLET = rgb(148, 0, 211);
    Color DARK_ORCHID = rgb(153, 50, 204);
    Color DARK_MAGENTA = rgb(139, 0, 139);
    Color PURPLE = rgb(128, 0, 128);
    Color INDIGO = rgb(75, 0, 130);

    //Green color codes
    Color GREEN_YELLOW = rgb(173, 255, 47);
    Color CHARTREUSE = rgb(127, 255, 0);
    Color LAWN_GREEN = rgb(124, 252, 0);
    Color LIME = rgb(0, 255, 0);
    Color LIME_GREEN = rgb(50, 205, 50);
    Color PALE_GREEN = rgb(152, 251, 152);
    Color LIGHT_GREEN = rgb(144, 238, 144);
    Color MEDIUM_SPRING_GREEN = rgb(0, 250, 154);
    Color SPRING_GREEN = rgb(0, 255, 127);
    Color MEDIUM_SEA_GREEN = rgb(60, 179, 113);
    Color SEA_GREEN = rgb(46, 139, 87);
    Color FOREST_GREEN = rgb(34, 139, 34);
    Color GREEN = rgb(0, 128, 0);
    Color DARK_GREEN = rgb(0, 100, 0);
    Color YELLOW_GREEN = rgb(154, 205, 50);
    Color OLIVE_DRAB = rgb(107, 142, 35);
    Color OLIVE = rgb(128, 128, 0);
    Color DARK_OLIVE_GREEN = rgb(85, 107, 47);
    Color MEDIUM_AQUAMARINE = rgb(102, 205, 170);
    Color DARK_SEA_GREEN = rgb(143, 188, 139);
    Color LIGHT_SEA_GREEN = rgb(32, 178, 170);
    Color DARK_CYAN = rgb(0, 139, 139);
    Color TEAL = rgb(0, 128, 128);

    //Blue color codes
    Color AQUA = rgb(0, 255, 255);
    Color CYAN = rgb(0, 255, 255);
    Color LIGHT_CYAN = rgb(224, 255, 255);
    Color PALE_TURQUOISE = rgb(175, 238, 238);
    Color AQUA_MARINE = rgb(127, 255, 212);
    Color TURQUOISE = rgb(64, 224, 208);
    Color MEDIUM_TURQUOISE = rgb(72, 209, 204);
    Color DARK_TURQUOISE = rgb(0, 206, 209);
    Color CADET_BLUE = rgb(95, 158, 160);
    Color STEEL_BLUE = rgb(70, 130, 180);
    Color LIGHT_STEEL_BLUE = rgb(176, 196, 222);
    Color POWDER_BLUE = rgb(176, 224, 230);
    Color LIGHT_BLUE = rgb(173, 216, 230);
    Color SKY_BLUE = rgb(135, 206, 235);
    Color LIGHT_SKY_BLUE = rgb(135, 206, 250);
    Color DEEP_SKY_BLUE = rgb(0, 191, 255);
    Color DODGER_BLUE = rgb(30, 144, 255);
    Color CORN_FLOWER_BLUE = rgb(100, 149, 237);
    Color MEDIUM_SLATE_BLUE = rgb(123, 104, 238);
    Color ROYAL_BLUE = rgb(65, 105, 225);
    Color BLUE = rgb(0, 0, 255);
    Color MEDIUM_BLUE = rgb(0, 0, 205);
    Color DARK_BLUE = rgb(0, 0, 139);
    Color NAVY = rgb(0, 0, 128);
    Color MIDNIGHT_BLUE = rgb(25, 25, 112);

    //Brown color codes
    Color CORNSILK = rgb(255, 248, 220);
    Color BLANCHED_ALMOND = rgb(255, 235, 205);
    Color BISQUE = rgb(255, 228, 196);
    Color NAVAJO_WHITE = rgb(255, 222, 173);
    Color WHEAT = rgb(245, 222, 179);
    Color BURLY_WOOD = rgb(222, 184, 135);
    Color TAN = rgb(210, 180, 140);
    Color ROSY_BROWN = rgb(188, 143, 143);
    Color SANDY_BROWN = rgb(244, 164, 96);
    Color GOLDENROD = rgb(218, 165, 32);
    Color DARK_GOLDENROD = rgb(184, 134, 11);
    Color PERU = rgb(205, 133, 63);
    Color CHOCOLATE = rgb(210, 105, 30);
    Color SADDLE_COLOR = rgb(139, 69, 19);
    Color SIENNA = rgb(160, 82, 45);
    Color BROWN = rgb(165, 42, 42);
    Color MAROON = rgb(128, 0, 0);

    //White color codes
    Color WHITE = rgb(255, 255, 255);
    Color SNOW = rgb(255, 250, 250);
    Color HONEY_DEW = rgb(240, 255, 240);
    Color MINT_CREAM = rgb(245, 255, 250);
    Color AZURE = rgb(240, 255, 255);
    Color ALICE_BLUE = rgb(240, 248, 255);
    Color GHOST_WHITE = rgb(248, 248, 255);
    Color WHITE_SMOKE = rgb(245, 245, 245);
    Color SEA_SHELL = rgb(255, 245, 238);
    Color BEIGE = rgb(245, 245, 220);
    Color OLD_LACE = rgb(253, 245, 230);
    Color FLORAL_WHITE = rgb(255, 250, 240);
    Color IVORY = rgb(255, 255, 240);
    Color ANTIQUE_WHITE = rgb(250, 235, 215);
    Color LINEN = rgb(250, 240, 230);
    Color LAVENDER_BLUSH = rgb(255, 240, 245);
    Color MISTY_ROSE = rgb(255, 228, 225);

    //Gray color codes
    Color GAINSBORO = rgb(220, 220, 220);
    Color LIGHT_GRAY = rgb(211, 211, 211);
    Color SILVER = rgb(192, 192, 192);
    Color DARK_GRAY = rgb(169, 169, 169);
    Color GRAY = rgb(128, 128, 128);
    Color DIM_GRAY = rgb(105, 105, 105);
    Color LIGHT_SLATE_GRAY = rgb(119, 136, 153);
    Color SLATE_GRAY = rgb(112, 128, 144);
    Color DARK_SLATE_GRAY = rgb(47, 79, 79);
    Color BLACK = rgb(0, 0, 0);
}
