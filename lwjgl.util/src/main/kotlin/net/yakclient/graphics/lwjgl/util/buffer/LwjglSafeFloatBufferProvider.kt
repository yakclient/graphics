package net.yakclient.graphics.lwjgl.util.buffer

import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider

public class LwjglSafeFloatBufferProvider : SafeFloatBufferProvider {
    override fun createNew(size: Int): SafeFloatBuffer = LwjglSafeFloatBuffer(size)
}