package net.yakclient.graphics.components.test

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.api.FunctionalComponent
import net.yakclient.graphics.api.GuiComponent
import net.yakclient.graphics.api.PropertyFactory
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.components.Text
import net.yakclient.graphics.util.YakTextureFactory
import java.util.function.Consumer

fun main() {
//    println(OpenGL2Box::class.java.module.name)

    val component: GuiComponent = FunctionalComponent(BasicTestComponent())
    OpenGLSetup.setupAndStart {
//        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

        val contexts = component.renderNatively(PropertyFactory().build())

//            val other: Array<RenderingContext> = Text().glRender(
//                PropertyFactory.create()
//                    .addProp("value", "Wow!")
//                    .addProp("x", 100.0)
//                    .addProp("y", 100.0).build()
//            )
        //            contexts[0].useRenderer().glRender();
//            val reversed: Array<RenderingContext> = arrayOf(other[0], contexts[1])
        for (context in contexts) {
            context.useRenderer().render()
        }
    }
}

// build(use<Text>(0)) {
//        set("x") to 100
//        set("y") to 100
//        set("value") to "Why is this text rendering weirdly?"
//    }
fun BasicTestComponent(): Component = { props ->
    val state = useState(0) { 0 }
    println("RENDERING")



    build(use<Text>(0)) {
        set("x") to 10
        set("y") to 10
        set("value") to "AYYY"
    }

    var id = 1
    for (x in 0 until 1000 step 100)
        for (y in 0 until 1000 step 100)
            build(use<Box>(id++)) {
                set("x") to x
                set("y") to y
                set("width") to 64
                set("height") to 64
                set("backgroundimage") to YakTextureFactory.loadTexture(
                    javaClass.getResource("/wood.png") ?: throw RuntimeException("Resource not found!")
                )
                set("onclick") to Runnable {
                    println("CLICKED!")
                }

                set("ondbclick") to Runnable {
                    println("double cooked")
                }

                set("onmousedown") to Runnable {
                    println("mouse down")
                }

                set("onmousemove") to Runnable {
                    println("mouse up")
                }

                set("onmouseout") to Runnable {
                    println("mouse out")
                }

                set("onkeydown") to Consumer<Int> {
                    println("key down: $it")
                }
            }
}
