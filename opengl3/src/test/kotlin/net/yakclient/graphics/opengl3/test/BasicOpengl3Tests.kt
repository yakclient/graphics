package net.yakclient.graphics.opengl3.test

import net.yakclient.graphics.util.use
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15.*
import org.lwjgl.system.MemoryStack.stackPush
import kotlin.test.Test


private const val WINDOW_HEIGHT = 400
private const val WINDOW_WIDTH = 400

class BasicOpengl3Tests {
    @Test
    fun `Render immediate mode triangle`() {
        check(glfwInit()) { "GLFW not initialised." }

        val window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Immediate Triangle Test", 0, 0)
        assert(window != 0L)

        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
        val width = vidmode.width()
        val height = vidmode.height()
        glfwSetWindowPos(
            window,
            (width - WINDOW_WIDTH) / 2,
            (height - WINDOW_HEIGHT) / 2
        )

        glfwMakeContextCurrent(window)

        GL.createCapabilities()

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glBegin(GL_TRIANGLES)

            glVertex2f(0f, 0.5f)
            glColor3f(1f, 0f, 0f)

            glVertex2f(0.5f, -0.5f)
            glColor3f(0f, 1f, 0f)

            glVertex2f(-0.5f, -0.5f)
            glColor3f(0f, 0f, 1f)

            glEnd()

            glfwSwapBuffers(window)
        }
    }

    @Test
    fun `Render VBO Quad`() {
        check(glfwInit()) { "GLFW not initialised." }

        val window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "VBO Quad Test", 0, 0)
        assert(window != 0L)

        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
        val width = vidmode.width()
        val height = vidmode.height()
        glfwSetWindowPos(
            window,
            (width - WINDOW_WIDTH) / 2,
            (height - WINDOW_HEIGHT) / 2
        )

        glfwMakeContextCurrent(window)

        GL.createCapabilities()

        val vbo = stackPush().use { stack ->
            val verts = stack.mallocFloat(4 * 6)

            verts.put(0.5f).put(0.5f).put(1f).put(0f).put(0f).put(1f)
            verts.put(0.5f).put(-0.5f).put(0f).put(1f).put(0f).put(1f)
            verts.put(-0.5f).put(-0.5f).put(0f).put(0f).put(1f).put(1f)
            verts.put(-0.5f).put(0.5f).put(0f).put(0f).put(0f).put(0f)

            verts.flip()

            val vbo = glGenBuffers()
            glBindBuffer(GL_ARRAY_BUFFER, vbo)
            glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            vbo
        }

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glEnableClientState(GL_VERTEX_ARRAY)
            glEnableClientState(GL_COLOR_ARRAY)

            glEnable(GL_BLEND)

            glBindBuffer(GL_ARRAY_BUFFER, vbo)
            GL11.glVertexPointer(2, GL_FLOAT, 24, 0)
            GL11.glColorPointer(4, GL_FLOAT, 24, 0)
            glDrawArrays(GL_QUADS, 0, 4)

            glDisableClientState(GL_COLOR_ARRAY)
            glDisableClientState(GL_VERTEX_ARRAY)


            glfwSwapBuffers(window)
        }
    }
}
