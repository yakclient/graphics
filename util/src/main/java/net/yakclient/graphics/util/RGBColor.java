package net.yakclient.graphics.util;

public class RGBColor {
    private final float red;
    private final float green;
    private final float blue;

    private final float alpha;

    public RGBColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public RGBColor(float red, float green, float blue) {
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
        return new ColorAggregation.ColorNode(this.red, this.green, this.blue, this.alpha);
    }
}
