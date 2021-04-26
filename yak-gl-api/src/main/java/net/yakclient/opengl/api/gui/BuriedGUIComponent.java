package net.yakclient.opengl.api.gui;

import net.yakclient.opengl.api.gui.state.GUIState;
import net.yakclient.opengl.api.gui.state.Stateful;
import net.yakclient.opengl.api.render.GLRenderingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

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
 * @see ContextPropsRenderable
 * @see GUIProperties
 *
 * @author Durgan McBroom
 */
public abstract class BuriedGUIComponent implements ContextPropsRenderable {
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
    public final <T extends ContextPropsRenderable> ComponentFactory<T> create(T component) {
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
        final List<ComponentRenderingContext<?>> children = properties.get(PropertyFactory.CHILD_NAME);

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
     * Returns and stores a state for the given object, note
     * this is not necessary and you can instead just use member
     * variables. However state provides the best possible way
     * to update values from passed lambdas(either in props or
     * just in general)
     *
     * Example:
     * <p>
     *     {@code
     *      final Stateful<String> name = this.useState("My name is Bob!", 0);
     *      final Stateful<Boolean> nameSet = this.useState(false, 1);
     *
     *      //... component rendering
     *      .addProp("onHover", ()->name.set("No my name is actually Jerry"));
     *      .addProp("onFocus", ()-> name.setIf(nameSet.get(), "Since we've hovered my name is Bob again"));
     *     }
     * </p>
     *
     * The one draw back of this method is that state has to be stored
     * by ID due to speed and the fact that it would be extremely hard
     * to try to figure out which state is which on re-renders.
     *
     * @param initial The initial value of the state.
     * @param key The key to identify the given state by.
     * @param <T> The state type.
     * @return the computed and stored state.
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
    public final <T> Optional<T> requestProp(@Nullable T property) {
        return Optional.ofNullable(property);
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
     * final byte color = manager.requestProp("color").or(Optional.of(0x01)).get(); //Will return an optional of the value which then you can or and get to provide a default
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

        @NotNull
        public <T> T requireProp(String name) {
            final T prop = this.properties.get(name);
            if (prop == null)
                throw new IllegalPropertyException("Failed to provide property: '" + name + "' in component: '" + this.getClass().getName() + "'");
            return prop;
        }

        @NotNull
        public <T> Optional<T> requestProp(String name) {
            return Optional.ofNullable(this.properties.get(name));
        }
    }
}
