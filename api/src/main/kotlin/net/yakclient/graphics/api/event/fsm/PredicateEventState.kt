package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.util.function.Consumer
import java.util.function.Predicate

public sealed class PredicateEventState : EventState {
    override fun <T : EventData> accept(event: T): Unit = find(event)?.accept(event) ?: Unit

    public abstract fun <T : EventData> find(event: T): PredicateTransition?
}

public open class PredicateTransition(
    to: EventState, ref: FSMReference,

    private val predicate: Predicate<EventData>
) : Transition(to, ref), Consumer<EventData> {
    override fun accept(t: EventData) {
        if (predicate.test(t)) run()
    }
}

