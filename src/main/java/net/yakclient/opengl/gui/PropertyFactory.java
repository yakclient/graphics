package net.yakclient.opengl.gui;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyFactory {
    public static final int CHILDREN_INDEX = 0;
    public static final String CHILD_NAME = "children";

    public static final int PARENT_INDEX = 1;
    public static final String PARENT_NAME = "parent";

    private final Map<GUIProperties.PropertyNode, Object> props;
    private int latestID;

    private PropertyFactory() {
        this.props = new HashMap<>();
        this.latestID = PARENT_INDEX;
    }

    public static PropertyFactory create() {
        return new PropertyFactory().innit();
    }

    private PropertyFactory innit() {
        this.props.put(new GUIProperties.PropertyNode(CHILD_NAME, CHILDREN_INDEX), new ArrayList<>());
        return this;
    }

    public PropertyFactory addProp(String name, Object value) throws IllegalPropertyException {
        return this.addProp(name, ++latestID, value);
    }


    private PropertyFactory addProp(String name, int id, Object value) throws IllegalPropertyException {
        if (id == CHILDREN_INDEX)
            throw new IllegalPropertyException("the ID of " + CHILDREN_INDEX +" is reserved for children, please use another ID!");
        if (name.equalsIgnoreCase(CHILD_NAME))
            throw new IllegalPropertyException("The name " + CHILD_NAME + " is reserved for children of components!");

        if (id == PARENT_INDEX)
            throw new IllegalPropertyException("the ID of " + PARENT_INDEX +" is reserved for the parent, please use another ID!");
        if (name.equalsIgnoreCase(PARENT_NAME))
            throw new IllegalPropertyException("The name " + PARENT_NAME + " is reserved for the parent of that component!");

        for (GUIProperties.PropertyNode node : props.keySet()) {
            if (node.getId() == id)
                throw new IllegalPropertyException(name, "You have a duplicate ID of: " + id);
            else if (node.getName().equalsIgnoreCase(name))
                throw new IllegalPropertyException(name, "You have a duplicate name of: " + name);
        }

        this.props.put(new GUIProperties.PropertyNode(name, id), value);
        return this;
    }

    public PropertyFactory addChild(ComponentRenderingContext<?> component) {
        this.retrieveChildren().add(component);
        return this;
    }

    @SuppressWarnings("unchecked")
    private List<ComponentRenderingContext<?>> retrieveChildren() {
        final GUIProperties.PropertyNode node = new GUIProperties.PropertyNode(CHILD_NAME, CHILDREN_INDEX);
        if (!this.props.containsKey(node)) this.props.put(node, new ArrayList<ComponentRenderingContext<?>>());
        final Object children = this.props.get(node);
        if (!(children instanceof List)) throw new IllegalPropertyException("Children type MUST be a list!");
        return (List<ComponentRenderingContext<?>>) children;
    }

//    public

    @Contract(pure = true)
    public GUIProperties build() {
        return new GUIProperties(this.props);
    }
}
