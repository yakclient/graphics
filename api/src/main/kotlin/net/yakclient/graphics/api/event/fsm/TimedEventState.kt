package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.time.Instant
import java.util.function.Predicate

public class TimedEventState(
    override val name: String,
    override val exits: List<Transition>
) : PredicateEventState() {
    private lateinit var last: Instant

    override fun accept() {
        last = Instant.now()
    }

    override fun <T : EventData> accept(event: T): Unit = find(event)?.run {
        this@run.accept(if (this@run is TimedTransition<*>) TimedEventData(event, last) else event)
        last = Instant.now()
    } ?: Unit

    override fun <T : EventData> find(event: T): PredicateTransition? =
        exits.filterIsInstance<TimedTransition<*>>().find {
            it.type.isAssignableFrom(event::class.java)
        } ?: exits.filterIsInstance<TypedPredicateTransition<*>>().find {
            it.type.isAssignableFrom(event::class.java)
        }

//    override fun <T : EventData> accept(event: T) {
//        (exits.filterIsInstance<TimedTransition<T>>().find { event::class.java.isAssignableFrom(it.type) }).accept(TimedEventData(event, Instant.now()))
//    }

    public data class TimedEventData<T : EventData>(
        public val data: T,
        public val instant: Instant
    ) : EventData
}

public class TimedTransition<T : EventData>(
    to: EventState,
    reference: FSMReference,
    public val type: Class<T>,
    predicate: Predicate<TimedEventState.TimedEventData<T>>,
) : PredicateTransition(to, reference, Predicate {
    it is TimedEventState.TimedEventData<*> && type.isAssignableFrom(it.data::class.java) && predicate.test(it as TimedEventState.TimedEventData<T>)
})

