package net.yakclient.opengl.api.render;

import net.yakclient.opengl.util.RGBColor;
import net.yakclient.opengl.util.YakFont;

import java.util.Objects;

public class FontRenderingContext implements GLRenderingContext {
    private final YakFont font;
    private final String value;
    private final RGBColor color;
    private final double x, y;

    public FontRenderingContext(YakFont font, String value, RGBColor color, double x, double y) {
        this.font = font;
        this.value = value;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public YakFont getFont() {
        return font;
    }

    public String getValue() {
        return value;
    }

    public RGBColor getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Renderer useRenderer(RenderingType type) {
        if (type != RenderingType.IMMEDIATE) throw new UnsupportedRenderingTypeException(type, this.getClass().getName());
        return new ImmediateFontRenderer(this);
    }

    @Override
    public Renderer useRenderer() {
        return this.useRenderer(RenderingType.IMMEDIATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontRenderingContext that = (FontRenderingContext) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Objects.equals(font, that.font) && Objects.equals(value, that.value) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, value, color, x, y);
    }
}
