package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Predicate

public class FailedEventData internal constructor() : EventData

public data class BinaryEventData<out T : EventData, out P : Any> internal constructor(
    public val event: T,
    public val other: P
) : EventData

public abstract class SatisfiableEventStage<T : EventData>(
    private val type: Class<T>,
) : EventStage {
    public var isSatisfied: Boolean = false

    override fun apply(t: EventData): EventData =
        // TODO redo logic about re-evaluation and stuff
        if (isSatisfied) EventMetaData(
            this,
            false,
            if (t is EventMetaData) t.event else t,
            if (t is EventMetaData) t else null,
            t::class.java
        )
        else {
            // TODO redo, will break if is not satisifed and type is not of expected data type
            if (type.isAssignableFrom(t::class.java)) isSatisfied = satisfies(t as T)
            else if (EventMetaData::class.java.isAssignableFrom(t::class.java) && type.isAssignableFrom((t as EventMetaData).type)) isSatisfied =
                satisfies(t.event as T)
            if (isSatisfied) EventMetaData(
                this,
                true,
                if (t is EventMetaData) t.event else t,
                if (t is EventMetaData) t else null,
                t::class.java
            ) else FailedEventData().apply {
                tailrec fun recursivelyFail(ref: EventMetaData) {
                   if( ref.ref is SatisfiableEventStage<*>) ref.ref.isSatisfied = false
                    if (ref.next != null) recursivelyFail(ref.next)
                }
                if (t is EventMetaData) recursivelyFail(t)
            }
        }

    public abstract fun satisfies(data: T): Boolean

}


public fun <T : EventData> eventOf(event: EventData, expected: Class<T>): T? =
    if (event is EventMetaData && expected.isAssignableFrom(event.type)) event.event as T
    else if (expected.isAssignableFrom(event::class.java)) event as T
    else null

public inline fun <reified T : EventData> eventOf(event: EventData): T? = eventOf(event, T::class.java)

public abstract class PredicateEventStage<T : EventData>(type: Class<T>) : SatisfiableEventStage<T>(type)

public class UnaryPredicateStage<T : EventData>(
    type: Class<T>,

    private val predicate: Predicate<T>
) : PredicateEventStage<T>(type) {
    override fun satisfies(data: T): Boolean = predicate.test(data)
}

private inline fun <reified T> classOf(): Class<T> = T::class.java

public class BinaryPredicateStage<T : EventData, P : Any>(
    private val predicate: BiPredicate<T, P>
) : PredicateEventStage<BinaryEventData<T, P>>(classOf()) {
    // Form it can come here in:
    // Failed
    // Hierarchical
    // Binary
//    override fun apply(t: EventData): EventData =
//        super.apply(eventOf<>(t) if (BinaryEventData::class.java.isAssignableFrom(t::class.java)) (t as BinaryEventData<*, *>).event else t)

    override fun satisfies(data: BinaryEventData<T, P>): Boolean = predicate.test(data.event, data.other)
}

public data class EventMetaData(
    public val ref: EventStage,
    public val neededEval: Boolean,
    public val event: EventData,
    public val next: EventMetaData?,
    public val type: Class<out EventData>
) : EventData {
//    public fun fail() =
}

//public class EventStageReference(
//    public val stage: SatisfiableEventStage<*>,
//    type: Class<out EventData>,
//    data: EventData
//) : HierarchicalEventData<EventData>(data, type)