package net.yakclient.graphics.api.event.stage.predicate

import net.yakclient.graphics.api.event.*
import net.yakclient.graphics.api.event.stage.EventStage
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
                if (t is EventMetaData) recursivelyFail(t)
            }

        }
    }

    public abstract fun satisfies(data: T): Boolean
}

public abstract class PredicateEventStage<T : EventData>(type: Class<T>) : SatisfiableEventStage<T>(type)

public class UnaryPredicateStage<T : EventData>(
    type: Class<T>,

    private val predicate: Predicate<T>
) : PredicateEventStage<T>(type) {
    override fun satisfies(data: T): Boolean = predicate.test(data)
}

public class BinaryPredicateStage<T : EventData, P : Any>(
    private val backingType: Class<T>,
    private val backingSuppliedType: Class<P>,
    private val predicate: BiPredicate<T, P>
) : PredicateEventStage<BinaryEventData>(BinaryEventData::class.java) {
    // TODO Probably going to have to make hierarchical event data mutable... sadly
    override fun apply(t: EventData): EventData {
        val eventOfT = eventOf(t)
        if (eventOfT is BinaryEventData && (!backingType.isAssignableFrom(eventOfT.wrapped::class.java) || !backingSuppliedType.isAssignableFrom(eventOfT.other::class.java))) return IgnoredEventData()
        return super.apply(t).also {
            if (it is WrappedEventData && it.wrapped is BinaryEventData) it.wrapped = (it.wrapped as? BinaryEventData)?.wrapped ?: it.wrapped
        }
    }

    override fun satisfies(data: BinaryEventData): Boolean = predicate.test(data.wrapped as T, data.other as P)
}