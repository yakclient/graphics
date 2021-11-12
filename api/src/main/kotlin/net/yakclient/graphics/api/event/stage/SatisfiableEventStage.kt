package net.yakclient.graphics.api.event.stage

import net.yakclient.graphics.api.event.*
import java.util.function.BiPredicate
import java.util.function.Predicate

public data class BinaryEventData internal constructor(
    public override var wrapped: EventData,
    public val other: Any
) : WrappedEventData


public data class EventMetaData(
    public val ref: EventStage,
    public override var wrapped: EventData,
    public val previous: EventMetaData?
) : WrappedEventData

public abstract class SatisfiableEventStage<T : EventData>(
    public val type: Class<T>,
) : EventStage {
    public var isSatisfied: Boolean = false

    public var reEval: Boolean = false

    override fun apply(t: EventData): EventData {
        if (t is SuccessfulEventData) return IgnoredEventData()
        else if (t is IgnoredEventData) return t
        else if (isSatisfied && !reEval) return EventMetaData(
            this, if (t is EventMetaData) t.wrapped else t,
            if (t is EventMetaData) t else null
        ) else {
            val event = eventOf(t, type) ?: return if (isSatisfied || reEval)
                EventMetaData(
                    this, if (t is EventMetaData) t.wrapped else t,
                    if (t is EventMetaData) t else null
                )
            else IgnoredEventData()

            isSatisfied = satisfies(event)

            return if (isSatisfied && !reEval) SuccessfulEventData(event)
            else if (isSatisfied && reEval) EventMetaData(this, event, if (t is EventMetaData) t else null)
            else IgnoredEventData().apply {
                tailrec fun recursivelyFail(meta: EventMetaData) {
                    if (meta.ref is SatisfiableEventStage<*>) meta.ref.isSatisfied = false
                    if (meta.previous != null) recursivelyFail(meta.previous)
                }
                if (t is EventMetaData) recursivelyFail(t)
            }

        }
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

//private inline fun <reified T> classOf(): Class<T> = T::class.java

//IDEAS FOR THE EVENT STUFF
// LINKED LIST:
//  There is almost a linked list of nodes(LinkedNodeDat?, it would be an actual list) that you can then search for types etc.
// Better UTILS:
//  there would be a WrappedEventData which is effectively my current hierarchical event data and utils like unwrapUntil, unwrapAll, unwrap, etc.
// Keep thinking, i gotta go to bed now....

public class BinaryPredicateStage<T : EventData, P : Any>(
    private val backingType: Class<T>,
    private val backingSuppliedType: Class<P>,
    private val predicate: BiPredicate<T, P>
) : PredicateEventStage<BinaryEventData>(BinaryEventData::class.java) {
    // TODO Probably going to have to make hierarchical event data mutable... sadly
    override fun apply(t: EventData): EventData {
        val eventOfT = eventOf(t)
        if (eventOfT is BinaryEventData && !backingType.isAssignableFrom(eventOfT.wrapped::class.java) && !backingSuppliedType.isAssignableFrom(eventOfT.other::class.java)) return IgnoredEventData()
        return super.apply(t).also {
            if (it is WrappedEventData && it.wrapped is BinaryEventData) it.wrapped = (it.wrapped as? BinaryEventData)?.wrapped ?: it.wrapped
        }
    }

    override fun satisfies(data: BinaryEventData): Boolean = predicate.test(data.wrapped as T, data.other as P)
}


//public class EventStageReference(
//    public val stage: SatisfiableEventStage<*>,
//    type: Class<out EventData>,
//    data: EventData
//) : HierarchicalEventData<EventData>(data, type)