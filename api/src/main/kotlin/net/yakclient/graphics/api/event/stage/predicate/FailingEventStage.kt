package net.yakclient.graphics.api.event.stage.predicate

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.stage.EventStage

internal tailrec fun recursivelyFail(meta: EventMetaData) {
    if (meta.ref is SatisfiableEventStage<*>) meta.ref.isSatisfied = false
    if (meta.previous != null) recursivelyFail(meta.previous)
}

public class FailingEventStage : EventStage {
    override fun apply(t: EventData): EventData = t.apply { if (t is EventMetaData) recursivelyFail(t) }
}