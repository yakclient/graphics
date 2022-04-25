package net.yakclient.graphics.lwjgl.legacy.components

import net.yakclient.graphics.api.DeferredComponent
import net.yakclient.graphics.api.DeferredComponentProvider
import net.yakclient.graphics.api.provideWith

public class OpenGL2ComponentProvider : DeferredComponentProvider {
    override fun <T: DeferredComponent> providesWith(type: Class<T>): Class<out T>? = provideWith(type, OpenGL2Box::class, OpenGL2Text::class)
}