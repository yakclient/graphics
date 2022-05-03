package net.yakclient.graphics.lwjgl.util

import net.yakclient.graphics.util.ScreenAccess
import net.yakclient.graphics.util.use
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryStack.stackPush

public class GL3ScreenAccess : ScreenAccess {
    private var _width: Int = 0
    private var _height: Int = 0

    override val width: Int
        get() = _width
    override val height: Int
        get() = _height

    init {
        stackPush().use { stack ->
            val width = stack.mallocInt(1)
            val height = stack.mallocInt(1)

            GLFW.glfwGetWindowPos(
                GLFW.glfwGetCurrentContext(),
                width,
                height
            )

            _width = width[0]
            _height = height[0]
        }

        GLFW.glfwSetWindowSizeCallback(GLFW.glfwGetCurrentContext()) { _, width, height ->
            _width = width
            _height = height
        }
    }


}