package net.yakclient.opengl.api.gui;

import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.api.render.VerticeRenderingContext;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public abstract class GUIComponent extends BuriedGUIComponent {
    private final Map<Integer, BuriedGUIComponent> renderedComponents;

    public GUIComponent() {
        this.renderedComponents = new HashMap<>();
    }

    @Contract(pure = true)
    public abstract ComponentRenderingContext<?> render(GUIProperties properties);

    @Override
    @Contract(pure = true)
    public final GLRenderingContext[] glRender(GUIProperties properties) {
        return this.render(properties).glRender();
    }

    @SuppressWarnings("unchecked")
    public <T extends BuriedGUIComponent> T useComponent(T component, int id) {
        if (!this.renderedComponents.containsKey(id)) this.renderedComponents.put(id, component);
        if (!component.getClass().isAssignableFrom(this.renderedComponents.get(id).getClass()))
            throw new IllegalPropertyException("Given component has a collision of id's with: " + component);
        return (T) this.renderedComponents.get(id);
    }


}