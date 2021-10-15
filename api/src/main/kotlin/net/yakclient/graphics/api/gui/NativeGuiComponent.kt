package net.yakclient.graphics.api.gui

import net.yakclient.graphics.api.gui.state.ObservableState
import net.yakclient.graphics.api.gui.state.Stateful
import net.yakclient.graphics.api.render.RenderingContext
import java.util.*
import kotlin.collections.ArrayList

/**
 * The base component for doing any rendering to openGL. This class
 * provides very basic utilities and state keeping.
 *
 *
 * Example BuriedComponent:
 *
 *
 *
 * `public class MyComponent extends BuriedGUIComponent {
 * GLRenderingContext[] glRender(GUIProperties properties) {
 * //Work ...
 * return new GLRenderingContext[] //provide context to render.
 * }
 * }
` *
 *
 *
 * This class extends the GLPropsRenderable interface to provide a rendering
 * contexts. Direct implementations of GLPropsRenderable can be used however
 * it is recommended to use the utilities provided in this class.
 *
 * @see ContextPropsRenderable
 *
 * @see GuiProperties
 *
 *
 * @author Durgan McBroom
 */
public abstract class NativeGuiComponent {
    private val states: MutableMap<Int, Stateful<*>> = HashMap()
    internal var needsReRender: Boolean = true

    public abstract fun renderNatively(props: GuiProperties): List<RenderingContext>
//    /**
//     * Creates a Component factory with the given component.
//     *
//     * @param component The base component to build from.
//     * @param <T> The component type.
//     * @return The factory created.
//    </T> */
//    public fun <T : ContextPropsRenderable?> create(component: T): ComponentFactory<T> {
//        return ComponentFactory.create<T>(component)
//    }

//    /**
//     * Executes the following consumer if the given object
//     * is not null. This should be mostly used for checks with
//     * properties and is a alternative to using
//     * BuriedGUIComponent#requireProp(T,T) or
//     * BuriedGUIComponent#requireProp(T,Supplier) if you want to
//     * run a task based on a non-null value.
//     *
//     * @param value The value to check against.
//     * @param consumer The consumer to consume the value.
//     * @param <T> The value type.
//    </T> */
//    public fun <T> executeIfNotNull(value: T?, consumer: Consumer<T>) {
//        if (value != null) consumer.accept(value)
//    }

//    /**
//     * Retrieves a given array of children based on the properties
//     * provided. Note, this convenience method will always return a
//     * value and cannot return null so it is extremely safe to iterate
//     * over. Null children will default to a empty array.
//     *
//     * @param properties The properties to get children from.
//     * @return The children of the properties given.
//     */
//    public fun getChildren(properties: GUIProperties): Array<ComponentRenderingContext<*>> {
//        val children = properties.get<List<ComponentRenderingContext<*>>>(PropertyFactory.Companion.CHILD_NAME)
//        return children?.toTypedArray() ?: arrayOfNulls(0)
//    }

//    /**
//     * Renders all given children in the properties provided.
//     *
//     * @param properties Properties to render from.
//     * @return The contexts returned from rendering.
//     */
//    public fun renderChildren(properties: GUIProperties): Array<GLRenderingContext?> {
//        val children = getChildren(properties)
//        val childrenRender: MutableList<GLRenderingContext?> = ArrayList()
//        for (child in children) {
//            childrenRender.addAll(Arrays.asList(*child.glRender()))
//        }
//        return childrenRender.toTypedArray()
//    }

    public fun applyChildren(properties: GuiProperties): List<RenderingContext> =
        properties.getAs<List<ComponentRenderingContext<*>>>(CHILD_NAME) ?: ArrayList()

    //    /**
//     * Combines contexts of a array and a singular to a
//     * array. This is useful when trying to return rendered
//     * children and the context created by the
//     * BuriedComponent.
//     *
//     * @param context The initial context to add.
//     * @param all The array of contexts to add to.
//     * @return The result of the singular and array.
//     */
//    public fun combine(context: RenderingContext, all: List<RenderingContext>): List<RenderingContext> =
//        RenderingNode(ArrayList<RenderingContext>().apply { add(context); addAll(all) }, listOf())

    public fun ofAll(initial: RenderingContext, all: List<RenderingContext>): List<RenderingContext> =
        ArrayList<RenderingContext>().apply { add(initial);addAll(all) }


    public fun <T> useState(key: Int, provider: () -> T): Stateful<T> =
        (states[key] ?: ObservableState(provider()) { _, _ ->
            needsReRender = true
        }.also { states[key] = it }) as Stateful<T>

    public fun <T> useState(key: Int): Stateful<T?> {
        return this.useState(key) { null }
    }

//    public fun <T> requireProp(value: T?): T {
//        if (value == null) throw IllegalPropertyException("You have null properties that are required!")
//        return value
//    }
//
//    public fun <T> requestProp(property: T?): Optional<T> {
//        return Optional.ofNullable(property)
//    }

    /**
     * Returns a PropertyManager based on the `GUIProperties`
     * given. Usually if you have 1-2 property's it will make more
     * sense to use the given basic methods for property management
     * given in the super class (`BuriedGUIComponent`).
     *
     * @param properties The properties to manage.
     * @return The manager for the given properties.
     * @see GuiProperties
     *
     * @see PropertyManager
     */
//    fun manageProps(properties: GUIProperties): PropertyManager {
//        return PropertyManager(properties)
//    }

    /**
     * This class provides general utilities for dealing with
     * properties. It is focused on speed and will try to perform
     * the given operations as fast as possible to provide
     * good rendering times.
     *
     *
     * An example:
     *
     *
     * `final PropertyManager manager = this.manageProps(props) //Assuming properties are provided in the render method
     * final int x = manager.requireProp("x-coord"); //Will through a exception if the property is not specified
     * final byte color = manager.requestProp("color").or(Optional.of(0x01)).get(); //Will return an optional of the value which then you can or and get to provide a default
     * <p>
     * //Your component...
     * <p>
    ` *
     *
     *
     *
     * @author Durgan McBroom
     */
//    public class PropertyManager(private val properties: GUIProperties) {
//        public fun <T> requireProp(name: String): T {
//            return properties[name]
//                ?: throw IllegalPropertyException("Failed to provide property: '" + name + "' in component: '" + javaClass.name + "'")
//        }
//
//        public fun <T> requestProp(name: String): Optional<T> {
//            return Optional.ofNullable(properties.get(name))
//        }
//    }


}