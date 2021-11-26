package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData

public abstract class DelegatingEventState(
    override val name: String
) : EventState {
    override fun <T : EventData> accept(event: T): Unit = find(event)?.accept(event) ?: Unit

    public abstract fun <T : EventData> find(event: T): Transition?
}