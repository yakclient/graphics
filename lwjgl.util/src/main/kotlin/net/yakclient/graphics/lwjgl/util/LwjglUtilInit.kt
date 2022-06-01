package net.yakclient.graphics.lwjgl.util

import net.yakclient.graphics.lwjgl.util.buffer.LwjglSafeFloatBufferProvider
import net.yakclient.graphics.util.ScreenAccess
import net.yakclient.graphics.util.TextureFactory
import net.yakclient.graphics.util.buffer.safeFloatBufferProvider

private var initialized: Boolean = false

public fun initLwjglUtil() {
    if (initialized) return
    initialized = true

    safeFloatBufferProvider = LwjglSafeFloatBufferProvider()
    ScreenAccess.access = LwjglScreenAccess()
    TextureFactory.provider = LwjglTextureProvider()
}