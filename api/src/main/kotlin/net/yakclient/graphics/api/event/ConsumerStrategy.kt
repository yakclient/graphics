package net.yakclient.graphics.api.event

import java.util.function.BiPredicate
import java.util.function.Predicate

// What should happen - The nodes are a pipeline, what this means is that anything that comes in the start MUST go out the back, each node will however, transform the data coming in. For the filtering/providing system this means that a filter
// will wrap the data with a class that contains another value, then the binary node will unwrap that value, at the very least always returning the wrapped value to the next node(for example, if already completed or something).  For cancelling
// data the only solution i can think of right now is to always wrap the event data in a CancellableNodeReference or something where you can cancel the node that it references(bad, but its ok).  Then for checkpoints there will be a node that
// effectively unwraps the cancellable node references and then just returns the original.  The implementation for the cancellable reference might be a bit wonky, so we might just not do that idea or you can find something else.  Ok im gonna
// work on my baldwin essay for 15 min, good luck tomorrow.

//public fun interface ConsumerStrategy {
//    public fun accept(node: EventNode, data: EventData)
//
//}
//
//public operator fun ConsumerStrategy.invoke(node: EventNode, data: EventData): Unit = accept(node, data)
//
////public typealias ConsumerStrategy = (Node,EventData) -> Unit
//
//public sealed class InheritedConsumerStrategy(
//    private val parent: ConsumerStrategy?
//) : ConsumerStrategy {
//    override fun accept(node: EventNode, data: EventData): Unit =
//        parent?.accept(node, data) ?: Unit
//}
//
//public sealed class PerpetuatedConsumerStrategy : ConsumerStrategy {
//    override fun accept(node: EventNode, data: EventData) {
//        node.next?.invoke(data)
//    }
//}
//
//
////public class PerpetuatedConsumerStrategy : ConsumerStrategy {
////    override fun accept(node: Node, data: EventData): Unit = node.next?.invoke(data) ?: Unit
////}
//
//public sealed class TypedConsumerStrategy<T : EventData>(
//    private val type: Class<T>,
//) : ConsumerStrategy {
//    final override fun accept(node: EventNode, data: EventData) {
//        if (type.isAssignableFrom(data::class.java)) acceptAs(node, data as T)
//    }
//
//    public abstract fun acceptAs(node: EventNode, data: T)
//}
//
//internal sealed class PredicateConsumerStrategy<T : EventData>(
//    type: Class<T>,
//    private val reEval: Boolean = false
//) : TypedConsumerStrategy<T>(type) {
//    private var isSatisfied: Boolean = false
//
//    override fun acceptAs(node: EventNode, data: T) {
//        if (!isSatisfied || reEval) {
//            isSatisfied = satisfies(data)
//            if (reEval) node.next?.invoke(data)
//        } else node.next?.invoke(data)
//    }
//
//    abstract fun satisfies(data: T): Boolean
//}
//
//public class UnaryPredicateConsumerStrategy<T : EventData>(
//    type: Class<T>,
//    private val predicate: Predicate<T>
//) : TypedConsumerStrategy<T>(type) {
//    override fun acceptAs(node: EventNode, data: T) {
//        if (predicate(data)) node.next?.invoke(data)
//    }
//}
//
////internal class BinaryEventData<out E : EventData, out D>(
////    val event: E,
////    val data: D
////) : EventData
//
////
////internal class BinaryPredicateNode<E : EventData, P>(
////    child: Node?, previous: Node?, next: Node?,
////
////    private val predicate: BiPredicate<E, P>
////) : PredicateNode<BinaryEventData<E, P>>(classOf(), child, previous, next) {
////    override fun satisfies(event: BinaryEventData<E, P>): Boolean = predicate.test(event.event, event.data)
////}
////
//private inline fun <reified T> classOf(): Class<T> = T::class.java
//
//internal class BinaryPredicateConsumerStrategy<T : EventData, P>(
//    private val predicate: BiPredicate<T, P>
//) : PredicateConsumerStrategy<BinaryEventData<T, P>>
//    (classOf()) {
//    override fun satisfies(data: BinaryEventData<T, P>): Boolean = predicate(data.event, data.data)
//}