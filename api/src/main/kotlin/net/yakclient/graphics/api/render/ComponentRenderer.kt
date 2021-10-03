package net.yakclient.graphics.api.render

import net.yakclient.graphics.api.gui.ComponentRenderingContext

public class ComponentRenderer(
    override val context: ComponentRenderingContext<*>,
    private val propagatingType: RenderingType,
) : Renderer<ComponentRenderingContext<*>> {
    override fun render(): Unit = context.applyContext().forEach { it.useRenderer(propagatingType).render() }
}