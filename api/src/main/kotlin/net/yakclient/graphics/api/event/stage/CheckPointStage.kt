package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData

public class CheckPointStage : EventStage {
    override fun apply(t: EventData): EventData = (t as? EventMetaData)?.wrapped ?: t// EventMetaData(this, false, eventOf(t), null)
}