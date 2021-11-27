package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.EventHook
import java.util.function.Consumer

public open class EventSubscriber<T : EventData>(
    private val type: Class<T>,
    private val subscriptions: List<Consumer<T>>
) : EventStage {
    override fun apply(t: EventData): EventData =
        t.takeIf { type.isAssignableFrom(it::class.java) }?.also { e -> subscriptions.forEach { it.accept(e as T) } }
            ?: t

    public class MutableEventSubscriber<T : EventData>(
        type: Class<T>, subscriptions: MutableList<Consumer<T>> = ArrayList()
    ) : EventSubscriber<T>(type, subscriptions), MutableList<Consumer<T>> by subscriptions
}