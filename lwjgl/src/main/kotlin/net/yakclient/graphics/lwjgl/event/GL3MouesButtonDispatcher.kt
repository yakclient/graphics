package net.yakclient.graphics.lwjgl.event

import net.yakclient.graphics.api.event.MouseActionData
import net.yakclient.graphics.api.event.MouseButtonEventDispatcher
import org.lwjgl.glfw.GLFW

public class GL3MouesButtonDispatcher : MouseButtonEventDispatcher() {
    init {
        GLFW.glfwSetMouseButtonCallback(GLFW.glfwGetCurrentContext()) { _, button, action, _ ->
            dispatch(MouseActionData(button, action != GLFW.GLFW_RELEASE))
        }
    }
}