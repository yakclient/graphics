package net.yakclient.opengl.gui;

import net.yakclient.opengl.render.GLRenderingContext;

public class ComponentRenderingContext<T extends GLPropsRenderable> implements GLRenderable {
    private final T component;
    private final GUIProperties properties;

    public ComponentRenderingContext(T component, GUIProperties properties) {
        this.component = component;
        this.properties = properties;
    }

    @Override
//    @SuppressWarnings("ConstantConditions")
    public GLRenderingContext[] glRender() {
       return this.component.glRender(this.properties);
//        if (this.component instanceof ScreenRenderer) this.component.glRender(this.properties);
//        if (this.properties.hasProperty(PropertyFactory.CHILDREN_INDEX)) {
//            for (ComponentRenderingContext<?> child :
//                    this.properties.<List<ComponentRenderingContext<?>>>get(PropertyFactory.CHILDREN_INDEX)) {
//                child.glRender();
//            }
//        }
    }
}