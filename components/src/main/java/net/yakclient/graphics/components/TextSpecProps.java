package net.yakclient.graphics.components;

import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.api.gui.IllegalPropertyException;
import net.yakclient.graphics.api.gui.SpecifiedProperties;
import net.yakclient.graphics.util.ColorCodes;
import net.yakclient.graphics.util.RGBColor;
import net.yakclient.graphics.util.YakFont;
import net.yakclient.graphics.util.YakFontFactory;

import java.util.Optional;

public class TextSpecProps extends SpecifiedProperties {
    public TextSpecProps(GuiProperties properties) {
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
