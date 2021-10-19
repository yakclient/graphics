package net.yakclient.graphics.api.hook

import java.util.*
import kotlin.collections.HashMap

public object HookManager {
    private val subscribers: MutableMap<String, EventSubscriber<*>> = HashMap()

    private fun <T : HookData> loadSubscriber(type: Class<out EventSubscriber<T>>): EventSubscriber<T> =
        ServiceLoader.load(type).firstOrNull()
            ?: throw IllegalStateException("Failed to find hook subscriber for type ${type.name}")

    public fun <T : HookData> subscribe(type: Class<out EventSubscriber<T>>, hook: Hook<T>): Unit =
        ((subscribers[type.name] ?: loadSubscriber(type).also { subscribers[type.name] = it; it.hook() }) as? EventSubscriber<T>
            ?: throw IllegalArgumentException("Invalid Hook for given type: ${type.name}")).subscribe(hook)


    public inline fun <reified S : EventSubscriber<T>, T : HookData> subscribe(hook: Hook<T>): Unit = subscribe(S::class.java, hook)
}