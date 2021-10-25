package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Consumer
import java.util.function.Predicate

public fun interface EventFilter<in E : EventData, out F : FilterData> : (E) -> F

public interface FilterData

public abstract class EventNode<E : EventData>(
    internal val subscriber: Class<out EventSubscriber<E>>,
    public val previous: EventNode<*>? = null
) : EventHook<E> {
    private var isSatisfied: Boolean = false
    private lateinit var eventConsumer: Consumer<E>

    override fun invoke(initial: E) {
        isSatisfied = (previous?.isSatisfied ?: true) && satisfies(initial)

        if (isSatisfied && this::eventConsumer.isInitialized) eventConsumer.accept(initial)
    }

    public fun event(event: Consumer<E>) {
        this.eventConsumer = event
        EventManager.subscribe(subscriber, this)
    }

    public fun <F : FilterData> filter(filter: EventFilter<E, F>): FilteredEventNode<E, F> =
        FilteredEventNode(filter, this)

    public abstract fun satisfies(initial: E): Boolean
}

public class FilteredEventNode<E : EventData, F : FilterData>(
    private val filter: EventFilter<E, F>,
    private val node: EventNode<E>
) : EventNode<E>(node.subscriber, node.previous) {
    internal lateinit var datum: F

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: BiPredicate<T, F> = java.util.function.BiPredicate { _: T, _: F -> true }
    ): BinaryEventNode<T, F> =
        BinaryEventNode(subscriber, this, predicate).also { EventManager.subscribe(this.subscriber, this) }

    override fun satisfies(initial: E): Boolean =
        node.satisfies(initial).also { this.datum = this.filter(initial) }
}

public class UnaryEventNode<E : EventData>(
    subscriber: Class<out EventSubscriber<E>>,
    previous: EventNode<*>? = null,
    private val predicate: Predicate<E>
) : EventNode<E>(subscriber, previous) {
    override fun satisfies(initial: E): Boolean {
        return predicate.test(initial)
    }

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: Predicate<T> = Predicate { true }
    ): UnaryEventNode<T> {
        return UnaryEventNode(subscriber, this, predicate).also { EventManager.subscribe(this.subscriber, this) }
    }
}

//Don't mistake this for a puny exception. It'll crash your app.
public class BinaryEventNode<E : EventData, F : FilterData>(
    subscriber: Class<out EventSubscriber<E>>,
    previous: FilteredEventNode<*, F>,
    private val predicate: BiPredicate<E, F> = java.util.function.BiPredicate { _, _ -> true },
) : EventNode<E>(subscriber, previous) {
    override fun satisfies(initial: E): Boolean = predicate.test(initial, (previous as FilteredEventNode<*, F>).datum)

    public fun <T : EventData> next(
        subscriber: Class<out EventSubscriber<T>>,
        predicate: Predicate<T> = Predicate { true }
    ): UnaryEventNode<T> =
        UnaryEventNode(subscriber, this, predicate).also { EventManager.subscribe(this.subscriber, this) }
}