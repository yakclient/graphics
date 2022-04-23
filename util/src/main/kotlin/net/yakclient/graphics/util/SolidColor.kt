package net.yakclient.graphics.util

import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.safeFloatBufOf

public class SolidColor(
    private val color: Color,
) : ColorFunction {
    override fun toAggregation(buf: SafeFloatBuffer, vertexSize: Int): SafeFloatBuffer {
        val vertices = buf.size / 2
        val colorBuf = safeFloatBufOf(vertices * 4)
        repeat(vertices) {
            colorBuf.put(color.red).put(color.green).put(color.blue).put(color.alpha)
        }

        return colorBuf
    //        return ColorFunction.applyColorEffect(0, vertices, color)
    }
}