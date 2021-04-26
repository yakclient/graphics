package net.yakclient.opengl.api.gui;

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
        return this.addProp(name, value, true);
    }

//    public ComponentFactory<T> addProp(String name, int id, Object value) {
//        this.properties.addProp(name, id, value);
//        return this;
//    }

    public ComponentFactory<T> addProp(String name, Object value, Boolean condition) {
       if (condition != null && condition) this.properties.addProp(name, value);
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
