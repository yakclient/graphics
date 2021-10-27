package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Consumer
import java.util.function.Predicate

public fun interface EventFilter<in E : EventData, out F : FilterData> : (E) -> F

public interface FilterData

public abstract class EventNode<E : EventData>(
    internal val subscriber: Class<out EventSubscriber<E>>,
    internal val dispatcher: EventNodeDispatcher,
    internal val previous: EventNode<*>?,
) : EventHook<E> {
    internal var isSatisfied: Boolean = false
    private lateinit var eventConsumer: Consumer<E>

    override fun invoke(initial: E) {
        isSatisfied = (previous?.isSatisfied ?: true) && satisfies(initial)

        if (isSatisfied && this::eventConsumer.isInitialized) eventConsumer.accept(initial)
    }

    public fun event(event: Consumer<E>) {
        this.eventConsumer = event
        dispatcher.register(this)
    }

    public fun <F : FilterData> filter(filter: EventFilter<E, F>): FilteredEventNode<E, F> =
        FilteredEventNode(dispatcher, filter, this)

    protected fun register(): Unit = dispatcher.register(this)

    public abstract fun satisfies(initial: E): Boolean
}

public class FilteredEventNode<E : EventData, F : FilterData>(
    dispatcher: EventNodeDispatcher,
    private val filter: EventFilter<E, F>,
    private val node: EventNode<E>
) : EventNode<E>(node.subscriber, dispatcher, node.previous) {
    internal lateinit var datum: F

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: BiPredicate<T, F> = java.util.function.BiPredicate { _: T, _: F -> true }
    ): BinaryEventNode<T, F> = BinaryEventNode(dispatcher, subscriber, this, predicate).also { register() }

    override fun satisfies(initial: E): Boolean = node.satisfies(initial).also { this.datum = this.filter(initial) }
}

public class UnaryEventNode<E : EventData>(
    dispatcher: EventNodeDispatcher,
    subscriber: Class<out EventSubscriber<E>>,
    previous: EventNode<*>?,
    private val predicate: Predicate<E>
) : EventNode<E>(subscriber, dispatcher, previous) {
    override fun satisfies(initial: E): Boolean = predicate.test(initial)

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: Predicate<T> = Predicate { true }
    ): UnaryEventNode<T> = UnaryEventNode(dispatcher, subscriber, this, predicate).also { register() }
}

//Don't mistake this for a puny exception. It'll crash your app.
public class BinaryEventNode<E : EventData, F : FilterData>(
    dispatcher: EventNodeDispatcher,
    subscriber: Class<out EventSubscriber<E>>,
    previous: FilteredEventNode<*, F>,
    private val predicate: BiPredicate<E, F> = java.util.function.BiPredicate { _, _ -> true },
) : EventNode<E>(subscriber, dispatcher, previous) {
    override fun satisfies(initial: E): Boolean =
        (previous?.isSatisfied ?: true) && predicate.test(initial, (previous as FilteredEventNode<*, F>).datum)

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: Predicate<T> = Predicate { true }
    ): UnaryEventNode<T> = UnaryEventNode(dispatcher, subscriber, this, predicate).also { register() }
}