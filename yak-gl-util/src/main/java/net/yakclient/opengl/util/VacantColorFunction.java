package net.yakclient.opengl.util;

public class VacantColorFunction implements ColorFunction {
    @Override
    public ColorAggregation toAggregation(VerticeAggregation aggregation) {
        return new ColorAggregation();
    }
}
