package net.yakclient.opengl.gui;

import java.util.HashMap;
import java.util.Map;

public abstract class GUIComponent extends BuriedGUIComponent {
    private final Map<Integer, BuriedGUIComponent> renderedComponents;
//    private boolean hasRendered = false;

    public GUIComponent() {
        this.renderedComponents = new HashMap<>();
    }

    public abstract ComponentRenderingContext<?> render(GUIProperties properties);

    @Override
    public final void glRender(GUIProperties properties) {
//        this.hasRendered = true;
        this.needsReRender = false;
        (this.lastRender = this.render(properties)).glRender();
    }

    @SuppressWarnings("unchecked")
    public <T extends BuriedGUIComponent> T useComponent(T component, int id) {
        if (!this.renderedComponents.containsKey(id)) this.renderedComponents.put(id, component);
        if (!component.getClass().isAssignableFrom(this.renderedComponents.get(id).getClass()))
            throw new IllegalPropertyException("Given component has a collision of id's with: " + component);
        return (T) this.renderedComponents.get(id);
    }
}