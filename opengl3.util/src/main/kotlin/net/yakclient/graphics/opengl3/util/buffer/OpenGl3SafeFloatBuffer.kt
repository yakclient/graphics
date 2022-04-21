package net.yakclient.graphics.opengl3.util.buffer

import net.yakclient.graphics.util.buffer.SafeBuffer
import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer

internal class OpenGl3SafeFloatBuffer(
    override val size: Int
) : SafeFloatBuffer {
    override val buffer: FloatBuffer = MemoryUtil.memAllocFloat(size)

    override fun put(number: Float): SafeBuffer<FloatBuffer, Float> = also { buffer.put(number) }

    override fun putAll(numbers: Array<Float>): SafeBuffer<FloatBuffer, Float> = also { buffer.put(numbers.toFloatArray()) }

    override fun close() = MemoryUtil.memFree(buffer)
}