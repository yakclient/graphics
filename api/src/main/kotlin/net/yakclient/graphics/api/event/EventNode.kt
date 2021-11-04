package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Consumer
import java.util.function.Predicate
//
//public fun interface EventFilter<in E : EventData, out F : Any> : (E) -> F
//
//public sealed class EventNode<E : EventData>(
//    internal val subscriber: Class<out EventSubscriber<E>>,
//    internal val dispatcher: EventNodeDispatcher,
//    internal val previous: EventNode<*>?,
//) : EventHook<E> {
//    private var filter: Predicate<E> = Predicate { false }
//    internal var isSatisfied: Boolean = false
//    private lateinit var eventConsumer: Consumer<E>
//
//    override fun invoke(initial: E) {
//        isSatisfied = (previous?.isSatisfied ?: true) && satisfies(initial)
//
//        if (isSatisfied && this::eventConsumer.isInitialized) {
//            tailrec fun recursivelyDissatisfy(node: EventNode<*>) {
//                node.isSatisfied = false
//                if (node.previous != null) recursivelyDissatisfy(node.previous)
//            }
//
//            eventConsumer.accept(initial)
//            recursivelyDissatisfy(this)
//        }
//    }
//
//    public fun event(event: Consumer<E>) {
//        this.eventConsumer = event
//        dispatcher.register(this)
//    }
//
//    public fun <F : Any> provide(provider: EventFilter<E, F>): BinaryEventNodeProvider<E, F> =
//        BinaryEventNodeProvider(dispatcher, provider, this)
//
//    /**
//     * false - doesnt ignore, true - ignores
//     */
//    public open fun ignore(predicate: Predicate<E>): EventNode<E> = apply { filter = predicate }
//
//    protected fun register(): Unit = dispatcher.register(this)
//
//    internal open fun ignores(initial: E): Boolean = filter.test(initial)
//
//    internal abstract fun satisfies(initial: E): Boolean
//}
//
//public class BinaryEventNodeProvider<E : EventData, F : Any>(
//    dispatcher: EventNodeDispatcher,
//    private val provider: EventFilter<E, F>,
//    private val node: EventNode<E>
//) : EventNode<E>(node.subscriber, dispatcher, node.previous) {
//    internal lateinit var datum: F
//
//    public fun <T : EventData> next(
//        subscriber: Class<out EventSubscriber<T>>,
//        predicate: BiPredicate<T, F> = java.util.function.BiPredicate { _: T, _: F -> true }
//    ): BinaryEventNode<T, F> = BinaryEventNode(dispatcher, subscriber, this, predicate).also { register() }
//
//    override fun ignore(predicate: Predicate<E>): EventNode<E> = apply { node.ignore(predicate) }
//
//    override fun satisfies(initial: E): Boolean =
//        node.satisfies(initial).also { if (it) this.datum = this.provider(initial) }
//
//    override fun ignores(initial: E): Boolean = node.ignores(initial)
//}
//
//public class UnaryEventNode<E : EventData>(
//    dispatcher: EventNodeDispatcher,
//    subscriber: Class<out EventSubscriber<E>>,
//    previous: EventNode<*>?,
//    private val predicate: Predicate<E>
//) : EventNode<E>(subscriber, dispatcher, previous) {
//    override fun satisfies(initial: E): Boolean = predicate.test(initial)
//
//    public fun <T : EventData> next(
//        subscriber: Class<out EventSubscriber<T>>,
//        predicate: Predicate<T> = Predicate { true }
//    ): UnaryEventNode<T> = UnaryEventNode(dispatcher, subscriber, this, predicate).also { register() }
//}
//
////Don't mistake this for a puny exception. It'll crash your app.
//public class BinaryEventNode<E : EventData, F : Any>(
//    dispatcher: EventNodeDispatcher,
//    subscriber: Class<out EventSubscriber<E>>,
//    previous: BinaryEventNodeProvider<*, F>,
//    private val predicate: BiPredicate<E, F> = java.util.function.BiPredicate { _, _ -> true },
//) : EventNode<E>(subscriber, dispatcher, previous) {
//    override fun satisfies(initial: E): Boolean =
//        (previous?.isSatisfied ?: true) && predicate.test(initial, (previous as BinaryEventNodeProvider<*, F>).datum)
//
//    public fun <T : EventData> next(
//        subscriber: Class<out EventSubscriber<T>>,
//        predicate: Predicate<T> = Predicate { true }
//    ): UnaryEventNode<T> = UnaryEventNode(dispatcher, subscriber, this, predicate).also { register() }
//}