package net.yakclient.graphics.lwjgl.event

import net.yakclient.graphics.api.event.KeyboardActionData
import net.yakclient.graphics.api.event.KeyboardActionDispatcher
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

public class LwjglKeyboardActionDispatcher : KeyboardActionDispatcher() {
    init {
        GLFW.glfwSetKeyCallback(GLFW.glfwGetCurrentContext()) { _, key, _, action, _ ->
            dispatch(KeyboardActionData(key, action != GLFW_RELEASE))
        }
    }
}