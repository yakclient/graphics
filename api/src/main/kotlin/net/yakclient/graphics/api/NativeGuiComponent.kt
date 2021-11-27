package net.yakclient.graphics.api

import net.yakclient.graphics.api.event.*
import net.yakclient.graphics.api.event.fsm.EventFSM
import net.yakclient.graphics.api.event.fsm.EventFSMScope
import net.yakclient.graphics.api.event.stage.StagedEventFSM
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.state.GuiState
import net.yakclient.graphics.api.state.ObservableState
import net.yakclient.graphics.api.state.Stateful
import kotlin.apply

public abstract class NativeGuiComponent {
    //    private val events: MutableSet<Int> = HashSet()
    private val states: MutableMap<Int, Stateful<*>> = HashMap()
    public var needsReRender: Boolean = true
    private var eventsScoped = false

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

//    @JvmOverloads
//    public fun <T> useState(key: Int, triggersReRender: Boolean = true): Stateful<T> {
//        return this.useState(key, triggersReRender) { null }
//    }

    //    public fun <T: EventData> eventScope(id: Int, event: Class<out EventSubscriber<T>>, callback: EventHook<T>) : Unit = if (events.add(id)) { EventManager.subscribe(event, callback); } else Unit
    public fun eventScope(callback: EventScopeReceiver.() -> Unit): Unit = if (!eventsScoped) {
        eventsScoped = true
        EventScopeReceiver().apply(callback).run {
            `fsm's`.forEach { CommonEventPipe.commonPipe.add(StagedEventFSM(it)) }
            neededDispatchers.forEach { (_, event) -> CommonEventPipe.commonPipe.subscribeTo(event as Class<out EventDispatcher<EventData>>) }
        }
    } else Unit

//    public fun <T : EventData> useChainedEvent(id: Int, first: Class<out EventSubscriber<T>>, firstPredicate: Predicate<T>) : UnaryEventNode<T> = UnaryEventNode(
//        EventNodeDispatcher(), first, null, firstPredicate)

    public inner class EventScopeReceiver internal constructor() {
        internal val `fsm's`: MutableList<EventFSM> = ArrayList()
        internal val neededDispatchers: MutableMap<String, Class<out EventDispatcher<*>>> = HashMap()

        @JvmOverloads
        public fun useFSM(debug: Boolean = false, callback: EventFSMScope.() -> Unit): EventScopeReceiver = apply {
            EventFSMScope(debug).also(callback).let { `fsm's`.add(it) }
        }

        //        public fun <T : EventData> chain(
//            first: Class<T>,
//            firstPredicate: Predicate<T>
//        ): PredicateStageBuilder<T> = EventPipelineBuilder().start(first, firstPredicate)
        public fun require(vararg dispatchers: Class<out EventDispatcher<*>>): EventScopeReceiver =
            apply { dispatchers.forEach { neededDispatchers[it.name] = it } }

        public fun <T : EventData> subscribe(event: Class<out EventDispatcher<T>>, callback: EventHook<T>): Unit =
            EventDispatchManager.load(event).subscribe(callback)
    }


//    public fun onMouseMove(callback: Hook<MouseMoveData>): Unit = HookManager.subscribe<MouseMoveSubscriber, MouseMoveData>(callback)
//
//    public fun onMouseEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<MouseButtonEventSubscriber, KeyActionData>(callback)
//
//    public fun onKeyboardEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<KeyboardActionSubscriber, KeyActionData>(callback)
}