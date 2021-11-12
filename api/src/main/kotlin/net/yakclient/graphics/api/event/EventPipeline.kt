package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.EventStage

public class EventPipeline(
    stages: List<EventStage>
) : List<EventStage> by stages {
    public fun dispatch(data: EventData): EventData = fold(data) { acc, stage -> stage.apply(acc) }
}


