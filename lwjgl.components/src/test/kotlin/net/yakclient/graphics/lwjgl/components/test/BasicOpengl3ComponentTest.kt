package net.yakclient.graphics.lwjgl.components.test

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.api.FunctionalComponent
import net.yakclient.graphics.api.GuiComponent
import net.yakclient.graphics.api.MutableGuiPropertiesMap
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.render.plus
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.util.ColorCodes
import net.yakclient.graphics.util.YakFontFactory
import net.yakclient.graphics.util.rgb
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15
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

        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "LWJGL 3 Component testing", 0, 0)
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

    @Test
    fun `Test basic components`() {
        glOrtho(0.0, WINDOW_WIDTH.toDouble(), WINDOW_HEIGHT.toDouble(), 0.0, 0.0, -1.0)

        val component: GuiComponent = FunctionalComponent(MinecraftHomePage)

        fun List<RenderingContext>.reduceWhile(): List<RenderingContext> {
            return flatMap { r -> r.reduce().let { if (it.isNotEmpty()) it.reduceWhile() else listOf(r) } }
        }

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()
            GL15.glClear(GL15.GL_COLOR_BUFFER_BIT or GL15.GL_DEPTH_BUFFER_BIT)

            val contexts = component.renderNatively(MutableGuiPropertiesMap().build()).reduceWhile()

            var toRender = contexts
            val batched = ArrayList<RenderingContext>()

            while (toRender.isNotEmpty()) {
                val context = toRender.first()
                val part = toRender.partition { other ->
                    context.combinable(other)
                }

                toRender = part.second

                val element = part.first.reduce { acc, renderingContext ->
                    (acc + renderingContext) ?: throw IllegalStateException("Contexts should be combinable!")
                }

                batched.add(element)

            }

            batched.forEach(RenderingContext::renderUsingDefault)
            glfwSwapBuffers(window)
        }

        println("Ending LWJGL")
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }
}

val MinecraftHomePage: Component = { props ->
    val background = useTexture("/img_1.png")
    var id = 0

    build(use<Box>(id++)) {
        set("x") to 0f
        set("y") to 0f
        set("width") to WINDOW_WIDTH
        set("height") to WINDOW_HEIGHT
        set("backgroundimage") to background
    }

     build(use(MinecraftButton, id++)) {
        set("scale") to 1
        set("x") to 200
        set("y") to 300
        set("value") to "Singleplayer"
    }

    build(use(MinecraftButton, id++)) {
        set("scale") to 1
        set("x") to 200
        set("y") to 380
        set("value") to "Multiplayer"
    }

    build(use(MinecraftButton, id++)) {
        set("scale") to 1
        set("x") to 200
        set("y") to 460
        set("value") to "Minecraft Realms"
    }

    build(use(MinecraftButton, id++)) {
        set("scale") to 2
        set("x") to 200
        set("y") to 540
        set("value") to "Options"
    }

    @Suppress("UNUSED_CHANGED_VALUE")
    build(use(MinecraftButton,  id++)) {
        set("scale") to 2
        set("x") to 400
        set("y") to 540
        set("value") to "Quit game"

        set("onclick") to Runnable {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), true)
        }
    }
}

val MinecraftButton: Component = { props ->
    val maxSize: Int = props.getAs("maxSize") ?: 400
    val scale = props.requireAs<Int>("scale")
    val size = (maxSize / scale - (scale - 1) * 10)

    val x = props.requireAs<Float>("x")
    val y = props.requireAs<Float>("y")

    val value = props.requireAs<String>("value")
    val mouseClick = props.getAs<Runnable>("onclick") ?: Runnable {  }

    var backgroundColor by useState(0) {
        rgb(112, 113, 113)
    }
    val height = 50

    build(use<Box>(0)) {

        set("x") to x
        set("y") to y
        set("width") to size
        set("height") to height
        set("backgroundcolor") to ColorCodes.WHITE.toColorFunc()
        set("backgroundcolor") to backgroundColor.toColorFunc()

        set("onmouseover") to Runnable {
            println("Mouse into $value")
            backgroundColor = rgb(126, 136, 184)
        }

        set("onmouseout") to Runnable {
            backgroundColor = rgb(112, 113, 113)
        }

        set("onclick") to mouseClick

        build(use<Text>(1)) {
            val font = YakFontFactory.fontOf()
            val textWidth = font.getWidth(value)
            val textHeight = font.getHeight(value)

            set("x") to (x + ((size - textWidth) / 2)).toInt()
            set("y") to (y + ((height - textHeight) / 2)).toInt()
            set("value") to value
        }
    }

}
