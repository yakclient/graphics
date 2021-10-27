package net.yakclient.graphics.api

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.EventHook
import net.yakclient.graphics.api.event.EventManager
import net.yakclient.graphics.api.event.EventSubscriber
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.state.GuiState
import net.yakclient.graphics.api.state.ObservableState
import net.yakclient.graphics.api.state.Stateful

public abstract class NativeGuiComponent{
    private val events: MutableSet<Int> = HashSet()
    private val states: MutableMap<Int, Stateful<*>> = HashMap()
    public var needsReRender: Boolean = true

    public abstract fun renderNatively(props: GuiProperties): List<RenderingContext>

    public fun applyChildren(properties: GuiProperties): List<RenderingContext> =
        properties.getAs<List<ComponentRenderingContext<*>>>(CHILD_NAME) ?: ArrayList()

    public fun ofAll(initial: RenderingContext, all: List<RenderingContext>): List<RenderingContext> =
        ArrayList<RenderingContext>().apply { add(initial);addAll(all) }

    @JvmOverloads
    public fun <T> useState(key: Int, triggersReRender : Boolean = true, provider: () -> T): Stateful<T> =
        (states[key] ?: (if (triggersReRender) ObservableState(provider()) { _, _ ->
            needsReRender = true
        } else GuiState(provider())).also { states[key] = it }) as Stateful<T>

//    @JvmOverloads
//    public fun <T> useState(key: Int, triggersReRender: Boolean = true): Stateful<T> {
//        return this.useState(key, triggersReRender) { null }
//    }

    public fun <T: EventData> useEvent(id: Int, event: Class<out EventSubscriber<T>>, callback: EventHook<T>) : Unit = if (events.add(id)) { EventManager.subscribe(event, callback); } else Unit

//    public fun onMouseMove(callback: Hook<MouseMoveData>): Unit = HookManager.subscribe<MouseMoveSubscriber, MouseMoveData>(callback)
//
//    public fun onMouseEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<MouseButtonEventSubscriber, KeyActionData>(callback)
//
//    public fun onKeyboardEvent(callback: Hook<KeyActionData>) : Unit = HookManager.subscribe<KeyboardActionSubscriber, KeyActionData>(callback)
}