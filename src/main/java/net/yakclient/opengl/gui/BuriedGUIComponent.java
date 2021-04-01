package net.yakclient.opengl.gui;

import java.util.List;
import java.util.function.Consumer;

public abstract class BuriedGUIComponent implements GLPropsRenderable {
    protected boolean needsReRender = false;
    protected ComponentRenderingContext<?> lastRender;


    public final <T extends GLPropsRenderable> ComponentFactory<T> create(T component) {
        return ComponentFactory.create(component);
    }

    public final <T> void executeIfNotNull(T obj, Consumer<T> consumer) {
        if (obj != null) consumer.accept(obj);
    }

    public final ComponentRenderingContext<?>[] getChildren(GUIProperties properties) {
        final List<ComponentRenderingContext<?>> children = properties.get(PropertyFactory.CHILDREN_INDEX);

        return children == null ? new ComponentRenderingContext[0] :
                children.toArray(new ComponentRenderingContext[0]);
    }

    public final void renderChildren(GUIProperties properties) {
        final List<ComponentRenderingContext<?>> children = properties.get(PropertyFactory.CHILDREN_INDEX);
        this.executeIfNotNull(children, (nnChildren) -> {
            for (ComponentRenderingContext<?> child : nnChildren) child.glRender();
        });
    }

    public void triggerRender() {
        this.needsReRender = true;
    }
}
