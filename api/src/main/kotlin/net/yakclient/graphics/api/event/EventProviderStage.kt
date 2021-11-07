package net.yakclient.graphics.api.event

import java.util.function.Function

public class EventProviderStage<T : EventData, P : Any>(
    private val reference: EventStage,
    private val type: Class<T>,
    private val provider: Function<T, P>
) : EventStage {
    override fun apply(t: EventData): EventData =
        t.takeIf { it is EventMetaData && it.neededEval && type.isAssignableFrom(it.type) }?.let { eventOf(it, type) }
            ?.let { EventMetaData(this, false, BinaryEventData(it, provider.apply(it)), t as EventMetaData, BinaryEventData::class.java) } ?: t
//        eventOf(t, type).takeIf { t is EventMetaData && t.ref == reference }
//            ?.let { BinaryEventData(it, provider.apply(it)) } ?: t

//        if (!FailedEventData::class.java.isAssignableFrom(t::class.java) && ) eventOf(t, type)?.let {
//            BinaryEventData(
//                it,
//                provider.apply(it)
//            )
//        } ?: t else t
}