package net.yakclient.opengl.util;

/**
 * A full list of HTML color codes can be found at:
 * https://htmlcolorcodes.com/color-names/
 */
public interface ColorCodes {
    static RGBColor rgb(int red, int green, int blue) {
        return new RGBColor(red, green, blue);
    }

    //Red color codes
    RGBColor INDIAN_RED = rgb(205, 92, 92);
    RGBColor LIGHT_CORAL = rgb(240, 128, 128);
    RGBColor SALMON = rgb(250, 128, 114);
    RGBColor DARK_SALMON = rgb(233, 150, 122);
    RGBColor LIGHT_SALMON = rgb(255, 160, 122);
    RGBColor CRIMSON = rgb(220, 20, 60);
    RGBColor RED = rgb(255, 0, 0);
    RGBColor FIRE_BRICK = rgb(178, 34, 34);
    RGBColor DARK_RED = rgb(139, 0, 0);

    //Pink color codes
    RGBColor PINK = rgb(255, 192, 203);
    RGBColor LIGHT_PINK = rgb(255, 182, 193);
    RGBColor HOT_PINK = rgb(255, 105, 180);
    RGBColor DEEP_PINK = rgb(255, 20, 147);
    RGBColor MEDIUM_VIOLET_RED = rgb(199, 21, 133);
    RGBColor PALE_VIOLET_RED = rgb(219, 112, 147);

    //Orange color codes
    RGBColor CORAL = rgb(255, 127, 80);
    RGBColor TOMATO = rgb(255, 99, 71);
    RGBColor ORANGE_RED = rgb(255, 69, 0);
    RGBColor DARK_ORANGE = rgb(255, 140, 0);
    RGBColor ORANGE = rgb(255, 165, 0);

    //Yellow color codes
    RGBColor GOLD = rgb(255, 215, 0);
    RGBColor YELLOW = rgb(255, 255, 0);
    RGBColor LIGHT_YELLOW = rgb(255, 255, 224);
    RGBColor LEMON_CHIFFON = rgb(255, 250, 205);
    RGBColor LIGHT_GOLDENROD_YELLOW = rgb(250, 250, 210);
    RGBColor PAPAYA_WHIP = rgb(255, 239, 213);
    RGBColor MOCCASIN = rgb(255, 228, 181);
    RGBColor PEACH_PUFF = rgb(255, 218, 185);
    RGBColor PALE_GOLDENROD = rgb(238, 232, 170);
    RGBColor KHAKI = rgb(240, 230, 140);
    RGBColor DARK_KHAKI = rgb(189, 183, 107);

    //Purple color codes
    RGBColor LAVENDER = rgb(230, 230, 250);
    RGBColor THISTLE = rgb(216, 191, 216);
    RGBColor PLUM = rgb(221, 160, 221);
    RGBColor VIOLET = rgb(238, 130, 238);
    RGBColor ORCHID = rgb(218, 112, 214);
    RGBColor FUCHSIA = rgb(255, 0, 255);
    RGBColor MAGENTA = rgb(255, 0, 255);
    RGBColor MEDIUM_ORCHID = rgb(186, 85, 211);
    RGBColor MEDIUM_PURPLE = rgb(147, 112, 219);
    RGBColor REBECCA_PURPLE = rgb(102, 51, 153);
    RGBColor BLUE_VIOLET = rgb(138, 43, 226);
    RGBColor DARK_VIOLET = rgb(148, 0, 211);
    RGBColor DARK_ORCHID = rgb(153, 50, 204);
    RGBColor DARK_MAGENTA = rgb(139, 0, 139);
    RGBColor PURPLE = rgb(128, 0, 128);
    RGBColor INDIGO = rgb(75, 0, 130);

    //Green color codes
    RGBColor GREEN_YELLOW = rgb(173, 255, 47);
    RGBColor CHARTREUSE = rgb(127, 255, 0);
    RGBColor LAWN_GREEN = rgb(124, 252, 0);
    RGBColor LIME = rgb(0, 255, 0);
    RGBColor LIME_GREEN = rgb(50, 205, 50);
    RGBColor PALE_GREEN = rgb(152, 251, 152);
    RGBColor LIGHT_GREEN = rgb(144, 238, 144);
    RGBColor MEDIUM_SPRING_GREEN = rgb(0, 250, 154);
    RGBColor SPRING_GREEN = rgb(0, 255, 127);
    RGBColor MEDIUM_SEA_GREEN = rgb(60, 179, 113);
    RGBColor SEA_GREEN = rgb(46, 139, 87);
    RGBColor FOREST_GREEN = rgb(34, 139, 34);
    RGBColor GREEN = rgb(0, 128, 0);
    RGBColor DARK_GREEN = rgb(0, 100, 0);
    RGBColor YELLOW_GREEN = rgb(154, 205, 50);
    RGBColor OLIVE_DRAB = rgb(107, 142, 35);
    RGBColor OLIVE = rgb(128, 128, 0);
    RGBColor DARK_OLIVE_GREEN = rgb(85, 107, 47);
    RGBColor MEDIUM_AQUAMARINE = rgb(102, 205, 170);
    RGBColor DARK_SEA_GREEN = rgb(143, 188, 139);
    RGBColor LIGHT_SEA_GREEN = rgb(32, 178, 170);
    RGBColor DARK_CYAN = rgb(0, 139, 139);
    RGBColor TEAL = rgb(0, 128, 128);

    //Blue color codes
    RGBColor AQUA = rgb(0, 255, 255);
    RGBColor CYAN = rgb(0, 255, 255);
    RGBColor LIGHT_CYAN = rgb(224, 255, 255);
    RGBColor PALE_TURQUOISE = rgb(175, 238, 238);
    RGBColor AQUA_MARINE = rgb(127, 255, 212);
    RGBColor TURQUOISE = rgb(64, 224, 208);
    RGBColor MEDIUM_TURQUOISE = rgb(72, 209, 204);
    RGBColor DARK_TURQUOISE = rgb(0, 206, 209);
    RGBColor CADET_BLUE = rgb(95, 158, 160);
    RGBColor STEEL_BLUE = rgb(70, 130, 180);
    RGBColor LIGHT_STEEL_BLUE = rgb(176, 196, 222);
    RGBColor POWDER_BLUE = rgb(176, 224, 230);
    RGBColor LIGHT_BLUE = rgb(173, 216, 230);
    RGBColor SKY_BLUE = rgb(135, 206, 235);
    RGBColor LIGHT_SKY_BLUE = rgb(135, 206, 250);
    RGBColor DEEP_SKY_BLUE = rgb(0, 191, 255);
    RGBColor DODGER_BLUE = rgb(30, 144, 255);
    RGBColor CORN_FLOWER_BLUE = rgb(100, 149, 237);
    RGBColor MEDIUM_SLATE_BLUE = rgb(123, 104, 238);
    RGBColor ROYAL_BLUE = rgb(65, 105, 225);
    RGBColor BLUE = rgb(0, 0, 255);
    RGBColor MEDIUM_BLUE = rgb(0, 0, 205);
    RGBColor DARK_BLUE = rgb(0, 0, 139);
    RGBColor NAVY = rgb(0, 0, 128);
    RGBColor MIDNIGHT_BLUE = rgb(25, 25, 112);

    //Brown color codes
    RGBColor CORNSILK = rgb(255, 248, 220);
    RGBColor BLANCHED_ALMOND = rgb(255, 235, 205);
    RGBColor BISQUE = rgb(255, 228, 196);
    RGBColor NAVAJO_WHITE = rgb(255, 222, 173);
    RGBColor WHEAT = rgb(245, 222, 179);
    RGBColor BURLY_WOOD = rgb(222, 184, 135);
    RGBColor TAN = rgb(210, 180, 140);
    RGBColor ROSY_BROWN = rgb(188, 143, 143);
    RGBColor SANDY_BROWN = rgb(244, 164, 96);
    RGBColor GOLDENROD = rgb(218, 165, 32);
    RGBColor DARK_GOLDENROD = rgb(184, 134, 11);
    RGBColor PERU = rgb(205, 133, 63);
    RGBColor CHOCOLATE = rgb(210, 105, 30);
    RGBColor SADDLE_COLOR = rgb(139, 69, 19);
    RGBColor SIENNA = rgb(160, 82, 45);
    RGBColor BROWN = rgb(165, 42, 42);
    RGBColor MAROON = rgb(128, 0, 0);

    //White color codes
    RGBColor WHITE = rgb(255, 255, 255);
    RGBColor SNOW = rgb(255, 250, 250);
    RGBColor HONEY_DEW = rgb(240, 255, 240);
    RGBColor MINT_CREAM = rgb(245, 255, 250);
    RGBColor AZURE = rgb(240, 255, 255);
    RGBColor ALICE_BLUE = rgb(240, 248, 255);
    RGBColor GHOST_WHITE = rgb(248, 248, 255);
    RGBColor WHITE_SMOKE = rgb(245, 245, 245);
    RGBColor SEA_SHELL = rgb(255, 245, 238);
    RGBColor BEIGE = rgb(245, 245, 220);
    RGBColor OLD_LACE = rgb(253, 245, 230);
    RGBColor FLORAL_WHITE = rgb(255, 250, 240);
    RGBColor IVORY = rgb(255, 255, 240);
    RGBColor ANTIQUE_WHITE = rgb(250, 235, 215);
    RGBColor LINEN = rgb(250, 240, 230);
    RGBColor LAVENDER_BLUSH = rgb(255, 240, 245);
    RGBColor MISTY_ROSE = rgb(255, 228, 225);

    //Gray color codes
    RGBColor GAINSBORO = rgb(220, 220, 220);
    RGBColor LIGHT_GRAY = rgb(211, 211, 211);
    RGBColor SILVER = rgb(192, 192, 192);
    RGBColor DARK_GRAY = rgb(169, 169, 169);
    RGBColor GRAY = rgb(128, 128, 128);
    RGBColor DIM_GRAY = rgb(105, 105, 105);
    RGBColor LIGHT_SLATE_GRAY = rgb(119, 136, 153);
    RGBColor SLATE_GRAY = rgb(112, 128, 144);
    RGBColor DARK_SLATE_GRAY = rgb(47, 79, 79);
    RGBColor BLACK = rgb(0, 0, 0);
}
