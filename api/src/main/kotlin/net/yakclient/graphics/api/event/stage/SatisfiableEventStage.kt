package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.*
import java.util.function.BiPredicate
import java.util.function.Predicate

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

public abstract class SatisfiableEventStage<T : EventData>(
    public val type: Class<T>,
) : EventStage {
    public var isSatisfied: Boolean = false

    public var reEval: Boolean = false

    override fun apply(t: EventData): EventData {
        return if (t is SuccessfulEventData) return IgnoredEventData()
        else if (t is IgnoredEventData) return t
        else if (isSatisfied && !reEval) return EventMetaData(
            this, false,
            if (t is EventMetaData) t.event else t,
            if (t is EventMetaData) t else null
        ) else {
            val event = eventOf(t, type) ?: return if (isSatisfied || reEval)
                EventMetaData(
                    this, false,
                    if (t is EventMetaData) t.event else t,
                    if (t is EventMetaData) t else null
                )
            else IgnoredEventData()

            isSatisfied = satisfies(event)

            if (isSatisfied && !reEval) SuccessfulEventData(t)
            else if (isSatisfied && reEval) EventMetaData(this, true, event, if (t is EventMetaData) t else null)
            else IgnoredEventData().apply {
                tailrec fun recursivelyFail(meta: EventMetaData) {
                    if (meta.ref is SatisfiableEventStage<*>) meta.ref.isSatisfied = false
                    if (meta.previous != null) recursivelyFail(meta.previous)
                }
                if (t is EventMetaData) recursivelyFail(t)
            }

        }


//        return if (t is SuccessfulEventData) IgnoredEventData()
//        else if (t is IgnoredEventData) t
//        else if (isSatisfied && !reEval) EventMetaData(
//            this,
//            false,
//            if (t is EventMetaData) t.event else t,
//            if (t is EventMetaData) t else null
//        )
//        else {
//            (if (type.isAssignableFrom(t::class.java)) t as T
//            else if (t is HierarchicalEventData && type.isAssignableFrom(t.event::class.java)) t.event as T
//            else null)?.let { actual ->
//                isSatisfied = satisfies(actual)
//
//                if (isSatisfied) if (reEval) EventMetaData(
//                    this,
//                    false,
//                    if (t is EventMetaData) t.event else t,
//                    if (t is EventMetaData) t else null
//                ) else SuccessfulEventData(actual)
//                else {
//                    IgnoredEventData().apply {
//                        tailrec fun recursivelyFail(meta: EventMetaData) {
//                            if (meta.ref is SatisfiableEventStage<*>) meta.ref.isSatisfied = false
//                            if (meta.previous != null) recursivelyFail(meta.previous)
//                        }
//                        if (t is EventMetaData) recursivelyFail(t)
//                    }
//                }
//            } ?: t


//            if (type.isAssignableFrom(t::class.java)) isSatisfied = satisfies(t as T)
//            else if (EventMetaData::class.java.isAssignableFrom(t::class.java) && type.isAssignableFrom((t as EventMetaData).event::class.java)) isSatisfied =
//                satisfies(t.event as T)

//    }
    }

    public abstract fun satisfies(data: T): Boolean
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
    private val backingType: Class<T>,
    private val predicate: BiPredicate<T, P>
) : PredicateEventStage<BinaryEventData<T, P>>(classOf()) {
    override fun apply(t: EventData): EventData {
        val eventOfT = eventOf(t)
        if (eventOfT is BinaryEventData<*, *> && !backingType.isAssignableFrom(eventOfT.event::class.java)) return IgnoredEventData()
        return super.apply(t).let {
            if (it is EventMetaData && it.event is BinaryEventData<*, *>) EventMetaData(
                this,
                it.neededEval,
                it.event.event,
                it.previous
            ) else it
        }
    }

    override fun satisfies(data: BinaryEventData<T, P>): Boolean = predicate.test(data.event, data.other)
}


//public class EventStageReference(
//    public val stage: SatisfiableEventStage<*>,
//    type: Class<out EventData>,
//    data: EventData
//) : HierarchicalEventData<EventData>(data, type)