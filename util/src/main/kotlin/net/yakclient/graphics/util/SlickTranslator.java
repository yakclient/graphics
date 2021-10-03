package net.yakclient.graphics.util;

import org.newdawn.slick.Color;

public interface SlickTranslator {
    static Color toColor(net.yakclient.graphics.util.Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}


