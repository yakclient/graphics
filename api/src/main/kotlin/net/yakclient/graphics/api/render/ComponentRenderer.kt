package net.yakclient.graphics.api.render

import net.yakclient.graphics.api.gui.ComponentRenderingContext

public class ComponentRenderer(
    context: ComponentRenderingContext<*>,
    private val propagatingType: RenderingType?
) : Renderer<ComponentRenderingContext<*>>(context) {
    override fun render() {
        for (c in context.applyContext()) {
             (if (propagatingType == null) c.useRenderer() else c.useRenderer(propagatingType)).render()
        }
//        return component!!.glRender(properties)
//                if (this.component instanceof ScreenRenderer) this.component.glRender(this.properties);
//        if (this.properties.hasProperty(PropertyFactory.CHILDREN_INDEX)) {
//            for (ComponentRenderingContext<?> child :
//                    this.properties.<List<ComponentRenderingContext<?>>>get(PropertyFactory.CHILDREN_INDEX)) {
//                child.glRender();
//            }
//        }
    }
}