package net.yakclient.graphics.api.gui

import net.yakclient.graphics.api.render.RenderingContext
import kotlin.reflect.KFunction

public abstract class GuiComponent : NativeGuiComponent() {
    private val maintainedChildren: MutableList<NativeGuiComponent> = ArrayList()
    private val childSettings: MutableList<ComponentRenderingContext<*>> = ArrayList()

    public abstract fun render(properties: GuiProperties)

    override fun renderNatively(props: GuiProperties): List<RenderingContext> {
        childSettings.clear()
        render(props)
        return childSettings
    }

    public fun <T : NativeGuiComponent> use(clazz: Class<out T>, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: clazz.getConstructor().newInstance()) as T

    public inline fun <reified T : NativeGuiComponent> use(id: Int): T = use(T::class.java, id)

    public fun <T : NativeGuiComponent> use(func: KFunction<Component>, id: Int): T =
        (maintainedChildren.getOrNull(id) ?: FunctionalComponent(func.call()).also { maintainedChildren[id] = it }) as T

    @JvmOverloads
    public fun <T : NativeGuiComponent> build(
        component: T,
        settings: ExternalComponentConfigurationScope.() -> Unit = {}
    ) : Unit = childSettings.add(ExternalComponentConfigurationScope(component).also(settings).let { ComponentRenderingContext(it.backingComponent, it.properties.build()) }).let { }
}

public typealias Component = GuiComponent.(props: GuiProperties) -> Unit

public class FunctionalComponent internal constructor(
    private val component: Component
) : GuiComponent() {
    override fun render(properties: GuiProperties): Unit = component(this, properties)
}

public class ExternalComponentConfigurationScope(
    internal val backingComponent: NativeGuiComponent
) {
    internal val properties: PropertyFactory = PropertyFactory()

    public fun set(name: String, value: Any): Unit = Unit.also { properties.set(name, value) }

    public fun set(name: String): PropertySetter = PropertySetter(name, this)

    public class PropertySetter internal constructor(
        internal val name: String,
        internal val scope: ExternalComponentConfigurationScope
    )

    public infix fun PropertySetter.to(value: Any): Unit = scope.set(name, value)

    public fun build(component: NativeGuiComponent, settings: ExternalComponentConfigurationScope.() -> Unit = {}) {
        val scope = ExternalComponentConfigurationScope(component)
        settings(scope)
        properties.addChild(ComponentRenderingContext(scope.backingComponent, scope.properties.build()))
    }
}