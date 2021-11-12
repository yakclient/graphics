package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.*
import java.util.function.Function

public class EventSupplierStage<T : EventData, P : Any>(
    private val type: Class<T>,
    private val supplier: Function<T, P>
) : EventStage {
    private lateinit var last: P

    override fun apply(t: EventData): EventData {
        if (t is SuccessfulEventData && type.isAssignableFrom(t.event::class.java)) last = supplier.apply(t.event as T)
        return if (this::last.isInitialized) if (t is SuccessfulEventData) IgnoredEventData() else EventMetaData(
            this,
            false,
            BinaryEventData(eventOf(t), last),
            if (t is EventMetaData) t else null
        ) else t
    }
//        (t.takeIf { t is HierarchicalEventData && type.isAssignableFrom(t.event::class.java) }
//            ?.let { it as HierarchicalEventData }
//            ?.also {
//                if (it is SuccessfulEventData) last = provider.apply(it.event as T)
//            } ?: t).let { EventMetaData(this, false, BinaryEventData(eventOf(t) as T, last), if (it is EventMetaData) it else null ) }
//        if (t is SuccessfulEventData) {
//
//        } else if (t is EventMetaData && )
//        t.takeIf { it is SuccessfulEventData }
//            ?.let { it as EventMetaData }
//            ?.also {
//                if (it.neededEval) last = provider.apply(it.event as T)
//            }
//            ?.let { EventMetaData(this, false, BinaryEventData(it.event, last), it) } ?: t
//        return if (t is EventMetaData && type.isAssignableFrom(t.event::class.java)) {
//            if (t.neededEval) last = provider.apply(t.event as T)
//            EventMetaData(this, false, BinaryEventData(t.event as T, last), t)
//        } else t
//        t.takeIf { it is EventMetaData && it.neededEval && type.isAssignableFrom(it.event::class.java) }
//            ?.let { eventOf(it, type) }
//            ?.let { EventMetaData(this, false, BinaryEventData(it, provider.apply(it)), t as EventMetaData) } ?: t
}