package net.yakclient.graphics.util;

public class GradientRGBColor implements ColorFunction {
    private final RGBColor[] colors;
    private final int offset;
    private final float alpha;

    public GradientRGBColor(RGBColor... colors) {
        this.colors = colors;
        this.offset = 0;
        this.alpha = ColorAggregation.DEFAULT_ALPHA;
    }

    public GradientRGBColor(int offset, RGBColor... colors) {
        this.colors = colors;
        this.offset = offset;
        this.alpha = ColorAggregation.DEFAULT_ALPHA;
    }

    public GradientRGBColor(int offset, float alpha, RGBColor... colors) {
        this.colors = colors;
        this.offset = offset;
        this.alpha = alpha;
    }

    public GradientRGBColor(float alpha, RGBColor... colors) {
        this.colors = colors;
        this.alpha = alpha;
        this.offset = 0;
    }

    @Override
    public ColorAggregation toAggregation(VerticeAggregation aggregation) {
        return ColorFunction.applyColorEffect(this.offset, this.alpha, aggregation, this.colors);
    }
}
