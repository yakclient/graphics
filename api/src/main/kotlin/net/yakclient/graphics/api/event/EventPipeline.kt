package net.yakclient.graphics.api.event

public class EventPipeline(
    private val stages: List<EventStage>
) {
    public fun dispatch(data: EventData): EventData = stages.fold(data) { acc, stage -> stage.apply(acc) }
}


