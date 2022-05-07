package net.yakclient.graphics.lwjgl.event

import net.yakclient.graphics.api.event.MouseMoveData
import net.yakclient.graphics.api.event.MouseMoveDispatcher
import net.yakclient.graphics.util.unit.px
import net.yakclient.graphics.util.use
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryStack.stackPush

public class GL3MouseMoveDispatcher : MouseMoveDispatcher() {
    init {
        var lastX = 0
        var lastY = 0

        stackPush().use {
            val x = it.mallocDouble(1)
            val y = it.mallocDouble(1)

            GLFW.glfwGetCursorPos(GLFW.glfwGetCurrentContext(), x, y)

            lastX = x.get(0).toInt()
            lastY = y.get(0).toInt()
        }

        GLFW.glfwSetCursorPosCallback(GLFW.glfwGetCurrentContext()) { _, xd, yd ->
            val x = xd.toInt()
            val y = yd.toInt()

            val e = MouseMoveData(x.toFloat(), y.toFloat(), (x - lastX).toFloat(), (y - lastY).toFloat())

            lastX = x
            lastY = y

            dispatch(e)
        }
    }
}