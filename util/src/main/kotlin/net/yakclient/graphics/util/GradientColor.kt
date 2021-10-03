package net.yakclient.graphics.util

public class GradientColor(
    private val colors: List<Color>,
    private val offset: Int,
) : ColorFunction {
    public constructor(vararg colors: Color) : this(colors.toList(), 0)

    public constructor(offset: Int, vararg colors: Color) : this(colors.toList(), offset)

    override fun toAggregation(aggregation: VerticeAggregation): ColorAggregation {
        return ColorFunction.applyColorEffect(offset, aggregation, *colors.toTypedArray())
    }
}