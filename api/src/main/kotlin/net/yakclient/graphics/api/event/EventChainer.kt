package net.yakclient.graphics.api.event

import net.yakclient.graphics.api.event.stage.EventStage
import net.yakclient.graphics.api.event.stage.predicate.*
import java.util.function.BiPredicate
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate

/**
 * The basis of the EventChainer, creates a pipeline from EventStages, following
 * the pattern of chained predicate stages.
 *
 * Example:
 * ```
 * EventPipelineChainer().start(MyEventClass::class.java) { ... }.toPipe().dispatch(...)
 * ```
 *
 * @see EventPipeline
 * @since 1.0
 */
public class EventPipelineChainer : MutableList<EventStageChainer> by ArrayList(), UnaryPreceding, CanIgnore {
    override val pipeline: EventPipelineChainer = this
    internal val events: MutableMap<String, Class<out EventDispatcher<*>>> = HashMap()

    public fun toPipe(): EventPipeline = EventPipeline(map { it.stage })
}

/**
 * A private interface for holding information about a parent pipeline chainer.
 *
 * @see EventStageChainer
 * @since 1.0
 */
public interface PipelineHolder {
    public val pipeline: EventPipelineChainer
}

/**
 * Provides a convenience method to add UnaryPredicateStages to the current pipeline.
 */
public interface UnaryPreceding : PipelineHolder {
    public fun <N : EventData> next(
        type: Class<out EventDispatcher<N>>,
        predicate: Predicate<N>
    ): PredicateStageChainer<N> =
        UnaryPredicateStageChainer(
            this.pipeline,
            UnaryPredicateStage(EventDispatchManager.typeOf(type), predicate)
        ).also { pipeline.events[type.name] = type }
}

/**
 * Provides a convenience method to add IgnoringEventStages to the current pipeline.
 */
public interface CanIgnore : PipelineHolder {
    /**
     * In this case there is no reason to take a EventDispatcher since ignoring should require no new events
     * to be processed.
     */
    public fun <T : EventData> ignore(type: Class<T>, predicate: Predicate<T>): IgnoringEventStageChainer =
        IgnoringEventStageChainer(pipeline, IgnoringEventStage(type, predicate))
}

/**
 * Provides a convenience method to add Checkpoints to the current pipeline.
 */
private interface CheckPointProceeding : PipelineHolder {
    fun checkPoint(): CheckpointStageChainer = CheckpointStageChainer(pipeline)
}

/**
 * Provides a convenience method to add event consumers to the current pipeline.
 */
private interface EventConsuming : PipelineHolder {
    /**
     * Creates and adds an event consuming chainer to the current pipeline.
     *
     * @param type The type of event to consume.
     * @param consumer The consumer to accept the event.
     *
     * @return The event consuming chainer.
     */
    fun <T : EventData> event(type: Class<out EventDispatcher<T>>, consumer: Consumer<T>): EventConsumingStageChainer =
        EventConsumingStageChainer(
            pipeline,
            EventConsumingStage(EventDispatchManager.typeOf(type), consumer)
        ).also { pipeline.events[type.name] = type }

    /**
     * Creates and adds an event consuming chainer to the current pipeline.
     *
     * @param type The type of event to consume.
     * @param runnable The runnable to run when events are consumed.
     *
     * @return The event consuming chainer.
     */
    fun <T : EventData> event(type: Class<out EventDispatcher<T>>, runnable: Runnable): EventConsumingStageChainer =
        EventConsumingStageChainer(
            pipeline,
            EventConsumingStage(EventDispatchManager.typeOf(type)) { runnable.run() }).also { pipeline.events[type.name] = type }
}

/**
 * The basis for all chainer's representing EventStages.
 *
 * @constructor constructs a new chainer given the current pipeline.
 */
public sealed class EventStageChainer(
    override val pipeline: EventPipelineChainer,
) : PipelineHolder {
    init {
        pipeline.add(this)
    }

    public abstract val stage: EventStage
}

/**
 * The base chainer representing all predicate stages.
 *
 * @param T the type of event data that is tested against.
 * @constructor Constructs a predicate stage chainer with the given pipeline and stage.
 */
public sealed class PredicateStageChainer<T : EventData>(
    pipeline: EventPipelineChainer,
    override val stage: PredicateEventStage<T>,
) : EventStageChainer(pipeline), UnaryPreceding, CanIgnore, CheckPointProceeding, EventConsuming {
    /**
     * Sets the re-eval property for this stage to the opposite or to
     * the value provided when calling.
     *
     * @param shouldEval If the stage should re-eval event data, set by default to the opposite of the current state.
     */
    @JvmOverloads
    public fun reEval(shouldEval: Boolean = !stage.reEval): PredicateStageChainer<T> =
        apply { stage.reEval = shouldEval }

    /**
     * Creates and returns an EventSupplyingStage.
     *
     * @param P The type that is supplied.
     * @param suppliedType The class type that is supplied
     * @param supplier The supplier function to provide type P.
     *
     * @see EventSupplierStageChainer
     * @see EventSupplierStage
     */
    public fun <P : Any> supply(suppliedType: Class<P>, supplier: Function<T, P>): EventSupplierStageChainer<T, P> =
        EventSupplierStageChainer(pipeline, suppliedType, EventSupplierStage(stage.type, supplier))
}

public class UnaryPredicateStageChainer<T : EventData>(
    pipeline: EventPipelineChainer,
    stage: UnaryPredicateStage<T>,
) : PredicateStageChainer<T>(pipeline, stage)

public class BinaryPredicateStageChainer<T : EventData, P : Any>(
    pipeline: EventPipelineChainer,
    stage: BinaryPredicateStage<T, P>,
) : PredicateStageChainer<BinaryEventData>(pipeline, stage)

public class EventSupplierStageChainer<T : EventData, P : Any>(
    pipeline: EventPipelineChainer,
    private val suppliedType: Class<P>,
    override val stage: EventSupplierStage<T, P>
) : EventStageChainer(pipeline) {
    public fun <N : EventData> next(
        type: Class<out EventDispatcher<N>>,
        predicate: BiPredicate<N, P>
    ): BinaryPredicateStageChainer<N, P> =
        BinaryPredicateStageChainer(
            pipeline,
            BinaryPredicateStage(EventDispatchManager.typeOf(type), this.suppliedType, predicate)
        ).also { pipeline.events[type.name] = type }
}

public class CheckpointStageChainer(
    pipeline: EventPipelineChainer,

    override val stage: CheckPointStage = CheckPointStage()
) : EventStageChainer(pipeline), UnaryPreceding, EventConsuming

public class EventConsumingStageChainer(
    pipeline: EventPipelineChainer,
    override val stage: EventConsumingStage<*>,
) : EventStageChainer(pipeline), UnaryPreceding, CheckPointProceeding, CanIgnore, EventConsuming

public class IgnoringEventStageChainer(
    pipeline: EventPipelineChainer,
    override val stage: IgnoringEventStage<*>,
) : EventStageChainer(pipeline), UnaryPreceding, EventConsuming

public class CustomStageChainer(
    pipeline: EventPipelineChainer,
    override val stage: EventStage
) : EventStageChainer(pipeline), UnaryPreceding, CheckPointProceeding, CanIgnore, EventConsuming

public inline fun <T : EventData, reified P : Any> PredicateStageChainer<T>.supply(supplier: Function<T, P>): EventSupplierStageChainer<T, P> =
    supply(P::class.java, supplier)

public inline fun <reified E : EventData> CanIgnore.ignore(predicate: Predicate<E>): IgnoringEventStageChainer =
    ignore(E::class.java, predicate)