package net.yakclient.graphics.components.test

import net.yakclient.graphics.api.gui.Component
import net.yakclient.graphics.api.gui.FunctionalComponent
import net.yakclient.graphics.api.gui.GuiComponent
import net.yakclient.graphics.api.gui.PropertyFactory
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.opengl2.components.OpenGL2Box
import net.yakclient.graphics.util.YakTextureFactory

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
            }
}
