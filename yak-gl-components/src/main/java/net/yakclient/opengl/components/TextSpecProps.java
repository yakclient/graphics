package net.yakclient.opengl.components;

import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.gui.IllegalPropertyException;
import net.yakclient.opengl.api.gui.SpecifiedProperties;
import net.yakclient.opengl.util.ColorCodes;
import net.yakclient.opengl.util.RGBColor;
import net.yakclient.opengl.util.YakFont;
import net.yakclient.opengl.util.YakFontFactory;

import java.util.Optional;

public class TextSpecProps extends SpecifiedProperties {
    public TextSpecProps(GUIProperties properties) {
        super(properties);
    }

    public String getValue() {
        return this.<String>get("value").orElseThrow(()-> new IllegalPropertyException("value"));
    }

    public double getX() {
        return this.<Double>get("x").orElseThrow(()->new IllegalPropertyException("x"));
    }

    public double getY() {
        return this.<Double>get("y").orElseThrow(()->new IllegalPropertyException("y"));
    }

    public RGBColor getColor() {
        return this.<RGBColor>get("color").or(()-> Optional.of(ColorCodes.WHITE)).get();
    }

    public YakFont getFont() {
        return this.<YakFont>get("font").or(()-> Optional.of(YakFontFactory.create().buildFont())).get();
    }
}
