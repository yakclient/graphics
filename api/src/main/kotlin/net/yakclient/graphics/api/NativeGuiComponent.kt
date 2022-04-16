package net.yakclient.graphics.api

import net.yakclient.event.api.*
import net.yakclient.event.api.fsm.EventFSM
import net.yakclient.event.api.fsm.MutableEventFSM
import net.yakclient.event.api.stage.StagedEventFSM
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.state.GuiState
import net.yakclient.graphics.api.state.ObservableState
import net.yakclient.graphics.api.state.Stateful
import java.util.function.Consumer
import kotlin.apply

public abstract class NativeGuiComponent {
    private val states: MutableMap<Int, Stateful<*>> = HashMap()
    public var needsReRender: Boolean = true
    private var subscriptionCyclePassed = false

    public abstract fun renderNatively(props: GuiProperties): List<RenderingContext>

    public fun applyChildren(properties: GuiProperties): List<RenderingContext> =
        properties.getAs<List<ComponentRenderingContext<*>>>(CHILD_NAME) ?: ArrayList()

    public fun ofAll(initial: RenderingContext, all: List<RenderingContext>): List<RenderingContext> =
        ArrayList<RenderingContext>().apply { add(initial);addAll(all) }

    @JvmOverloads
    public fun <T> useState(key: Int, triggersReRender: Boolean = true, provider: () -> T): Stateful<T> =
        (states[key] ?: (if (triggersReRender) ObservableState(provider()) { _, _ ->
            needsReRender = true
        } else GuiState(provider())).also { states[key] = it }) as Stateful<T>

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