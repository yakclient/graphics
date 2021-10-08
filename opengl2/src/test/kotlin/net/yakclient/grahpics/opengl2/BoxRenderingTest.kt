package net.yakclient.grahpics.opengl2

import net.yakclient.graphics.util.YakGraphicsUtils
import net.yakclient.graphics.util.colorsOf
import net.yakclient.graphics.util.verticesOf
import org.lwjgl.LWJGLException
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import kotlin.system.exitProcess

fun main() {
    try {
        Display.setDisplayMode(DisplayMode(500, 500))
        Display.setResizable(true)
        Display.create()
    } catch (e: LWJGLException) {
        println("FAILED: $e")
        exitProcess(1)
    }

    GL11.glOrtho(0.0, Display.getWidth().toDouble(), Display.getHeight().toDouble(), 0.0, 0.0, -1.0)

    while (!Display.isCloseRequested()) {
        val verticesHandle: Int = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesHandle)
        GL15.glBufferData(
            GL15.GL_ARRAY_BUFFER,
            YakGraphicsUtils.flipBuf(verticesOf(2, 100.0, 100.0, 100.0, 200.0, 200.0, 200.0, 200.0, 100.0).asBuffer()),
            GL15.GL_STATIC_DRAW
        )
        GL11.glVertexPointer(4, GL11.GL_DOUBLE, 0, 0);
        val colorHandle: Int = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle)
        GL15.glBufferData(
            GL15.GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(
                colorsOf(
                    1f, 0f, 0f,
                    0f, 1f, 0f,
                    0f, 0f, 1f,
                    1f, 1f, 1f
                ).asBuffer()
            ), GL15.GL_STATIC_DRAW
        )
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0)

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)

        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4)

        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)

        Display.update()
        Display.sync(10)
    }
    Display.destroy()

}