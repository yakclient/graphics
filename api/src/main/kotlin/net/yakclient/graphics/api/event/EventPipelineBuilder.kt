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

    public fun <P : Any> supply(supplier: Function<T, P>): EventSupplierStageBuilder<T, P> =
        EventSupplierStageBuilder(pipelineBuilder, EventSupplierStage(stage.type, supplier))
}

public class UnaryPredicateStageBuilder<T : EventData>(
    pipeline: EventPipelineBuilder,
    stage: UnaryPredicateStage<T>
) : PredicateStageBuilder<T>(pipeline, stage)

public class BinaryPredicateStageBuilder<T : EventData, P : Any>(
    pipeline: EventPipelineBuilder,
    stage: BinaryPredicateStage<T, P>
) : PredicateStageBuilder<BinaryEventData<T, P>>(pipeline, stage)

public class EventSupplierStageBuilder<T : EventData, P : Any>(
    pipeline: EventPipelineBuilder,
    override val stage: EventSupplierStage<T, P>
) : EventStageBuilder(pipeline) {
    public fun <N : EventData> next(type: Class<N>, predicate: BiPredicate<N, P>): BinaryPredicateStageBuilder<N, P> =
        BinaryPredicateStageBuilder(pipelineBuilder, BinaryPredicateStage(type, predicate))
}

public class CheckpointStageBuilder(
    pipeline: EventPipelineBuilder,

    override val stage: CheckPointStage = CheckPointStage()
) : EventStageBuilder(pipeline) {
    public fun <N : EventData> next(type: Class<N>, predicate: Predicate<N>): PredicateStageBuilder<N> =
        UnaryPredicateStageBuilder(pipelineBuilder, UnaryPredicateStage(type, predicate))
}