package net.yakclient.graphics.opengl2.components

import net.yakclient.graphics.api.gui.GuiProperties
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.opengl2.render.GLRenderingData
import net.yakclient.graphics.opengl2.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.util.function.Consumer

public fun main() {
    println(OpenGL2Box::class.java.module.name)
}

public class OpenGL2Box : Box() {
    override fun renderNatively(props: GuiProperties): List<RenderingContext> {
        val width: Double = props.requireAs("width")
        val height: Double = props.requireAs("height")
        val x: Double = props.requireAs("x")
        val y: Double = props.requireAs("y")
        //  Transparency is defined in the background color.
        val backgroundColor = props.getAs<ColorFunction>("backgroundcolor")
        val texture = props.getAs<YakTexture>("backgroundimage") ?: VacantTexture()

        //  Events
        val onClick = props.getAs<Runnable>("onclick") ?: Runnable {}
        val onDbClick = props.getAs<Runnable>("ondbclick") ?: Runnable {}
        val onMouseDown = props.getAs<Runnable>("onmousedown") ?: Runnable {}
        val onMouseUp = props.getAs<Runnable>("onmouseup") ?: Runnable {}
        val onMouseOver = props.getAs<Runnable>("onmouseover") ?: Runnable {}
        val onMouseMove = props.getAs<Runnable>("onmousemove") ?: Runnable {}
        val onMouseOut = props.getAs<Runnable>("onmouseout") ?: Runnable {}
        val onKeyDown = props.getAs<Consumer<Int>>("onkeydown") ?: Consumer<Int> {}
        val onKeyUp = props.getAs<Consumer<Int>>("onkeyup") ?: Consumer<Int> {}
        val onHover = props.getAs<Runnable>("onhover") ?: Runnable {}

        //  State
        val lastClick = this.useState(0) { System.currentTimeMillis() }
        val wasLastTickMouseDown = this.useState(1) { false }
        val lastTickMousePos = this.useState(2) { Vertice(Mouse.getX(), Mouse.getY()) }
        val focused = this.useState(3) { false }
        val lastKeyDown = this.useState(4) { YakGraphicsUtils.CHAR_NONE }
        val wasLastTickKeyDown = this.useState(5) { false }


        //  Event implementation
        val lmbDown: Boolean = Mouse.isButtonDown(YakGraphicsUtils.MOUSE_LEFT_BUTTON)
        if (wasLastTickMouseDown.value && !lmbDown) onMouseUp()// .ifPresent { obj: Runnable -> obj.run() }
        val isMouseOver = rectBounding(Mouse.getX().toDouble(), Mouse.getY().toDouble(), y, x, x + width, y + height)
        if (isMouseOver) onMouseOver()//.ifPresent { obj: Runnable -> obj.run() }
        if (isMouseOver && lmbDown) onMouseDown()// onMouseDown.ifPresent { obj: Runnable -> obj.run() }
        val ltPos: Vertice = lastTickMousePos.value
        if (ltPos == Vertice(
                Mouse.getX(),
                Mouse.getY()
            ) && isMouseOver
        ) onHover()//.ifPresent { obj: Runnable -> obj.run() }
        if (ltPos != Vertice(
                Mouse.getX(),
                Mouse.getY()
            ) && isMouseOver
        ) onMouseMove()//.ifPresent { obj: Runnable -> obj.run() }
        if (rectBounding(
                ltPos.x,
                ltPos.y,
                y,
                x,
                x + width,
                y + height
            ) && !isMouseOver
        ) onMouseOut()//.ifPresent { obj: Runnable -> obj.run() }
        while (Keyboard.next()) {
            val eventKey: Int = Keyboard.getEventKey()
            if (focused.value && eventKey != YakGraphicsUtils.CHAR_NONE) lastKeyDown.value = eventKey
        }
        val key: Int = lastKeyDown.value
        if (Keyboard.isKeyDown(key)) {
            onKeyDown(key)//.ifPresent { consumer -> consumer.accept(key) }
            wasLastTickKeyDown.value = true
        } else if (wasLastTickKeyDown.value) {
            onKeyUp(key)// { consumer -> consumer.accept(key) }
            wasLastTickKeyDown.value = false
        }
        if (isMouseOver) wasLastTickMouseDown.value = lmbDown
        while (Mouse.next()) {
            val button: Int = Mouse.getEventButton()
            if (button == YakGraphicsUtils.MOUSE_LEFT_BUTTON && Mouse.getEventButtonState()) {
                if (System.currentTimeMillis() - lastClick.value <= YakGraphicsUtils.MAX_DOUBLE_CLICK_TIME && isMouseOver) {
                    focused.value = true
                    onDbClick()// { obj: Runnable -> obj.run() }
                    lastClick.value = 0L
                } else {
                    if (!isMouseOver) focused.value = false else {
                        lastClick.value = System.currentTimeMillis()
                        focused.value = true
                        onClick()// { obj: Runnable -> obj.run() }
                    }
                }
            }
        }
        lastTickMousePos.value = Vertice(Mouse.getX(), Mouse.getY())

        //  Rendering
        val vertices: VerticeAggregation = verticesOf(
            Vertice(x, y),
            Vertice(x + width, y),
            Vertice(x + width, y + height),
            Vertice(x, y + height)
        )

        val colors: ColorFunction =
            backgroundColor ?: VacantColorFunction()// ?:  backgroundColor.or(Supplier<Optional<out ColorFunction>> {



        return this.combine(
            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    vertices, colors.toAggregation(vertices), texs = texsOf(
                        TexNode(0f, 0f),
                        TexNode(1f, 0f),
                        TexNode(0f, 1f),
                        TexNode(1f, 1f),
                    ), texture = texture
                )
            ), this.applyChildren(props)
        )
    }

    private fun rectBounding(x: Double, y: Double, top: Double, left: Double, bottom: Double, right: Double): Boolean {
        return top < y && bottom > y && left < x && right > x
    }
}