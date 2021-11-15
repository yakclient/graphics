package net.yakclient.graphics.api.event.stage.predicate

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.stage.EventStage

public class CheckPointStage : EventStage {
    override fun apply(t: EventData): EventData = (t as? EventMetaData)?.wrapped ?: t
}