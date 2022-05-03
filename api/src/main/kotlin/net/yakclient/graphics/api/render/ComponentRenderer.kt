package net.yakclient.graphics.api.render

import net.yakclient.graphics.api.ComponentRenderingContext

@Deprecated("Please break down component and render separately instead!")
public class ComponentRenderer(
    private val propagatingType: RenderingType,
) : Renderer<ComponentRenderingContext<*>> {
    override fun render(context: ComponentRenderingContext<*>): Unit = context.reduce().forEach { (it.renderUsingDefault(propagatingType)) }
}