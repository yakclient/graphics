package net.yakclient.graphics.opengl3.util.buffer

import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider

public class OpenGL3SafeFloatBufferProvider : SafeFloatBufferProvider {
    override fun createNew(size: Int): SafeFloatBuffer = OpenGl3SafeFloatBuffer(size)
}