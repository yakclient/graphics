package net.yakclient.graphics.api.event.fsm

import kotlinx.coroutines.*
import net.yakclient.graphics.api.event.EventData

public class TimingOutEventState(
    name: String,
    exits: List<Transition>,

    private val timeout: Long
) : TimedEventState(name, exits) {
    override fun accept() {
        GlobalScope.async {
            delay(timeout)
            checkNotNull(
                exits.filterIsInstance<TimingOutTransition>().firstOrNull()
            ) { "Timing out states must have 1 Timed out transition!" }.accept(object : EventData {})
        }
        super.accept()
    }
}

public class TimingOutTransition(to: EventState, ref: FSMReference) : Transition(to, ref)