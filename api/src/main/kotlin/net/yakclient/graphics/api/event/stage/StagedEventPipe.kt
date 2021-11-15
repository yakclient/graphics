package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.EventPipeline

public class StagedEventPipe(
    stages: List<EventStage>
) : EventPipeline(stages), EventStage {
    override fun apply(t: EventData): EventData = dispatch(t)
}