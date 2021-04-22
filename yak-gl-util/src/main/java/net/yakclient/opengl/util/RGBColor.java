package net.yakclient.opengl.util;

public class RGBColor {
    public static final float COLOR_MAX = 255f;

    private final float red;
    private final float green;
    private final float blue;

    private final float alpha;

    public RGBColor(int red, int green, int blue, float alpha) {
        this.red = red % COLOR_MAX;
        this.green = green % COLOR_MAX;
        this.blue = blue % COLOR_MAX;
        this.alpha = alpha;
    }

    public RGBColor(int red, int green, int blue) {
        this(red, green, blue, ColorAggregation.DEFAULT_ALPHA);
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public ColorAggregation.ColorNode toColorNode() {
        return new ColorAggregation.ColorNode(this.red / COLOR_MAX, this.green / COLOR_MAX, this.blue / COLOR_MAX, this.alpha);
    }
}
