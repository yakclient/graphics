@file:JvmName("EventNodeKt")

package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Predicate

//public interface NodeStrategy


//public fun main() {
//    UnaryPredicateNode(KeyActionData::class.java, null, null, UnaryPredicateNode(KeyActionData::class.java, null, null, null) {
//        true
//    }) {
//        false
//    }
//}

//public fun main() {
//    chain(asdf) {
//
//    }.next(asdf) {
//
//    }.provide {
//        something
//    }.next(asdf) {
//
//    }.reEval().checkPoint().next {
//
//    }.provide {
//
//    }.next { one, two ->
//
//    }.event {
//
//    }
//}

//public fun nodesOf(nodes: List<NodeStrategy>): Node {
//    nodes.withIndex()
//        .fold<IndexedValue<NodeStrategy>, Node?>(null) { acc, it -> Node(acc, nodes.getOrNull(it.index + 1), it.value) }
//
//    fun recursivelyCreateNode(strategies: List<NodeStrategy>, previous: Node? = null, index: Int = 0): Node? {
//        Node(previous, recursivelyCreateNode(strategies))
//    }
//
//    nodes.fold(null) { acc, strategies = }
//}

//public class NodeStrategySet(
//    public val failureStrategy: FailureStrategy,
//    public val consumerStrategy: ConsumerStrategy
//)
//
//public class EventNode(
//    public val previous: EventNode?,
//    public var next: EventNode? = null,
//    private val failureStrategy: FailureStrategy,
//    private val consumerStrategy: ConsumerStrategy,
//) {
//
//    public operator fun invoke(): Unit = failureStrategy(this)
//
//    public operator fun invoke(event: EventData): Unit = consumerStrategy(this, event)
//}
//
//internal operator fun <T> Predicate<T>.invoke(t: T): Boolean = test(t)
//
//internal operator fun <T, U> BiPredicate<T, U>.invoke(t: T, u: U): Boolean = test(t, u)

//
//public sealed class TypedNode<T : EventData>(
//    child: Node?, previous: Node?, next: Node?,
//
//    private val accepts: Class<T>,
//) : Node(child, previous, next) {
//    final override fun consume(event: EventData): Unit =
//        if (accepts.isAssignableFrom(event::class.java)) consumeType(event as T) else Unit
//
//    public abstract fun consumeType(event: T)
//}
//
//internal sealed class PredicateNode<T : EventData>(
//    eventType: Class<T>, child: Node?, previous: Node?, next: Node?,
//) : TypedNode<T>(child, previous, next, eventType) {
//    private var isSatisfied: Boolean = false
//
//    override fun consumeType(event: T) {
//        if (isSatisfied) next.get()?.consume(event)
//        else isSatisfied = satisfies(event)
//    }
//
//    abstract fun satisfies(event: T): Boolean
//}
//
//internal class UnaryPredicateNode<T : EventData>(
//    eventType: Class<T>, child: Node?, previous: Node?, next: Node?,
//
//    private val predicate: Predicate<T>
//) : PredicateNode<T>(eventType, child, previous, next) {
//    override fun satisfies(event: T): Boolean = predicate.test(event)
//}
//
//internal class BinaryEventData<out E : EventData, out D>(
//    val event: E,
//    val data: D
//) : EventData
//
//internal class BinaryPredicateNode<E : EventData, P>(
//    child: Node?, previous: Node?, next: Node?,
//
//    private val predicate: BiPredicate<E, P>
//) : PredicateNode<BinaryEventData<E, P>>(classOf(), child, previous, next) {
//    override fun satisfies(event: BinaryEventData<E, P>): Boolean = predicate.test(event.event, event.data)
//}
//
//private inline fun <reified T> classOf(): Class<T> = T::class.java
//
//internal class BinaryPredicateProviderNode<T : EventData, P>(
//    eventType: Class<T>,
//    child: Node?,
//    previous: Node?,
//    next: Node?,
//
//    private val provider: Function<T, P>
//) : TypedNode<T>(child, previous, next, eventType) {
//    override fun consumeType(event: T) {
//        next.get()!!.consume(BinaryEventData(event, provider.apply(event)))
//    }
//}

//public sealed class EventNode<T: EventData>(
//    private val subscriber: Class<EventSubscriber<T>>,
//    child: Node?,
//    previous: WeakReference<out Node?>,
//    next: WeakReference<out Node?>
//) : TypedNode<T>() {
//    private var isSatisfied : Boolean = true
//
//    override fun consumeType(event: T) {
//
//    }
//}
//
//public sealed class BiPredicateNode<T>(
//
//
//    child: Node?,
//    previous: WeakReference<out Node?>,
//    next: WeakReference<out Node?>,
//) : TypedNode()

//public sealed class StrategizedNode(
//    private val failureStrategy: FailureStrategy,
//    private val dispatchStrategy: DispatchStrategy,
//
//    child: Node?,
//    previous: WeakReference<out Node?>,
//    next: WeakReference<out Node?>
//) : Node(child, previous, next) {
//    override fun consume(event: EventData) = dispatchStrategy.consume(event)
//
//}

//public abstract class EventNode<T : EventData>(
//    private val accepts: Class<T>,
//    parent: Node?,
//    previous: WeakReference<out Node?>,
//    next: WeakReference<out Node?>
//) : Node(parent,previous, next), Predicate<T> {
//    final override fun consume(data: EventData) {
//        if (accepts.isAssignableFrom(data::class.java)) {
//            this.test(data as T)
//            return
//        }
//        previous.get()?.consume(data)
//    }
//}
//
//internal class PredicateNode(
//    previous: WeakReference<out Node?>,
//    next: WeakReference<out Node?>,
//) : Node(previous, next) {
//    override fun fail() {
//
//    }
//
//    override fun consume(data: EventData) {
//
//    }
//}