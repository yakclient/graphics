package net.yakclient.graphics.api.event

import java.util.*
import kotlin.collections.HashMap

public object EventManager {
    private val subscribers: MutableMap<String, EventSubscriber<*>> = HashMap()

    //Not recommended, mostly used with testing
    public fun <T : EventData> forceLoad(
        subscriber: EventSubscriber<T>
    ) {
        subscribers[subscriber::class.java.name] = subscriber
        subscriber.hook()
    }

    private fun <T : EventData> load(type: Class<out EventSubscriber<T>>): EventSubscriber<T> =
        ServiceLoader.load(type).firstOrNull()
            ?: throw IllegalStateException("Failed to find hook subscriber for type ${type.name}")

    public fun <T : EventData> subscribe(type: Class<out EventSubscriber<T>>, hook: EventHook<T>): Unit =
        ((subscribers[type.name] ?: load(type).also {
            subscribers[type.name] = it; it.hook()
        }) as? EventSubscriber<T>
            ?: throw IllegalArgumentException("Invalid Hook for given type: ${type.name}")).subscribe(hook)


    public inline fun <reified S : EventSubscriber<T>, T : EventData> subscribe(hook: EventHook<T>): Unit =
        subscribe(S::class.java, hook)
}