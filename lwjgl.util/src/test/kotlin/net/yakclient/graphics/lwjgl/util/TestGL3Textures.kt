package net.yakclient.graphics.lwjgl.util

import net.yakclient.graphics.util.FontFactory
import net.yakclient.graphics.util.use
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import kotlin.test.BeforeTest
import kotlin.test.Test

private const val WINDOW_HEIGHT: Int = 500
private const val WINDOW_WIDTH: Int = 500

class TestGL3Textures {
    @BeforeTest
    fun init() {
        System.setProperty("java.awt.headless", "true")
    }

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


        val tex = FontFactory.fontOf()['a']?.backingTexture!!
        val vbo = MemoryStack.stackPush().use { stack ->
            val verts = stack.mallocFloat(8 * 4)

            verts.put(0.25f).put(0.25f)
                .put(0.75f).put(0.25f).put(0.25f).put(0.75f)
                .put(0.25f).put(0.25f)

            verts.put(0.75f).put(0.25f)
                .put(0.25f).put(0.75f).put(0.25f).put(0.75f)
                .put(0.75f).put(0.25f)

            verts.put(0.75f).put(0.75f)
                .put(0.25f).put(0.25f).put(0.75f).put(0.75f)
                .put(0.75f).put(0.75f)
//
            verts.put(0.25f).put(0.75f)
                .put(0.25f).put(0.25f).put(0.25f).put(0.25f)
                .put(0.25f).put(0.75f)

            verts.flip()

            val vbo = GL15.glGenBuffers()
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW)

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)

//            glBindVertexArray(0)

            vbo
        }

        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents()
            GL15.glClear(GL15.GL_COLOR_BUFFER_BIT or GL15.GL_DEPTH_BUFFER_BIT)

            GL15.glEnableClientState(GL15.GL_VERTEX_ARRAY)
            GL15.glEnableClientState(GL15.GL_COLOR_ARRAY)
            GL15.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY)

            GL15.glEnable(GL_TEXTURE_2D)



            glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)



            tex.bind()

            GL11.glVertexPointer(2, GL15.GL_FLOAT, 32, 0)
            GL11.glColorPointer(4, GL15.GL_FLOAT, 32, 0)
            GL11.glTexCoordPointer(2, GL15.GL_FLOAT, 32, 0)

//            GL15.glDrawElements(GL15.GL_QUADS, 4, GL_FLOAT, 0)
            GL15.glDrawArrays(GL15.GL_QUADS, 0, 4)

            GL15.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
            GL15.glDisableClientState(GL15.GL_COLOR_ARRAY)
            GL15.glDisableClientState(GL15.GL_VERTEX_ARRAY)

            GLFW.glfwSwapBuffers(window)
        }
    }

    @Test
    fun testArraySize() {
        Array(53390000) { 1000 }
//        for (i in 53_000_000 until 54_000_000 step 10_000) {
//            println(i)
//            Array(i) { -223957897 }
//        }

    }

    @Test
    fun arraySpeed() {
        fun Int.glValue(toShr: Int) = ((this shr toShr) and 255).toByte()

        val array = Array(64000000) { -223957897 }

        val buf = MemoryUtil.memAlloc(8000 * 8000 * 4)
        println("Started")

        for (y in 0 until 8000)
            for (x in 0 until 8000) {
                val thing = array[19]

                buf.put((thing).glValue(8))
                buf.put((thing).glValue(8))
                buf.put((thing).glValue(8))
                buf.put((thing).glValue(8))
            }
        println(buf.get(12045))
        println(buf.hasRemaining())

        println("stopped")
    }
}

