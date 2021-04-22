package net.yakclient.opengl.api.gui;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyFactory {
    public static final String CHILD_NAME = "children";

    private final Map<String, Object> props;

    private PropertyFactory() {
        this.props = new HashMap<>();
    }

    public static PropertyFactory create() {
        return new PropertyFactory().innit();
    }

    private PropertyFactory innit() {
        this.props.put(CHILD_NAME, new ArrayList<>());
        return this;
    }

    public PropertyFactory addProp(String name, Object value) throws IllegalPropertyException {
        if (name.equalsIgnoreCase(CHILD_NAME))
            throw new IllegalPropertyException("The name " + CHILD_NAME + " is reserved for children of components!");

        this.props.put(name, value);
//        for (String node : props.keySet()) {
//            if (node.getId() == id)
//                throw new IllegalPropertyException(name, "You have a duplicate ID of: " + id);
//            else if (node.getName().equalsIgnoreCase(name))
//                throw new IllegalPropertyException(name, "You have a duplicate name of: " + name);
//        }

//        this.props.put(new GUIProperties.PropertyNode(name, id), value);
        return this;
    }

    public PropertyFactory addChild(ComponentRenderingContext<?> component) {
        this.retrieveChildren().add(component);
        return this;
    }

    @SuppressWarnings("unchecked")
    private List<ComponentRenderingContext<?>> retrieveChildren() {
//        final GUIProperties.PropertyNode node = new GUIProperties.PropertyNode(CHILD_NAME, CHILDREN_INDEX);
//        if (!this.props.containsKey(node)) this.props.put(node, new ArrayList<ComponentRenderingContext<?>>());
//        final Object children = this.props.get(node);
//        if (!(children instanceof List)) throw new IllegalPropertyException("Children type MUST be a list!");
        final Object children = this.props.get(CHILD_NAME);
        return (List<ComponentRenderingContext<?>>) children;
    }

    @Contract(pure = true)
    public GUIProperties build() {
        return new GUIProperties(this.props);
    }
}
