package net.yakclient.graphics.util

public class SolidColor(
    private val color: Color,
) : ColorFunction {
    override fun toAggregation(aggregation: VerticeAggregation): ColorAggregation {
        return ColorFunction.applyColorEffect(0, aggregation, color)
    }
}