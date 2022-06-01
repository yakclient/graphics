@file:JvmName("LwjglInit")

package net.yakclient.graphics.lwjgl

import net.yakclient.event.api.EventDispatchManager
import net.yakclient.graphics.lwjgl.event.LwjglKeyboardActionDispatcher
import net.yakclient.graphics.lwjgl.event.LwjglMouesButtonDispatcher
import net.yakclient.graphics.lwjgl.event.LwjglMouseMoveDispatcher

private var initialized : Boolean = false

public fun initLwjgl() {
    if (initialized) return
    initialized = true

    EventDispatchManager.add(LwjglKeyboardActionDispatcher())
    EventDispatchManager.add(LwjglMouseMoveDispatcher())
    EventDispatchManager.add(LwjglMouesButtonDispatcher())
}

