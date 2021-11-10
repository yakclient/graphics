package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Predicate

public open class IgnoredEventData internal constructor() : EventData

public data class SuccessfulEventData internal constructor(
    override val event: EventData
) : HierarchicalEventData

public data class BinaryEventData<out T : EventData, out P : Any> internal constructor(
    public override val event: T,
    public val other: P
) : HierarchicalEventData


public data class EventMetaData(
    public val ref: EventStage,
    public val neededEval: Boolean,
    public override val event: EventData,
    public val previous: EventMetaData?
) : HierarchicalEventData

public abstract class SatisfiableEventStage<in T : EventData>(
    private val type: Class<T>,
) : EventStage {
    public var isSatisfied: Boolean = false

    public var `re-eval`: Boolean = false

    override fun apply(t: EventData): EventData =
        // TODO redo logic about re-evaluation and stuff
        if (t is SuccessfulEventData) IgnoredEventData()
        else if (t is IgnoredEventData) t
        else if (isSatisfied && !`re-eval`) EventMetaData(
            this,
            false,
            if (t is EventMetaData) t.event else t,
            if (t is EventMetaData) t else null
        )
        else {
            (if (type.isAssignableFrom(t::class.java)) t as T
            else if (t is HierarchicalEventData && type.isAssignableFrom(t.event::class.java)) t.event as T
            else null)?.let { actual ->
                isSatisfied = satisfies(actual)

                if (isSatisfied) SuccessfulEventData(actual)
                else {
                    IgnoredEventData().apply {
                        tailrec fun recursivelyFail(meta: EventMetaData) {
                            if (meta.ref is SatisfiableEventStage<*>) meta.ref.isSatisfied = false
                            if (meta.previous != null) recursivelyFail(meta.previous)
                        }
                        if (t is EventMetaData) recursivelyFail(t)
                    }
                }
            } ?: IgnoredEventData()


//            if (type.isAssignableFrom(t::class.java)) isSatisfied = satisfies(t as T)
//            else if (EventMetaData::class.java.isAssignableFrom(t::class.java) && type.isAssignableFrom((t as EventMetaData).event::class.java)) isSatisfied =
//                satisfies(t.event as T)

        }

    public abstract fun satisfies(data: T): Boolean
}

public fun eventOf(it: EventData): EventData = if (it is HierarchicalEventData) it.event else it

public fun <T : EventData> eventOf(it: EventData, expected: Class<T>): T? {
    val eventOf = eventOf(it)
    return if (expected.isAssignableFrom(eventOf::class.java)) eventOf as T else null
}

//public inline fun <reified T : EventData> eventOf(event: EventData): T? = eventOf(event, T::class.java)

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


//public class EventStageReference(
//    public val stage: SatisfiableEventStage<*>,
//    type: Class<out EventData>,
//    data: EventData
//) : HierarchicalEventData<EventData>(data, type)