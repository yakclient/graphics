package net.yakclient.opengl.util;

public class VacantColorFunction implements ColorFunction {
    @Override
    public ColorAggregation toAggregation(int offset, VerticeAggregation aggregation) {
        return new ColorAggregation();
    }
}
