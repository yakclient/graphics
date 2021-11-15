package net.yakclient.graphics.api

import net.yakclient.graphics.api.event.*
import net.yakclient.graphics.api.event.stage.StagedEventPipe
import net.yakclient.graphics.api.event.stage.predicate.FailingEventStage
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.state.GuiState
import net.yakclient.graphics.api.state.ObservableState
import net.yakclient.graphics.api.state.Stateful

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

    public fun eventScope(callback: ChainedEventReceiver.() -> Unit): Unit = if (!eventsScoped) {
        eventsScoped = true
        val receiver = ChainedEventReceiver()
        callback(receiver)
        receiver.pipelines.forEach { pipe ->
            pipe.add(CustomStageChainer(pipe, FailingEventStage()))
            CommonEventPipe.commonPipe.add(StagedEventPipe(pipe.toPipe()))

            pipe.events.values.forEach { CommonEventPipe.commonPipe.subscribeTo(it as Class<out EventDispatcher<EventData>>) }
//            pipe.events.mapNotNull { EventDispatchManager.load(it.value as Class<out EventDispatcher<EventData>>) }
//                .forEach { CommonEventPipe.commonPipe.subscribeTo(it::class.java) }
        }

    } else Unit

//    public fun <T : EventData> useChainedEvent(id: Int, first: Class<out EventSubscriber<T>>, firstPredicate: Predicate<T>) : UnaryEventNode<T> = UnaryEventNode(
//        EventNodeDispatcher(), first, null, firstPredicate)

    public inner class ChainedEventReceiver internal constructor() {
        internal val pipelines: MutableList<EventPipelineChainer> = ArrayList()

        public fun chain(): EventPipelineChainer = EventPipelineChainer().also { pipelines.add(it) }
//        public fun <T : EventData> chain(
//            first: Class<T>,
//            firstPredicate: Predicate<T>
//        ): PredicateStageBuilder<T> = EventPipelineBuilder().start(first, firstPredicate)

        public fun <T : EventData> subscribe(event: Class<out EventDispatcher<T>>, callback: EventHook<T>): Unit =
            EventDispatchManager.load(event).subscribe(callback)
    }


//    public fun onMouseMove(callback: Hook<MouseMoveData>): Unit = HookManager.subscribe<MouseMoveSubscriber, MouseMoveData>(callback)
//
//    public fun onMouseEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<MouseButtonEventSubscriber, KeyActionData>(callback)
//
//    public fun onKeyboardEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<KeyboardActionSubscriber, KeyActionData>(callback)
}