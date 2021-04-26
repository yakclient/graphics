package net.yakclient.opengl.util;

import org.newdawn.slick.Color;

public interface SlickTranslator {
    static Color toColor(RGBColor color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}
