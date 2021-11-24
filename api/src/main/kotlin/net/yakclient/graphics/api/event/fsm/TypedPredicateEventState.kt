package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.util.function.Predicate

public open class TypedPredicateEventState(
    name: String? = null,
    override val exits: List<Transition>,
) : PredicateEventState() {
    public override val name: String = name ?: "unnamed@${System.identityHashCode(this)}"

    override fun <T : EventData> find(event: T): PredicateTransition? =
        exits.filterIsInstance<TypedPredicateTransition<*>>().find { it.type.isAssignableFrom(event::class.java) }
}

public class TypedPredicateTransition<T : EventData>(
    to: EventState, ref: FSMReference, predicate: Predicate<T>,

    public val type: Class<T>
) : PredicateTransition(to, ref, predicate as Predicate<EventData>) {
    override fun accept(t: EventData) {
        if (type.isAssignableFrom(t::class.java)) super.accept(t)
    }
}