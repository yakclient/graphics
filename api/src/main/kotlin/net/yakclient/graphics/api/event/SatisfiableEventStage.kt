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
        // TODO logic about re-evaluation and stuff
        if (isSatisfied) EventStageReference(this, t::class.java, t)
        else {
            if (type.isAssignableFrom(t::class.java)) isSatisfied = satisfies(t as T)
            else if (EventStageReference::class.java.isAssignableFrom(t::class.java) && type.isAssignableFrom((t as EventStageReference).type)) isSatisfied =
                satisfies(t.event as T)
            if (isSatisfied) EventStageReference(this, t::class.java, t) else FailedEventData().apply {
                tailrec fun recursivelyFail(ref: EventStageReference) {
                    ref.fail()
                    if (EventStageReference::class.java.isAssignableFrom(ref.type)) recursivelyFail(ref.event as EventStageReference)
                }

                if (EventStageReference::class.java.isAssignableFrom(t::class.java)) recursivelyFail(t as EventStageReference)
            }
        }

    public abstract fun satisfies(data: T): Boolean

    public class EventStageReference(
        private val stage: SatisfiableEventStage<*>,
        public val type: Class<out EventData>,
        data: EventData
    ) : HierarchicalEventData<EventData>(data) {
        public fun fail(): Unit = Unit.also { stage.isSatisfied = false }
    }
}

public fun <T : EventData> eventOf(event: EventData) : T {
    assert(event !is FailedEventData)
    return if (event is SatisfiableEventStage.EventStageReference) event.event as T
    else event as T
}

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
    override fun apply(t: EventData): EventData =
        super.apply(t)
            .let { if (BinaryEventData::class.java.isAssignableFrom(t::class.java)) (it as BinaryEventData<*, *>).event else it }

    override fun satisfies(data: BinaryEventData<T, P>): Boolean = predicate.test(data.event, data.other)
}