package net.yakclient.graphics.api.event.fsm

import net.yakclient.graphics.api.event.EventData
import java.util.function.Consumer

public open class Transition(
    private val to: EventState,
    private val ref: FSMReference,
) : Consumer<EventData> by (Consumer { ref.set(to) })