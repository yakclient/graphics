package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.*
import java.util.function.BiPredicate
import java.util.function.Function
import java.util.function.Predicate

public class EventPipelineBuilder : MutableList<EventStageBuilder> by ArrayList() {
    public fun <N : EventData> start(type: Class<N>, predicate: Predicate<N>): PredicateStageBuilder<N> =
        UnaryPredicateStageBuilder(this, UnaryPredicateStage(type, predicate))

    public fun build(): EventPipeline = EventPipeline(map { it.stage })
}

public sealed class EventStageBuilder(
    protected val pipelineBuilder: EventPipelineBuilder,
) {
    init {
        pipelineBuilder.add(this)
    }

    public abstract val stage: EventStage
}

public sealed class PredicateStageBuilder<T : EventData>(
    pipeline: EventPipelineBuilder,
    override val stage: PredicateEventStage<T>
) : EventStageBuilder(pipeline) {
    public fun <N : EventData> next(type: Class<N>, predicate: Predicate<N>): PredicateStageBuilder<N> =
        UnaryPredicateStageBuilder(this.pipelineBuilder, UnaryPredicateStage(type, predicate))

    public fun reEval(shouldEval: Boolean = !stage.reEval): PredicateStageBuilder<T> =
        apply { stage.reEval = shouldEval }

    public fun checkPoint(): CheckpointStageBuilder = CheckpointStageBuilder(pipelineBuilder)

    public fun <P : Any> supply(suppliedType: Class<P>, supplier: Function<T, P>): EventSupplierStageBuilder<T, P> =
        EventSupplierStageBuilder(pipelineBuilder, suppliedType, EventSupplierStage(stage.type, supplier))
}

public class UnaryPredicateStageBuilder<T : EventData>(
    pipeline: EventPipelineBuilder,
    stage: UnaryPredicateStage<T>
) : PredicateStageBuilder<T>(pipeline, stage)

public class BinaryPredicateStageBuilder<T : EventData, P : Any>(
    pipeline: EventPipelineBuilder,
    stage: BinaryPredicateStage<T, P>
) : PredicateStageBuilder<BinaryEventData>(pipeline, stage)

public class EventSupplierStageBuilder<T : EventData, P : Any>(
    pipeline: EventPipelineBuilder,
    private val suppliedType: Class<P>,
    override val stage: EventSupplierStage<T, P>
) : EventStageBuilder(pipeline) {
    public fun <N : EventData> next(type: Class<N>, predicate: BiPredicate<N, P>): BinaryPredicateStageBuilder<N, P> =
        BinaryPredicateStageBuilder(pipelineBuilder, BinaryPredicateStage(type, this.suppliedType, predicate))
}

public class CheckpointStageBuilder(
    pipeline: EventPipelineBuilder,

    override val stage: CheckPointStage = CheckPointStage()
) : EventStageBuilder(pipeline) {
    public fun <N : EventData> next(type: Class<N>, predicate: Predicate<N>): PredicateStageBuilder<N> =
        UnaryPredicateStageBuilder(pipelineBuilder, UnaryPredicateStage(type, predicate))
}

public inline fun <T : EventData, reified P : Any> PredicateStageBuilder<T>.supply(supplier: Function<T, P>): EventSupplierStageBuilder<T, P> =
    supply(P::class.java, supplier)

public inline fun <reified N : EventData> PredicateStageBuilder<*>.next(predicate: Predicate<N>): PredicateStageBuilder<N> =
    next(N::class.java, predicate)

public inline fun <reified N : EventData> CheckpointStageBuilder.next(predicate: Predicate<N>): PredicateStageBuilder<N> =
    next(N::class.java, predicate)

public inline fun <reified N : EventData> EventPipelineBuilder.start(predicate: Predicate<N>): PredicateStageBuilder<N> =
    start(N::class.java, predicate)

public inline fun <reified N : EventData, P : Any> EventSupplierStageBuilder<*, P>.next(predicate: BiPredicate<N, P>): BinaryPredicateStageBuilder<N, P> =
    next(N::class.java, predicate)