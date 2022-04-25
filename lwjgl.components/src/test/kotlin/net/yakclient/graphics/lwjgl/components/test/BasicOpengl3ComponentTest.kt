package net.yakclient.graphics.lwjgl.components.test

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.api.FunctionalComponent
import net.yakclient.graphics.api.GuiComponent
import net.yakclient.graphics.api.MutableGuiPropertiesMap
import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.util.ColorCodes
import net.yakclient.graphics.util.YakTextureFactory
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import java.util.function.Consumer
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

private const val WINDOW_HEIGHT = 800
private const val WINDOW_WIDTH = 800

class BasicOpengl3ComponentTest {
    private var window: Long = -1

    @BeforeTest
    fun initGL() {
        System.setProperty("java.awt.headless", "true") // MASSIVELY IMPORTANT

        check(glfwInit()) { "GLFW not initialised." }

        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "VBO Quad Test", 0, 0)
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
    }

    @AfterTest
    fun destroyGL() {
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    @Test
    fun `Test basic components`() {
        GL11.glOrtho(0.0, WINDOW_WIDTH.toDouble(), WINDOW_HEIGHT.toDouble(), 0.0, 0.0, -1.0)

        val component: GuiComponent = FunctionalComponent(BasicTestComponent)

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()
            GL15.glClear(GL15.GL_COLOR_BUFFER_BIT or GL15.GL_DEPTH_BUFFER_BIT)

            val contexts = component.renderNatively(MutableGuiPropertiesMap().build())
            for (context in contexts) {
                val renderer = context.useRenderer() as Renderer<RenderingContext>
                renderer.render(context)
            }
            println("Doing good")
            glfwSwapBuffers(window)
        }
    }
}

val BasicTestComponent: Component = { props ->
    println("RENDERING")

    var backgroundColor by useState(0) { ColorCodes.RED }
    var dbClicked by useState(1) { false }

    build(use<Box>(0)) {
        set("x") to 400
        set("y") to 400
        set("width") to 100
        set("height") to 100
        set("backgroundcolor") to backgroundColor.toColorFunc()
        set("backgroundimage") to YakTextureFactory.loadTexture(
            checkNotNull(BasicOpengl3ComponentTest::class.java.getResource("/img.png")) { "Resource must not be null!" }
        )

        set("ondbclick") to Runnable {
            dbClicked = true
            backgroundColor = ColorCodes.GREEN

            println("double click")
        }
        set("onclick") to Runnable {
            println("Clicked!")
        }
        set("onmouseover") to Runnable {
           if (!dbClicked) backgroundColor = ColorCodes.BLUE else backgroundColor = ColorCodes.GREEN

            println("Mouse over")
        }
        set("onmouseup") to Runnable {
            println("mouse up!")
        }
        set("onmousedown") to Runnable {
            println("mouse down!")
        }
        set("onmouseout") to Runnable {
           if (!dbClicked) backgroundColor = ColorCodes.RED else backgroundColor = ColorCodes.LIME

            println("mouse out")
        }
        set("onmousemove") to Runnable {
            println("mouse move")
        }
        set("onkeydown") to Consumer<Int> {
            println("Key $it down")
        }
        set("onkeyup") to Consumer<Int> {
            println("Key $it up")
        }
    }
    build(use<Text>(1)) {
        set("x") to 100
        set("y") to 600
        set("value") to "Hey dude, how are you? Lets see how much of this works!"
    }
//   if (doubleClicked.value) build(use<Box>(2)) {
//        set("x") to 20
//        set("y") to 20
//        set("width") to 64
//        set("height") to 500
//        set("backgroundcolor") to SolidColor(ColorCodes.RED)
//    }
}
