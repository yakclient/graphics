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

//    public fun load(name: String) : EventDispatcher<out EventData>? = subscribers[name]

    public fun <T: EventData> typeOf(type: Class<out EventDispatcher<T>>) : Class<T> = load(type).eventType

//    public fun <T: EventData> typeOf(name: String) : Class<T> = load<T>(name).eventType

    public fun <T: EventData> findSubscriber(type: Class<T>) : Class<out EventDispatcher<T>>? = subscribers.values.firstOrNull { type.isAssignableFrom(it.eventType) } as? Class<out EventDispatcher<T>>?

//    public fun <T : EventData> subscribe(type: Class<out EventDispatcher<T>>, hook: EventHook<T>): Unit =
//        ((subscribers[type.name] ?: load(type).also {
//            subscribers[type.name] = it; it.hook()
//        }) as? EventDispatcher<T>
//            ?: throw IllegalArgumentException("Invalid Hook for given type: ${type.name}")).subscribe(hook)
//
//
//    public inline fun <reified S : EventDispatcher<T>, T : EventData> subscribe(hook: EventHook<T>): Unit =
//        subscribe(S::class.java, hook)
}