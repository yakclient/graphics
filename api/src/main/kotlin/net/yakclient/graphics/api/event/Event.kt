package net.yakclient.graphics.api.event

public fun interface EventHook<E : EventData> : (E) -> Unit

public interface EventData

public interface WrappedEventData : EventData {
    public var wrapped: EventData
}

public abstract class EventSubscriber<E : EventData> {
    private val hooks: MutableList<EventHook<E>> = ArrayList()

    public fun subscribe(hook: EventHook<E>): Unit = hooks.add(hook).let {}

    protected fun notify(data: E): Unit = hooks.forEach { it(data) }

    public abstract fun hook()
}