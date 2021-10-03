package net.yakclient.graphics.api.gui

import net.yakclient.graphics.api.render.ComponentRenderer
import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.render.RenderingType

public class ComponentRenderingContext<in T : NativeGuiComponent>(
    private val component: T,
    private val properties: GuiProperties
) : RenderingContext {
    internal fun applyContext() : List<RenderingContext> = component.renderNatively(properties)

    override fun useRenderer(type: RenderingType): Renderer<ComponentRenderingContext<*>> = ComponentRenderer(this, type)
}