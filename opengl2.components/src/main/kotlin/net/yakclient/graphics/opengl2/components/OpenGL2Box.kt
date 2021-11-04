package net.yakclient.graphics.opengl2.components

import net.yakclient.graphics.api.GuiProperties
import net.yakclient.graphics.api.event.onMouseClick
import net.yakclient.graphics.api.event.onMouseMove
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.opengl2.render.GLRenderingData
import net.yakclient.graphics.opengl2.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.util.function.Consumer

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
        val mouseClick = props.getAs<Runnable>("onclick") ?: Runnable {}
        val doubleClick = props.getAs<Runnable>("ondbclick") ?: Runnable {}
        val mouseDown = props.getAs<Runnable>("onmousedown") ?: Runnable {}
        val mouseUp = props.getAs<Runnable>("onmouseup") ?: Runnable {}
//        val mouseOver = props.getAs<Runnable>("onmouseover") ?: Runnable {}
        val mouseMove = props.getAs<Runnable>("onmousemove") ?: Runnable {}
        val mouseOut = props.getAs<Runnable>("onmouseout") ?: Runnable {}
        val keyDown = props.getAs<Consumer<Int>>("onkeydown") ?: Consumer<Int> {}
//        val keyUp = props.getAs<Consumer<Int>>("onkeyup") ?: Consumer<Int> {}
//        val hover = props.getAs<Runnable>("onhover") ?: Runnable {}

        //  State
        val lastClick = this.useState(0, false) { System.currentTimeMillis() }
        val wasLastTickMouseDown = this.useState(1, false) { false }
        val focused = this.useState(3, false) { false }
        val lastKeyDown = this.useState(4, false) { YakGraphicsUtils.CHAR_NONE }
        val wasLastTickKeyDown = this.useState(5, false) { false }

//        val isMouseOver =

        //  Event implementation
//        val lmbDown: Boolean = Mouse.isButtonDown(YakGraphicsUtils.MOUSE_LEFT_BUTTON)
//        if (wasLastTickMouseDown.value && !lmbDown) onMouseUp()// .ifPresent { obj: Runnable -> obj.run() }
        val lastTickMousePos = this.useState(2, false) { Vertice(Mouse.getX(), Mouse.getY()) }

        val isMouseOver = useState(6, false) { false }
        //TODO the reason this doesnt work is because the key press has an up AND down event, so the first one is getting called with down then the second one is getting called with up, fails and then returns to the beginning.... it sucks... ya
        eventScope {
            chain(onMouseClick) {
                it.key == YakGraphicsUtils.MOUSE_LEFT_BUTTON && it.state
            }.provide {
                System.currentTimeMillis()
            }.next(onMouseClick) { event, data ->
                event.key == YakGraphicsUtils.MOUSE_LEFT_BUTTON && event.state && System.currentTimeMillis() - data <= YakGraphicsUtils.MAX_DOUBLE_CLICK_TIME
            }.ignore {
                it.state
            }.event { doubleClick() }

            chain(onMouseClick) {
                it.state
            }.next(onMouseClick) {
                !it.state
            }.event { mouseClick() }

            chain(onMouseMove) {
                rectBounding(it.x, it.y, x, y, x + width, y + height)
            }.next(onMouseClick).event {
                if (it.state) mouseDown()
                else mouseUp()
            }

            subscribe(onMouseClick) {
                if (it.state) mouseDown
                else mou
            }
//            subscribe(onMouseMove) {
//                isMouseOver.value = rectBounding(it.absoluteX, it.absoluteY, y, x, x + width, y + height)
//
//                if (isMouseOver.value) mouseMove()
//                else mouseOut()
//
////            val lastMousePosition: Vertice = lastTickMousePos.value
////            if (lastMousePosition == Vertice(
////                    Mouse.getX(),
////                    Mouse.getY()
////                ) && isMouseOver.value!!
////            ) onHover()
////            if (lastMousePosition != Vertice(
////                    Mouse.getX(),
////                    Mouse.getY()
////                ) && isMouseOver.value!!
////            ) onMouseMove()
//            }
        }


//        eventScope(1, onMouseClick) { (key, state) ->
//            if (isMouseOver.value && (state && (key == YakGraphicsUtils.MOUSE_LEFT_BUTTON))) mouseDown()// onMouseDown.ifPresent { obj: Runnable -> obj.run() }
//            if (key == YakGraphicsUtils.MOUSE_LEFT_BUTTON && state) {
//                if (System.currentTimeMillis() - lastClick.value <= YakGraphicsUtils.MAX_DOUBLE_CLICK_TIME && isMouseOver.value) {
//                    focused.value = true
//                    doubleClick()// { obj: Runnable -> obj.run() }
//                    lastClick.value = 0L
//                } else {
//                    if (!isMouseOver.value) focused.value = false else {
//                        lastClick.value = System.currentTimeMillis()
//                        focused.value = true
//                        mouseClick()// { obj: Runnable -> obj.run() }
//                    }
//                }
//            }
//        }
//
//        eventScope(2, onKeyboardAction) { (key) ->
//            if (focused.value && key != YakGraphicsUtils.CHAR_NONE) {
//                lastKeyDown.value = key
//                keyDown(key)
//            }
//        }


//        if (isMouseOver) onMouseOver()//.ifPresent { obj: Runnable -> obj.run() }
//        if (isMouseOver && lmbDown) onMouseDown()// onMouseDown.ifPresent { obj: Runnable -> obj.run() }

//        if (rectBounding(
//                ltPos.x,
//                ltPos.y,
//                y,
//                x,
//                x + width,
//                y + height
//            ) && !isMouseOver
//        ) onMouseOut()//.ifPresent { obj: Runnable -> obj.run() }

//        while (Keyboard.next()) {
//            val eventKey: Int = Keyboard.getEventKey()
//            if (focused.value && eventKey != YakGraphicsUtils.CHAR_NONE) lastKeyDown.value = eventKey
//        }
//        val key: Int = lastKeyDown.value
//        if (Keyboard.isKeyDown(key)) {
//            onKeyDown(key)//.ifPresent { consumer -> consumer.accept(key) }
//            wasLastTickKeyDown.value = true
//        } else if (wasLastTickKeyDown.value) {
//            onKeyUp(key)// { consumer -> consumer.accept(key) }
//            wasLastTickKeyDown.value = false
//        }
//        if (isMouseOver) wasLastTickMouseDown.value = lmbDown
//        while (Mouse.next()) {
//            val button: Int = Mouse.getEventButton()
//            if (button == YakGraphicsUtils.MOUSE_LEFT_BUTTON && Mouse.getEventButtonState()) {
//                if (System.currentTimeMillis() - lastClick.value <= YakGraphicsUtils.MAX_DOUBLE_CLICK_TIME && isMouseOver) {
//                    focused.value = true
//                    doubleClick()// { obj: Runnable -> obj.run() }
//                    lastClick.value = 0L
//                } else {
//                    if (!isMouseOver) focused.value = false else {
//                        lastClick.value = System.currentTimeMillis()
//                        focused.value = true
//                        mouseClick()// { obj: Runnable -> obj.run() }
//                    }
//                }
//            }
//        }
//        lastTickMousePos.value = Vertice(Mouse.getX(), Mouse.getY())


        //  Rendering
        val vertices: VerticeAggregation = verticesOf(
            Vertice(x, y),
            Vertice(x + width, y),
            Vertice(x + width, y + height),
            Vertice(x, y + height)
        )

        val colors: ColorFunction =
            backgroundColor ?: VacantColorFunction()// ?:  backgroundColor.or(Supplier<Optional<out ColorFunction>> {

        return ofAll(
            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    vertices, colors.toAggregation(vertices),
                    texs = texsOf(
                        TexNode(0f, 0f),
                        TexNode(1f, 0f),
                        TexNode(0f, 1f),
                        TexNode(1f, 1f),
                    ),
                    texture = texture
                )
            ), this.applyChildren(props)
        )

    }

    private fun rectBounding(x: Int, y: Int, top: Double, left: Double, bottom: Double, right: Double): Boolean {
        return top < y && bottom > y && left < x && right > x
    }
}