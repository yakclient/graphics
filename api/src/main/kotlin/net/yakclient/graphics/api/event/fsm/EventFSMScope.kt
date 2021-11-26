package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.util.function.Predicate

public class EventFSMScope(
    debug: Boolean = false
) : EventFSM(StatePlaceholder(), debug) {
    public val ref: FSMReference = FSMReference(this)
//    internal val neededDispatchers: MutableMap<String, Class<out EventDispatcher<*>>> = HashMap()

    public fun of(name: String = EventState.defaultName()): MutableEventState =
        of(TypedPredicateEventState(name, ArrayList()))

    public fun timedOf(name: String = EventState.defaultName()): MutableEventState =
        of(TimedEventState(name, ArrayList()))

    public fun timingOutOf(to: EventState, timeOut: Long, name: String = EventState.defaultName()): MutableEventState = of(TimingOutEventState(name, arrayListOf(TimingOutTransition(to, ref)), timeOut))

    public fun of(delegate: PredicateEventState): MutableEventState =
        MutableEventState(delegate).also { if (current is StatePlaceholder) current = it }

    public fun <T: EventState> of(delegate: T): T = delegate.also { if (current is StatePlaceholder) current = it }

    public inner class MutableEventState(
        private val delegate: PredicateEventState = TypedPredicateEventState(EventState.defaultName(), ArrayList()),
    ) : EventState by delegate, MutableList<Transition> by delegate.exits as? MutableList<Transition>
        ?: throw IllegalStateException("Event state must be mutable!") {

        public fun transitionsTo(state: EventState): TransitionProvider = TransitionProvider(state)

        public open inner class TransitionProvider(
            private val to: EventState
        ) {
            public fun <T : EventData> with(type: Class<T>, predicate: Predicate<T>) {
                add(TypedPredicateTransition(to, ref, type, predicate))
            }

            public fun <T : EventData> withTime(
                type: Class<T>,
                predicate: Predicate<TimedEventState.TimedEventData<T>>
            ) {
                add(TimedTransition(to, ref, type, predicate))
            }
        }
    }


    private class StatePlaceholder : EventState {
        override val name: String = "placeholder"

        override fun <T : EventData> accept(event: T) {}
    }
}

public inline fun <reified T : EventData> EventFSMScope.MutableEventState.TransitionProvider.with(p: Predicate<T>): Unit =
    with(T::class.java, p)

public inline fun <reified T : EventData> EventFSMScope.MutableEventState.TransitionProvider.withTime(p: Predicate<TimedEventState.TimedEventData<T>>): Unit =
    withTime(T::class.java, p)

public infix fun EventFSMScope.MutableEventState.transitionsTo(state: EventState): EventFSMScope.MutableEventState.TransitionProvider =
    TransitionProvider(state)
