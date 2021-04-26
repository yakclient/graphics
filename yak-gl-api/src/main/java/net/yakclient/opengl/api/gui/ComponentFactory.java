package net.yakclient.opengl.api.gui;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class ComponentFactory<T extends ContextPropsRenderable> {
    private final T component;
    private final PropertyFactory properties;

    public ComponentFactory(T component, PropertyFactory properties) {
        this.component = component;
        this.properties = properties;
    }

    public static <T extends ContextPropsRenderable> ComponentFactory<T> create(T component) {
        return new ComponentFactory<>(component, PropertyFactory.create());
    }

    public <V> ComponentFactory<T> addProp(String name, V value) {
        return this.addProp(name, true, value);
    }

//    public ComponentFactory<T> addProp(String name, int id, Object value) {
//        this.properties.addProp(name, id, value);
//        return this;
//    }

    public ComponentFactory<T> addProp(String name, Boolean condition, Object value) {
       if (condition != null && condition) this.properties.addProp(name, value);
        return this;
    }

    public ComponentFactory<T> addProp(String name, Boolean condition, Supplier<?> value) {
        if (condition != null && condition) this.properties.addProp(name, value.get());
        return this;
    }

//    public ComponentFactory<T> addProp(String name, int id, Object value, boolean condition) {
//       if (condition) this.properties.addProp(name, id, value);
//        return this;
//    }

    public ComponentFactory<T> addChild(ComponentFactory<?> factory) {
        this.properties.addChild(factory.build());
        return this;
    }

    public ComponentFactory<T> addChild(ComponentFactory<?> factory, Boolean condition) {
        if (condition != null && condition) this.addChild(factory);
        return this;
    }

    public ComponentRenderingContext<T> build() {
        return new ComponentRenderingContext<>(this.component, this.properties.build());
    }


}
