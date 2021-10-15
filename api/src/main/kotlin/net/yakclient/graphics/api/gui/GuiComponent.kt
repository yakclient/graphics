package net.yakclient.graphics.api.gui

import net.yakclient.graphics.api.render.RenderingContext
import kotlin.reflect.KFunction

public abstract class GuiComponent : NativeGuiComponent() {
    private val maintainedChildren: MutableList<NativeGuiComponent> = ArrayList()
    private val childSettings: MutableList<ComponentRenderingContext<*>> = ArrayList()

    private var lastPropertyHash: Int = -1

    protected abstract fun render(properties: GuiProperties)

    //Should not render unless the properties have changed since last render cycle. Should always call render on children.
    override fun renderNatively(props: GuiProperties): List<RenderingContext> {
        val shouldReRender: Boolean = props.hashCode() != lastPropertyHash || super.needsReRender

        lastPropertyHash = props.hashCode()

        if (shouldReRender) {
            childSettings.clear()
            render(props)
            super.needsReRender = false
        }
        return childSettings
    }

    public fun <T : NativeGuiComponent> use(clazz: Class<out T>, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: clazz.getConstructor().newInstance()) as T

    public inline fun <reified T : NativeGuiComponent> use(id: Int): T = use(T::class.java, id)

    public fun <T : NativeGuiComponent> use(func: KFunction<Component>, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: FunctionalComponent(func.call()).also {
            maintainedChildren.add(
                id,
                it
            )
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

public typealias Component = GuiComponent.(props: GuiProperties) -> Unit

public class FunctionalComponent constructor(
    private val component: Component
) : GuiComponent() {
    override fun render(properties: GuiProperties): Unit = component(this, properties)
}

public class ComponentBuilder(
//    private val backingComponent: GuiComponent,
    internal val component: NativeGuiComponent
) {
    internal val properties: PropertyFactory = PropertyFactory()

    public fun set(name: String, value: Any): Unit = Unit.also { properties[name] = value }

    public fun setIfNotNull(name: String, value: Any?): Unit = if (value != null) set(name, value) else Unit

    public fun set(name: String): PropertySetter = PropertySetter(name, this)

    public class PropertySetter internal constructor(
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