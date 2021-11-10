package net.yakclient.graphics.api.event

public class CheckPointStage : EventStage {
    override fun apply(t: EventData): EventData = eventOf(t)// EventMetaData(this, false, eventOf(t), null)
}