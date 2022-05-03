package net.yakclient.graphics.api

import net.yakclient.graphics.api.render.RenderingContext
import kotlin.reflect.KFunction

public abstract class GuiComponent: NativeGuiComponent() {
    private val maintainedChildren: MutableList<NativeGuiComponent> = ArrayList()
    private val childSettings: MutableList<ComponentRenderingContext<*>> = ArrayList()

    private var lastPropertyHash: Int = -1

    protected abstract fun render(properties: GuiPropertiesMap)

    //Should not render unless the properties have changed since last render cycle. Should always call render on children.
    override fun renderNatively(props: GuiPropertiesMap): List<RenderingContext> {
        val shouldReRender: Boolean = props.hashCode() != lastPropertyHash || super.needsReRender

        lastPropertyHash = props.hashCode()

        if (shouldReRender) {
            super.needsReRender = false

            childSettings.clear()
            render(props)
        }
        return childSettings
    }

    public fun <T : NativeGuiComponent> use(clazz: Class<out T>, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: clazz.getConstructor().newInstance().also {
            maintainedChildren.add(id, it)
        }) as T

    public inline fun <reified T : NativeGuiComponent> use(id: Int): T = use(T::class.java, id)

    public fun <T : NativeGuiComponent> use(func: Component, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: FunctionalComponent(func).also {
            maintainedChildren.add(id, it)
        }) as T

    public fun <T : NativeGuiComponent> build(
        component: T,
    ): ComponentBuilder = ComponentBuilder(component)

    @JvmOverloads
    public fun <T : NativeGuiComponent> build(
        component: T,
        settings: ComponentBuilder.() -> Unit = {}
    ): Unit = childSettings.add(
        ComponentBuilder(component).also(settings)
            .let { ComponentRenderingContext(it.component, it.properties.build()) }).let { }
}

//public fun interface Component<T: Properties> : GuiProperties.(T) -> Unit;

public typealias Component = GuiComponent.(props: GuiPropertiesMap) -> Unit

public class FunctionalComponent constructor(
    private val component: Component
) : GuiComponent() {
    override fun render(properties: GuiPropertiesMap): Unit = component(this, properties)
}

public class ComponentBuilder(
//    private val backingComponent: GuiComponent,
    internal val component: NativeGuiComponent
) {
    internal val properties: MutableGuiPropertiesMap = MutableGuiPropertiesMap()

    public fun set(name: String, value: Any): Unit = Unit.also { properties[name] = value }

    public fun setIfNotNull(name: String, value: Any?): Unit = if (value != null) set(name, value) else Unit

    public fun set(name: String): PropertySetter = PropertySetter(name, this)

    public inner class PropertySetter internal constructor(
        internal val name: String,
        internal val scope: ComponentBuilder
    )

    public infix fun PropertySetter.to(value: Any): Unit = scope.set(name, value)

    public infix fun PropertySetter.ifNotNull(value: Any?): Unit = if (value != null) to(value) else Unit

    public fun build(component: NativeGuiComponent, settings: ComponentBuilder.() -> Unit = {}) {
        val scope = ComponentBuilder(component)
        settings(scope)
        properties.addChild(ComponentRenderingContext(scope.component, scope.properties.build()))
    }
}