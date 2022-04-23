package net.yakclient.graphics.api.render

import net.yakclient.graphics.api.ComponentRenderingContext

public class ComponentRenderer(
    private val propagatingType: RenderingType,
) : Renderer<ComponentRenderingContext<*>> {
    override fun render(context: ComponentRenderingContext<*>): Unit = context.applyContext().forEach { (it.renderUsingDefault(propagatingType)) }
}