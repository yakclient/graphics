package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.EventSubscriber
import java.util.function.Consumer

public class CommonEventPipe private constructor() : EventPipeline.MutablePipeline() {
    private val subscribedEvents: MutableSet<String> = HashSet()
    private val subscribers: MutableMap<String, EventSubscriber.MutableEventSubscriber<*>> = HashMap()

    public companion object {
        @JvmStatic
        public val commonPipe: CommonEventPipe = CommonEventPipe()
    }

    public fun <T : EventData> subscribeTo(dispatcher: Class<out EventDispatcher<T>>): Unit =
        if (subscribedEvents.add(dispatcher.name)) EventDispatchManager.load(dispatcher)
            .subscribe(CommonPipeEventHook()) else Unit

    public fun <T : EventData> subscribe(dispatcher: Class<out EventDispatcher<T>>, callback: Consumer<T>): Unit =
        (if (!subscribers.containsKey(dispatcher.name)) EventSubscriber.MutableEventSubscriber(
            EventDispatchManager.typeOf(dispatcher),
            ArrayList()
        ).also { subscribers[dispatcher.name] = it } else subscribers[dispatcher.name] as? EventSubscriber.MutableEventSubscriber<T>)?.add(callback).let { }

    private inner class CommonPipeEventHook<T : EventData> : EventHook<T> {
        override fun accept(t: T) = Unit.apply { this@CommonEventPipe.dispatch(t) }
    }
}