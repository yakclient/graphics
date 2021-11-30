package net.yakclient.graphics.api

import net.yakclient.graphics.api.render.RenderingContext
import java.util.*
import kotlin.reflect.KClass

public object DeferredComponentLoader {
    private val providers: List<DeferredComponentProvider> =
        ServiceLoader.load(DeferredComponentProvider::class.java)
            .sortedWith { first, second -> first.priority - second.priority }

    public fun <T : DeferredComponent> find(type: Class<T>): Class<out DeferredComponent> =
        providers.firstNotNullOfOrNull { it.providesWith(type) }
            ?: throw IllegalStateException(
                if (providers.isEmpty())
                    "Failed to load Deferred component providers, this is most likely an issue with the provider modules not being on your classpath path"
                else "No deferred component provider loaded manages type: ${type.name}"
            )

    public fun <T : DeferredComponent> create(type: Class<T>): T = find(type).let {
        it.getDeclaredConstructor().newInstance() as? T
            ?: throw IllegalStateException("Found component type(${it.name}) does not extend from type ${type.name}. Make sure the component provide is correctly setup!")
    }

    public inline fun <reified T : DeferredComponent> create(): T = create(T::class.java)
}

public interface DeferredComponentProvider {
    public val priority: Int
        get() = 0

    public fun <T : DeferredComponent> providesWith(type: Class<T>): Class<out T>?
}

public fun <T : DeferredComponent> provideWith(
    type: Class<T>,
    vararg inputs: KClass<out DeferredComponent>
): Class<out T>? =
    assert(type.superclass == DeferredComponent::class.java).let { inputs.firstOrNull { type.isAssignableFrom(it.java) }?.java as? Class<out T> }

public abstract class DeferredComponent : NativeGuiComponent() {
    private val backingComponent: DeferredComponent by lazy {
        DeferredComponentLoader.create(this::class.java)
    }

    override fun renderNatively(props: GuiProperties): List<RenderingContext> =
        backingComponent.renderNatively(props)
}