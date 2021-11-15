package net.yakclient.graphics.api.event

import java.util.function.Consumer

public fun interface EventHook<E : EventData> : Consumer<E>

public interface EventData

public interface WrappedEventData : EventData {
    public var wrapped: EventData
}

public abstract class EventDispatcher<E : EventData> {
    private val hooks: MutableSet<EventHook<E>> = HashSet()
    public abstract val eventType: Class<E>

    public fun subscribe(e: EventHook<E>): Unit = let { hooks.add(e) }

    protected fun dispatch(e: E): Unit = hooks.forEach { it.accept(e) }
}
