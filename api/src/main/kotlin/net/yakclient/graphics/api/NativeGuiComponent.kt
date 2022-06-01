package net.yakclient.graphics.api

import net.yakclient.event.api.*
import net.yakclient.event.api.fsm.EventFSM
import net.yakclient.event.api.fsm.MutableEventFSM
import net.yakclient.event.api.stage.StagedEventFSM
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.state.GuiState
import net.yakclient.graphics.api.state.ObservableState
import net.yakclient.graphics.api.state.Stateful
import net.yakclient.graphics.util.Texture
import net.yakclient.graphics.util.TextureFactory
import java.net.URL
import java.util.function.Consumer
import kotlin.apply

public abstract class NativeGuiComponent {
    private val textures: MutableMap<String, Texture> = HashMap()
    private val states: MutableMap<Int, Stateful<*>> = HashMap()
    public var needsReRender: Boolean = true
    private var subscriptionCyclePassed = false

    public abstract fun renderNatively(props: GuiPropertiesMap): List<RenderingContext>

    public fun applyChildren(properties: GuiPropertiesMap): List<RenderingContext> =
        properties.getAs<List<ComponentRenderingContext<*>>>(CHILD_NAME) ?: ArrayList()

    public fun ofAll(initial: RenderingContext, all: List<RenderingContext>): List<RenderingContext> =
        ArrayList<RenderingContext>().apply { add(initial);addAll(all) }

    @JvmOverloads
    public fun <T> useState(key: Int, triggersReRender: Boolean = true, provider: () -> T): Stateful<T> =
        (states[key] ?: (if (triggersReRender) ObservableState(provider()) { _, _ ->
            needsReRender = true
        } else GuiState(provider())).also { states[key] = it }) as Stateful<T>

    public fun useTexture(path: String): Texture {
        return if (textures.contains(path)) textures[path]!! else {
            val callerElement = Thread.currentThread().stackTrace[2]
            val caller = runCatching { Class.forName(callerElement.className) }.getOrNull()
                ?: throw IllegalStateException("Failed to load caller class for useTexture call. Caller class is: $'${callerElement.className}'. Make sure that you have exported all applicable packages to 'yakclient.graphics.api'")

            val url = caller.getResource(path) ?: throw IllegalArgumentException("Failed to find resource: '$path'.")
            useTexture(url)
        }
    }

    public fun useTexture(url: URL): Texture =
        if (textures.contains(url.toString())) textures[url.toString()]!! else {
            val tex = TextureFactory.loadTexture(url)
            textures[url.toString()] = tex
            tex
        }

    public fun eventScope(callback: EventScopeReceiver.() -> Unit): Unit = if (!subscriptionCyclePassed) {
        subscriptionCyclePassed = true
        EventScopeReceiver().apply(callback).run {
            fsms.forEach { CommonEventPipe.commonPipe.add(StagedEventFSM(it)) }
            neededDispatchers.forEach { (_, event) -> CommonEventPipe.commonPipe.subscribeTo(event as Class<out EventDispatcher<EventData>>) }
        }
    } else Unit

    public inner class EventScopeReceiver internal constructor() {
        internal val fsms: MutableList<EventFSM> = ArrayList()
        internal val neededDispatchers: MutableMap<String, Class<out EventDispatcher<*>>> = HashMap()

        @JvmOverloads
        public fun useFSM(debug: Boolean = false, callback: MutableEventFSM.() -> Unit): EventScopeReceiver = apply {
            MutableEventFSM(debug).also(callback).let { fsms.add(it) }
        }

        public fun require(vararg dispatchers: Class<out EventDispatcher<*>>): EventScopeReceiver =
            apply { dispatchers.forEach { neededDispatchers[it.name] = it } }

        public fun <T : EventData> subscribe(event: Class<out EventDispatcher<T>>, callback: Consumer<T>): Unit =
            CommonEventPipe.commonPipe.apply { subscribe(event, callback) }.subscribeTo(event)
    }
}