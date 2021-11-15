package net.yakclient.graphics.api.event.stage.predicate

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.IgnoredEventData
import net.yakclient.graphics.api.event.eventOf
import net.yakclient.graphics.api.event.stage.EventStage
import java.util.function.Predicate

public class IgnoringEventStage<T : EventData>(
    private val type: Class<T>,
    private val predicate: Predicate<T>
) : EventStage {
    override fun apply(t: EventData): EventData =
        eventOf(t).let { if (type.isAssignableFrom(it::class.java) && predicate.test(t as T)) IgnoredEventData() else t }
}