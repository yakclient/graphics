package net.yakclient.graphics.api.hook

public fun interface Hook<E : HookData> : (E) -> Unit

public interface HookData

public abstract class EventSubscriber<E : HookData> {
    private val hooks: MutableList<Hook<E>> = ArrayList()

    public fun subscribe(hook: Hook<E>): Unit = hooks.add(hook).let {}

    protected fun notify(data: E): Unit = hooks.forEach { it(data) }

    public abstract fun hook()
}