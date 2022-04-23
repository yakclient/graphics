package net.yakclient.graphics.opengl3.event

import net.yakclient.graphics.api.event.KeyboardActionData
import net.yakclient.graphics.api.event.KeyboardActionDispatcher
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

public class GL3KeyboardActionDispatcher : KeyboardActionDispatcher() {
    init {
        GLFW.glfwSetKeyCallback(GLFW.glfwGetCurrentContext()) { _, key, _, action, _ ->
            dispatch(KeyboardActionData(key, action != GLFW_RELEASE))
        }
    }
}