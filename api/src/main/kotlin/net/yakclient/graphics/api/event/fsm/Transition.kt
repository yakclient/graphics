package net.yakclient.graphics.api.event.fsm

public open class Transition(
    private val to: EventState,
    private val ref: FSMReference,
) : Runnable by (Runnable { ref.set(to) })