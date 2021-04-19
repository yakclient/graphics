package net.yakclient.opengl.gui;

import net.yakclient.opengl.render.GLRenderingContext;
import net.yakclient.opengl.util.state.GUIState;
import net.yakclient.opengl.util.state.Stateful;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The base component for doing any rendering to openGL. This class
 * provides very basic utilities and state keeping.
 * <p>
 * Example BuriedComponent:
 *
 * <p>
 * {@code
 *  public class MyComponent extends BuriedGUIComponent {
 *      GLRenderingContext[] glRender(GUIProperties properties) {
 *          //Work ...
 *          return new GLRenderingContext[] //provide context to render.
 *      }
 *  }
 * }
 * </p>
 *
 * This class extends the GLPropsRenderable interface to provide a rendering
 * contexts. Direct implementations of GLPropsRenderable can be used however
 * it is recommended to use the utilities provided in this class.
 *
 * @see GLPropsRenderable
 * @see GLRenderingContext
 * @see GUIProperties
 *
 * @author Durgan McBroom
 */
public abstract class BuriedGUIComponent implements GLPropsRenderable {
    /**
     * State keeping for this component. Each state is by kept
     * by an integer id. This provides an alternative for using
     * state and keeping it as a member variable.
     */
    private final Map<Integer, Stateful<?>> states;

    /**
     * Constructs a BuriedGUIComponent.
     */
    public BuriedGUIComponent() {
        this.states = new HashMap<>();
    }

    /**
     * Creates a Component factory with the given component.
     *
     * @param component The base component to build from.
     * @param <T> The component type.
     * @return The factory created.
     */
    public final <T extends GLPropsRenderable> ComponentFactory<T> create(T component) {
        return ComponentFactory.create(component);
    }

    /**
     * Executes the following consumer if the given object
     * is not null. This should be mostly used for checks with
     * properties and is a alternative to using
     * BuriedGUIComponent#requireProp(T,T) or
     * BuriedGUIComponent#requireProp(T,Supplier) if you want to
     * run a task based on a non-null value.
     *
     * @param value The value to check against.
     * @param consumer The consumer to consume the value.
     * @param <T> The value type.
     */
    public final <T> void executeIfNotNull(T value, Consumer<T> consumer) {
        if (value != null) consumer.accept(value);
    }

    /**
     * Retrieves a given array of children based on the properties
     * provided. Note, this convenience method will always return a
     * value and cannot return null so it is extremely safe to iterate
     * over. Null children will default to a empty array.
     *
     * @param properties The properties to get children from.
     * @return The children of the properties given.
     */
    @NotNull
    public final ComponentRenderingContext<?>[] getChildren(GUIProperties properties) {
        final List<ComponentRenderingContext<?>> children = properties.get(PropertyFactory.CHILDREN_INDEX);

        return children == null ? new ComponentRenderingContext[0] :
                children.toArray(new ComponentRenderingContext[0]);
    }

    /**
     * Renders all given children in the properties provided.
     *
     * @param properties Properties to render from.
     * @return The contexts returned from rendering.
     */
    public final GLRenderingContext[] renderChildren(GUIProperties properties) {
        final ComponentRenderingContext<?>[] children = this.getChildren(properties);

        final List<GLRenderingContext> childrenRender = new ArrayList<>();
        for (ComponentRenderingContext<?> child : children) {
            childrenRender.addAll(Arrays.asList(child.glRender()));
        }

        return childrenRender.toArray(new GLRenderingContext[0]);
    }

    /**
     * Combines contexts of a array and a singular to a
     * array. This is useful when trying to return rendered
     * children and the context created by the
     * BuriedComponent.
     *
     * @param context The initial context to add.
     * @param all The array of contexts to add to.
     * @return The result of the singular and array.
     */
    public final GLRenderingContext[] combineContexts(GLRenderingContext context, GLRenderingContext[] all) {
        final GLRenderingContext[] out = new GLRenderingContext[all.length + 1];
        out[0] = context;
        System.arraycopy(all, 0, out, 1, all.length);
        return out;
    }

    /**
     *
     * @param initial
     * @param key
     * @param <T>
     * @return
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public final <T> Stateful<T> useState(T initial, int key) {
        this.states.putIfAbsent(key, new GUIState<>(initial));
        return (Stateful<T>) this.states.get(key);
    }

    public final <T> Stateful<T> useState(int key) {
        return this.useState(null, key);
    }

    @NotNull
    public final <T> T requireProp(@Nullable T value) {
        if (value == null) throw new IllegalPropertyException("You have null properties that are required!");
        return value;
    }

    @NotNull
    public final <T> T requestProp(@Nullable T property, @NotNull T defaultVal) {
        return property == null ? defaultVal : property;
    }

    @NotNull
    public final <T> T requestProp(@Nullable T property, @NotNull Supplier<T> defaultVal) {
        return property == null ? defaultVal.get() : property;
    }

    /**
     * Returns a PropertyManager based on the {@code GUIProperties}
     * given. Usually if you have 1-2 property's it will make more
     * sense to use the given basic methods for property management
     * given in the super class ({@code BuriedGUIComponent}).
     *
     * @param properties The properties to manage.
     * @return The manager for the given properties.
     * @see GUIProperties
     * @see PropertyManager
     */
    public PropertyManager manageProps(GUIProperties properties) {
        return new PropertyManager(properties);
    }

    /**
     * This class provides general utilities for dealing with
     * properties. It is focused on speed and will try to perform
     * the given operations as fast as possible to provide
     * good rendering times.
     * <p>
     * An example:
     * <p>
     * {@code
     * final PropertyManager manager = this.manageProps(props) //Assuming properties are provided in the render method
     * final int x = manager.requireProp("x-coord"); //Will through a exception if the property is not specified
     * final byte color = manager.requestProp("color", 0x0100); //Will substitute the second parameter in for the default value if the prop is null
     * final String name = manager.requestProp("name", ()->"My name"); //Will use the supplier to do the same as PropertyManager#requireProp(String)
     * <p>
     * //Your component...
     * <p>
     * }
     *
     * </p>
     *
     * @author Durgan McBroom
     */
    protected static class PropertyManager {
        private final GUIProperties properties;

        private PropertyManager(GUIProperties properties) {
            this.properties = properties;
        }

        public <T> T requireProp(String name) {
            final T prop = this.properties.get(name);
            if (prop == null)
                throw new IllegalPropertyException("Failed to provide property: '" + name + "' in component: '" + this.getClass().getName() + "'");
            return prop;
        }

        public <T> T requireProp(int index) {
            final T prop = this.properties.get(index);
            if (prop == null)
                throw new IllegalPropertyException("Failed to provide property: '" + index + "' in component: '" + this.getClass().getName() + "'");
            return prop;
        }

        public <T> Optional<T> requestProp(String name) {
            return Optional.ofNullable(this.properties.get(name));
        }

//        public <T> T requestProp(String name, Supplier<T> defaultVal) {
//            return this.requestProp(this.properties.<T>get(name), defaultVal);
//        }

        public <T> Optional<T> requestProp(int index) {
            return Optional.ofNullable(this.properties.get(index));
        }

//        public <T> T requestProp(int index, T defaultVal) {
//            return this.requestProp(this.properties.<T>get(index), () -> defaultVal);
//        }
//
//        private <T> Opt requestProp(T prop) {
//            return prop == null ? defaultVal.get() : prop;
//        }
    }
}
