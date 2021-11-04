package net.yakclient.graphics.api.event

import java.util.function.Function

public class EventProviderStage<T: EventData, P : Any>(
    private val provider: Function<T, P>
) : EventStage {
    override fun apply(t: EventData): EventData =
        if (!FailedEventData::class.java.isAssignableFrom(t::class.java)) eventOf<T>(t).let {BinaryEventData(it, provider.apply(it)) } else t
}