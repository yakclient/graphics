package net.yakclient.graphics.opengl3.components

import net.yakclient.graphics.api.DeferredComponent
import net.yakclient.graphics.api.DeferredComponentProvider
import net.yakclient.graphics.api.provideWith

public class OpenGL3ComponentProvider : DeferredComponentProvider {
    override fun <T: DeferredComponent> providesWith(type: Class<T>): Class<out T>? = provideWith(type, OpenGl3Box::class, OpenGL3Text::class)
}