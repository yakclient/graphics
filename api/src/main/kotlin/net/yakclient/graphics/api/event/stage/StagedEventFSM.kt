package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.fsm.EventFSM

public class StagedEventFSM(
    private val fsm: EventFSM
) : EventStage {
    override fun apply(t: EventData): EventData = t.apply { fsm.dispatch(t) }
}