package net.yakclient.graphics.components.test

import net.yakclient.graphics.api.gui.Component
import net.yakclient.graphics.api.gui.FunctionalComponent
import net.yakclient.graphics.api.gui.GuiComponent
import net.yakclient.graphics.api.gui.PropertyFactory
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.util.ColorCodes
import net.yakclient.graphics.util.SolidColor
import net.yakclient.graphics.util.YakTextureFactory
import org.lwjgl.opengl.GL11

fun main() {
    val component: GuiComponent = FunctionalComponent(BasicTestComponent())
    OpenGLSetup.setupAndStart {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

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

fun BasicTestComponent(): Component = { props ->
    build(use<Box>(0)) {
        set("width") to 256
        set("height") to 256
        set("x") to 100
        set("y") to 100
        set("backgroundimage") ifNotNull javaClass.getResource("/wood.png")?.let { YakTextureFactory.loadTexture(it) }

//        set("backgroundcolor") to  SolidColor(ColorCodes.RED)
    }
}
