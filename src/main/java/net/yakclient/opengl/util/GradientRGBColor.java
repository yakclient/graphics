package net.yakclient.opengl.util;

public class GradientRGBColor implements ColorFunction {
    private final RGBColor[] colors;

    public GradientRGBColor(RGBColor... colors) {
        this.colors = colors;
    }

    @Override
    public ColorAggregation toAggregation(int offset, VerticeAggregation aggregation) {
        return ColorFunction.applyColorEffect(offset, aggregation, this.colors);
    }
}
