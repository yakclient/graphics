package net.yakclient.opengl.util;

public class SolidRGBColor implements ColorFunction {
    private final RGBColor color;

    public SolidRGBColor(RGBColor color) {
        this.color = color;
    }

    @Override
    public ColorAggregation toAggregation(int offset, VerticeAggregation aggregation) {
        return ColorFunction.applyColorEffect(offset, aggregation, this.color);
    }
}
