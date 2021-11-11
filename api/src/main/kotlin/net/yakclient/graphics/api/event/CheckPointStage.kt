package net.yakclient.graphics.api.event

public class CheckPointStage : EventStage {
    override fun apply(t: EventData): EventData = (t as? EventMetaData)?.event ?: t// EventMetaData(this, false, eventOf(t), null)
}