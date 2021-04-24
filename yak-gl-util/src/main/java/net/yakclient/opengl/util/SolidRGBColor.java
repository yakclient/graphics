package net.yakclient.opengl.util;

public class SolidRGBColor implements ColorFunction {
    private final RGBColor color;
    private final float alpha;

    public SolidRGBColor(RGBColor color) {
        this.color = color;
        this.alpha = ColorAggregation.DEFAULT_ALPHA;
    }

    public SolidRGBColor(RGBColor color, float alpha) {
        this.color = color;
        this.alpha = alpha;
    }

    @Override
    public ColorAggregation toAggregation(VerticeAggregation aggregation) {
        return ColorFunction.applyColorEffect(0, this.alpha, aggregation, this.color);
    }
}
