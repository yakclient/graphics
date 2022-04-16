package net.yakclient.graphics.opengl3.test

import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GLUtil
import kotlin.test.Test


private const val WINDOW_HEIGHT = 400
private const val WINDOW_WIDTH = 400

class QuadTest {
    @Test
    fun `Render basic quad`() {
        println("Hello LWJGL " + Version.getVersion() + "!")

        check(glfwInit()) { "GLFW not initialised." }

        val window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Quad Test", 0, 0)
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

            glBegin(GL_QUADS)

            glVertex2f(-0.5f, -0.5f)
            glVertex2f(0.5f, -0.5f)
            glVertex2f(0.5f, 0.5f)
            glVertex2f(-0.5f, 0.5f)

            glEnd()

            glfwSwapBuffers(window)
        }
    }
}
