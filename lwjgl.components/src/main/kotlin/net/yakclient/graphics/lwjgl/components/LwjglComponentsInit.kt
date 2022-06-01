@file:JvmName("LwjglComponentsInit")

package net.yakclient.graphics.lwjgl.components

import net.yakclient.graphics.api.DeferredComponentLoader
import net.yakclient.graphics.lwjgl.initLwjgl

private var initialized : Boolean = false

public fun initLwjglComponents() {
    if (initialized) return
    initialized = true

    DeferredComponentLoader.add(OpenGL3ComponentProvider())
}

