package net.yakclient.graphics.components.test

import net.yakclient.graphics.api.Component
import net.yakclient.graphics.api.FunctionalComponent
import net.yakclient.graphics.api.GuiComponent
import net.yakclient.graphics.api.MutableGuiPropertiesMap
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.util.YakTextureFactory
import java.io.File
import java.util.function.Consumer

fun main() {
//    println(OpenGL2Box::class.java.module.name)

    val component: GuiComponent = FunctionalComponent(BasicTestComponent)
    OpenGLSetup.setupAndStart {

        val contexts = component.renderNatively(MutableGuiPropertiesMap().build())
        for (context in contexts) {
            context.renderUsingDefault()
        }
    }
}

// build(use<Text>(0)) {
//        set("x") to 100
//        set("y") to 100
//        set("value") to "Why is this text rendering weirdly?"
//    }
val BasicTestComponent: Component = { props ->
    var doubleClicked by useState(0) { false }
    println("RENDERING")

    build(use<Box>(0)) {
        set("x") to 400
        set("y") to 400
        set("width") to 200
        set("height") to 300
        set("backgroundimage") to YakTextureFactory.loadTexture(
           File("/wood_pickaxe.png").toURI().toURL()  ?: throw RuntimeException("Resource not found!")
        )
        set("ondbclick") to Runnable {
            println("double click")
        }
        set("onclick") to Runnable {
            println("Clicked!")
        }
        set("onmouseover") to Runnable {
            println("Mouse over")
        }
        set("onmouseup") to Runnable {
            println("mouse up!")
        }
        set("onmousedown") to Runnable {
            println("mouse down!")
        }
        set("onmouseout") to Runnable {
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
