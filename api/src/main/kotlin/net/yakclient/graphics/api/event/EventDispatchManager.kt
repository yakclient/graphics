package net.yakclient.graphics.api.event

import java.util.*
import kotlin.collections.HashMap

public object EventDispatchManager {
    private val subscribers: MutableMap<String, EventDispatcher<*>> = HashMap()

    public fun <T : EventData> overload(subscriber: EventDispatcher<T>): EventDispatcher<T> =
        subscriber.also { subscribers[subscriber::class.java.name] = it }

    public fun <T : EventData> load(type: Class<out EventDispatcher<T>>): EventDispatcher<T> =
        (subscribers[type.name] ?: (ServiceLoader.load(type).firstOrNull()?.also { subscribers[type.name] = it }
            ?: throw IllegalStateException("Failed to find hook subscriber for type ${type.name}"))) as EventDispatcher<T>

    public fun <T: EventData> typeOf(type: Class<out EventDispatcher<T>>) : Class<T> = load(type).eventType

    public fun <T: EventData> findSubscriber(type: Class<T>) : Class<out EventDispatcher<T>>? = subscribers.values.firstOrNull { type.isAssignableFrom(it.eventType) } as? Class<out EventDispatcher<T>>?
}