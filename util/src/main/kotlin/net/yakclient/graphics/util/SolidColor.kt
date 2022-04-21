package net.yakclient.graphics.util

import net.yakclient.graphics.util.buffer.SafeFloatBuffer

public class SolidColor(
    private val color: Color,
) : ColorFunction {
    override fun toAggregation(vertices: SafeFloatBuffer): SafeFloatBuffer {
        return ColorFunction.applyColorEffect(0, vertices, color)
    }
}