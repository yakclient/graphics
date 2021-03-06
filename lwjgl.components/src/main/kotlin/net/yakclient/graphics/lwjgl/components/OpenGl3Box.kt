package net.yakclient.graphics.lwjgl.components

import net.yakclient.event.api.EventData
import net.yakclient.event.api.fsm.*
import net.yakclient.graphics.api.GuiPropertiesMap
import net.yakclient.graphics.api.event.*
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.components.Box
import net.yakclient.graphics.lwjgl.render.GLRenderingData
import net.yakclient.graphics.lwjgl.render.VerticeRenderingContext
import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.buffer.safeFloatBufOf
import org.lwjgl.opengl.GL11
import java.util.function.Consumer

public class OpenGl3Box : Box() {
    override fun renderNatively(props: GuiPropertiesMap): List<RenderingContext> {
        val width = props.requireAs<Float>("width")
        val height = props.requireAs<Float>("height")

        val x = props.requireAs<Float>("x")
        val y = props.requireAs<Float>("y")

        //  Transparency is defined in the background color.
        val backgroundColor = props.getAs<ColorFunction>("backgroundcolor") ?: ColorCodes.WHITE.toColorFunc()
        val tex = props.getAs<Texture>("backgroundimage") ?: VacantTexture()

        //  Events
        val mouseClick = props.getAs<Runnable>("onclick") ?: Runnable {}
        val doubleClick = props.getAs<Runnable>("ondbclick") ?: Runnable {}
        val mouseDown = props.getAs<Runnable>("onmousedown") ?: Runnable {}
        val mouseUp = props.getAs<Runnable>("onmouseup") ?: Runnable {}
        val mouseOver = props.getAs<Runnable>("onmouseover") ?: Runnable {}
        val mouseMove = props.getAs<Runnable>("onmousemove") ?: Runnable {}
        val mouseOut = props.getAs<Runnable>("onmouseout") ?: Runnable {}
        val keyDown = props.getAs<Consumer<Int>>("onkeydown") ?: Consumer<Int> {}
        val keyUp = props.getAs<Consumer<Int>>("onkeyup") ?: Consumer<Int> {}
        //TODO val hover = props.getAs<Runnable>("onhover") ?: Runnable {}

        eventScope {
            fun MutableEventFSM.TransitionProvider.withBounding(event: Runnable = Runnable {}) =
                with<MouseMoveData> {
//                    println("x: ${it.x} y: ${it.y} x1: $x y1: $y x2: ${x + width} y2: ${y + height}")
                    val rectBounding = rectBounding(it.x, it.y, x, y, x + width, y + height)
                    if (rectBounding) event.run()
                    rectBounding
//                    rectBounding.also { b -> if (b) event.run() }
                }

            fun MutableEventFSM.TransitionProvider.withNotBounding(event: Runnable = Runnable {}) =
                with<MouseMoveData> {
                    (!rectBounding(it.x, it.y, x, y, x + width, y + height)).also { b -> if (b) event.run() }
                }

            // Mouse click, double click, mouse down, mouse up, mouse over, mouse move and mouse out.
            useFSM {
                // Initial state
                val initial = of("Initial")
                // State representing that the mouse is currently bounding within this box
                val inBox = of("Mouse in box")
                // State representing that the mouse is in the up position after being in the down position. This state times out after 500ms
                val up = timingOutOf(inBox, GraphicsUtils.MAX_DOUBLE_CLICK_TIME.toLong(), "Mouse up in box")
                // State representing that the mouse is bounding within the box and is in a down position
                val down = of(object : DelegatingEventState("Mouse down in box") {
                    // The transition for moving back to the "mouse in box" state
                    private val to_inBox: Transition = transition(inBox).with()

                    // The transition representing that the mouse has gone into an up position
                    private val to_mouseUp: Transition = eventTransition(up) { mouseClick(); mouseUp() }

                    // The transition representing that the mouse has moved outside the box and goes back into the initial position
                    private val to_initial: Transition = transition(initial).with<MouseMoveData> { e ->
                        (!rectBounding(
                            e.x,
                            e.y,
                            x,
                            y,
                            x + width,
                            y + height
                        )).also { if (it) mouseOut() }
                    }

                    // Custom logic for delegating to transitions
                    override fun <T : EventData> find(event: T): Transition? = when (event) {
                        is MouseActionData -> (if (!event.state && event.key == GraphicsUtils.MOUSE_LEFT_BUTTON) to_mouseUp else to_inBox)
                        is MouseMoveData -> to_initial
                        else -> null
                    }
                })

                // The transition for moving to inBox when the mouse is bounding with this box
                (initial transitionsTo inBox).withBounding(mouseOver)
                // The transition for moving to "mouse down" when the left mouse button has been clicked(mouse down)
                (inBox transitionsTo down).with<MouseActionData> { e ->
                    // Checks if the mouse action data and calls events accordingly
                    (e.key == GraphicsUtils.MOUSE_LEFT_BUTTON).also {
                        if (it && e.state) mouseDown() else if (it) mouseUp()
                    }.let { it && e.state }
                }
                // The transition for moving from the up state to inBox after a double click(or timing out)
                (up transitionsTo inBox).with<MouseActionData> { e -> (e.state && e.key == GraphicsUtils.MOUSE_LEFT_BUTTON).also { if (it) doubleClick() } }
                // Transition for moving from the inBox state to the initial if the mouse moves outside the box
                (inBox transitionsTo initial).with<MouseMoveData> {
                    // Checks if the mouse is bounding, then calls events accordingly
                    (!rectBounding(it.x, it.y, x, y, x + width, y + height)).also { b ->
                        if (b) mouseOut()
                        else mouseMove()
                    }
                }
                // Transition for moving to initial from up if the mouse is no longer bounding
                (up transitionsTo initial).withNotBounding(mouseOut)
            }

            // Handles key up and down
            useFSM {
                val initial = of("Initial")
                val inBox = of("In box")
                val clicked = of("Clicked in box")

                // Happy path
                (initial transitionsTo inBox).withBounding()
                (inBox transitionsTo clicked).with<MouseActionData> { it.key == GraphicsUtils.MOUSE_LEFT_BUTTON && it.state }
                (clicked transitionsTo clicked).with<KeyboardActionData> {
                    if (it.state) keyUp(it.key)
                    else keyDown(it.key)
                    false // Doesn't matter, just less work for the FSM to do if no state transitions have to happen
                }

                // Failure path
                (inBox transitionsTo initial).withNotBounding()
                (clicked transitionsTo inBox).with<MouseActionData> { it.key != GraphicsUtils.MOUSE_LEFT_BUTTON }
                (clicked transitionsTo initial).withNotBounding()
            }

            require(onMouseMove, onMouseClick, onKeyboardAction)
        }


        //  Rendering
        val vertices = safeFloatBufOf(8)
            .put(x).put(y)
            .put(x + width).put(y)
            .put(x + width).put(y + height)
            .put(x).put(y + height)

        val hasTexture = tex !is VacantTexture
        return ofAll(
            VerticeRenderingContext(
                GL11.GL_QUADS,
                GL11.GL_TEXTURE_2D,
                GLRenderingData(
                    vertices, 2, backgroundColor.toAggregation(vertices, 2),
                    texs = if (hasTexture) tex.texToBuf() else safeFloatBufOf(),
                    texStride = if (hasTexture) 2 else 0,
                    texture = tex
                )
            ), this.applyChildren(props)
        )

    }

    private fun rectBounding(x: Float, y: Float, x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        return x in x1..x2 && y in y1..y2
    }
}