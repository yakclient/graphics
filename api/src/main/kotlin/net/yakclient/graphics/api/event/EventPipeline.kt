package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.EventStage

public open class EventPipeline(
    stages: List<EventStage>
) : List<EventStage> by stages {
    public fun dispatch(data: EventData): EventData = fold(data) { acc, stage -> stage.apply(acc) }

    public open class MutablePipeline(
        stages: MutableList<EventStage> = ArrayList()
    ) : EventPipeline(stages), MutableList<EventStage> by stages
}



