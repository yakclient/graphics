package net.yakclient.graphics.util

import net.yakclient.graphics.util.buffer.SafeFloatBuffer

public class GradientColor(
    private val colors: List<Color>,
    private val offset: Int,
) : ColorFunction {
    public constructor(vararg colors: Color) : this(colors.toList(), 0)

    public constructor(offset: Int, vararg colors: Color) : this(colors.toList(), offset)

    override fun toAggregation(buf: SafeFloatBuffer, verticeSize: Int): SafeFloatBuffer {
        return ColorFunction.applyColorEffect(offset, buf, *colors.toTypedArray())
    }
}