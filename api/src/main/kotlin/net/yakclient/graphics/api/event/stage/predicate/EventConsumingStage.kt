package net.yakclient.graphics.api.event.stage.predicate

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.eventOf
import net.yakclient.graphics.api.event.stage.EventStage
import java.util.function.Consumer

public class EventConsumingStage<T : EventData>(
    private val type: Class<T>,
    private val consumer: Consumer<T>
) : EventStage {
    override fun apply(t: EventData): EventData = eventOf(t, type)?.let { consumer.accept(it); t } ?: t
}