package net.yakclient.graphics.opengl3.util

import net.yakclient.graphics.util.YakTextureFactory
import net.yakclient.graphics.util.use
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL15
import org.lwjgl.system.MemoryStack
import kotlin.test.Test

private const val WINDOW_HEIGHT: Int = 500
private const val WINDOW_WIDTH: Int = 500

class TestGL3Textures {
    @Test
    fun `Test texture displaying`() {

        check(GLFW.glfwInit()) { "GLFW not initialised." }

        val window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Texture tests", 0, 0)
        assert(window != 0L)

        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!
        val width = vidmode.width()
        val height = vidmode.height()
        GLFW.glfwSetWindowPos(
            window,
            (width - WINDOW_WIDTH) / 2,
            (height - WINDOW_HEIGHT) / 2
        )

        GLFW.glfwMakeContextCurrent(window)

        GL.createCapabilities()

        GL11.glOrtho(0.0, 1.0, 1.0, 0.0, 0.0, -1.0)

        val url = javaClass.getResource("/test-image.png")!!
        val tex = YakTextureFactory.loadTexture(url)
        val vbo = MemoryStack.stackPush().use { stack ->
            val verts = stack.mallocFloat(4 * 8)

            verts.put(0f).put(0f)
                .put(1f).put(0f).put(0f).put(1f)
                .put(0f).put(0f)

            verts.put(1f).put(0f)
                .put(0f).put(1f).put(0f).put(1f)
                .put(1f).put(0f)

            verts.put(1f).put(1f)
                .put(0f).put(0f).put(1f).put(1f)
                .put(1f).put(1f)

            verts.put(0f).put(1f)
                .put(0f).put(0f).put(0f).put(0f)
                .put(0f).put(1f)

            verts.flip()

            val vbo = GL15.glGenBuffers()
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
            vbo


        }


        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents()
            GL15.glClear(GL15.GL_COLOR_BUFFER_BIT or GL15.GL_DEPTH_BUFFER_BIT)

            GL15.glEnableClientState(GL15.GL_VERTEX_ARRAY)
            GL15.glEnableClientState(GL15.GL_COLOR_ARRAY)
            GL15.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY)

            GL15.glEnable(GL_TEXTURE_2D)

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

            tex.bind()

            GL11.glVertexPointer(2, GL15.GL_FLOAT, 32, 0)
            GL11.glColorPointer(4, GL15.GL_FLOAT, 32, 0)
            GL11.glTexCoordPointer(2, GL15.GL_FLOAT, 32, 0)

            GL15.glDrawArrays(GL15.GL_QUADS, 0, 4)

            GL15.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
            GL15.glDisableClientState(GL15.GL_COLOR_ARRAY)
            GL15.glDisableClientState(GL15.GL_VERTEX_ARRAY)


            GLFW.glfwSwapBuffers(window)
        }
    }

}