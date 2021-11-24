package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.EventHook

public class EventSubscriber<T: EventData>(
    private val type: Class<T>,
    private val subscriptions: List<EventHook<T>>
) : EventStage {
    override fun apply(t: EventData): EventData =
        t.takeIf { type.isAssignableFrom(it::class.java) }?.also { e -> subscriptions.forEach { it.accept(e as T) } } ?: t
}