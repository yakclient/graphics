package net.yakclient.graphics.api

import net.yakclient.graphics.api.render.*

public class ComponentRenderingContext<in T : NativeGuiComponent>(
    private val component: T,
    private val properties: GuiPropertiesMap,
) : RenderingContext {
    override var needsReRender: Boolean by component::needsReRender

    internal fun applyContext() : List<RenderingContext> = component.renderNatively(properties)

    override fun useRenderer(type: RenderingType): Renderer<ComponentRenderingContext<*>> = ComponentRenderer(type)
}