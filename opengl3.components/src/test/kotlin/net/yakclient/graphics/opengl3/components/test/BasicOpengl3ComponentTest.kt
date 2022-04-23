package net.yakclient.graphics.opengl3.components.test

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.api.FunctionalComponent
import net.yakclient.graphics.api.GuiComponent
import net.yakclient.graphics.api.PropertyFactory
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.util.ColorCodes
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import java.util.function.Consumer
import kotlin.test.BeforeTest
import kotlin.test.Test

private const val WINDOW_HEIGHT = 800
private const val WINDOW_WIDTH = 800

class BasicOpengl3ComponentTest {
    private var window: Long = -1

    @BeforeTest
    fun initGL() {
        check(GLFW.glfwInit()) { "GLFW not initialised." }

        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "VBO Quad Test", 0, 0)
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

        GL11.glOrtho(0.0, WINDOW_WIDTH.toDouble(), WINDOW_HEIGHT.toDouble(), 0.0, 0.0, -1.0)

    }

    @Test
    fun `Test basic components`() {
        val component: GuiComponent = FunctionalComponent(BasicTestComponent)


        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents()
            GL15.glClear(GL15.GL_COLOR_BUFFER_BIT or GL15.GL_DEPTH_BUFFER_BIT)

            val contexts = component.renderNatively(PropertyFactory().build())
            for (context in contexts) {
                context.renderUsingDefault()
            }

            GLFW.glfwSwapBuffers(window)
        }
    }
}

val BasicTestComponent: Component = { props ->
    println("RENDERING")

    var backgroundColor by useState(0) { ColorCodes.RED }

    build(use<Box>(0)) {
        set("x") to 400
        set("y") to 400
        set("width") to 100
        set("height") to 100
        set("backgroundcolor") to backgroundColor.toColorFunc()
//        set("backgroundimage") to YakTextureFactory.loadTexture(
//            File("/wood_pickaxe.png").toURI().toURL()  ?: throw RuntimeException("Resource not found!")
//        )
        set("ondbclick") to Runnable {
            println("double click")
        }
        set("onclick") to Runnable {
            println("Clicked!")
        }
        set("onmouseover") to Runnable {
            backgroundColor = ColorCodes.BLUE

            println("Mouse over")
        }
        set("onmouseup") to Runnable {
            println("mouse up!")
        }
        set("onmousedown") to Runnable {
            println("mouse down!")
        }
        set("onmouseout") to Runnable {
            backgroundColor = ColorCodes.RED

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

//   if (doubleClicked.value) build(use<Box>(2)) {
//        set("x") to 20
//        set("y") to 20
//        set("width") to 64
//        set("height") to 500
//        set("backgroundcolor") to SolidColor(ColorCodes.RED)
//    }
}