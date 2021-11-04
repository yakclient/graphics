package net.yakclient.graphics.api.event

import java.util.*
import kotlin.collections.ArrayList

//public class EventNodeDispatcher {
//    //    private val nodes: MutableMap<String, MutableSet<EventNode<*>>> = HashMap()
//    private val nodes: MutableMap<String, MutableList<EventNode<*>>> = HashMap()
//
//    public fun <T : EventData> register(node: EventNode<T>) {
//        val event = node.subscriber.name
//        if (nodes.containsKey(event)) EventManager.subscribe(node.subscriber) { dispatch(event, it) }
//
//        (nodes[event] ?: ArrayList<EventNode<*>>().also { nodes[event] = it }).add(node)
//    }
//
//    private fun <T : EventData> dispatch(key: String, data: T) {
//        fun EventNode<*>.recursivelyAssertSatisfaction(): Boolean =
//            isSatisfied && (previous?.recursivelyAssertSatisfaction() ?: true)
//
//        //TODO This bit seems a bit controlling as the dispatcher should 100% not mutate event nodes.
//        tailrec fun EventNode<*>.recursivelyDissatisfy() {
//            isSatisfied = false
//            previous?.recursivelyDissatisfy()
//        }
//
//        ((nodes[key] ?: ArrayList()) as List<EventNode<T>>).find {
//            !it.isSatisfied && it.previous?.isSatisfied == true && !it.ignores(data)
//
////            if (it.isSatisfied || it.previous?.isSatisfied == false) return@find false
////            else if (it.ignores(data)) return@find false
////            else it(data); true
//        }
//
//        for (node in (nodes[key] ?: ArrayList()) as List<EventNode<T>>) {
//
//            if (node.isSatisfied) continue
//            if (event == key) {
//                if (node.ignores(data)) continue
//                if (node.satisfies(data)) {
//                    node(data)
//                    break
//                } else {
//                    node.recursivelyDissatisfy()
//                    break
//                }
//            }
//        }
//
////        ((nodes[key] ?: HashSet<EventNode<T>>()) as? Set<EventNode<T>>
////            ?: throw IllegalStateException("Nodes for subscriber: $key must all receive the event data type of: ${data::class.java.name}")
////                ).find {
////                !it.isSatisfied && it.satisfies(data) && (it.previous?.recursivelyAssertSatisfaction() ?: true)
////            }?.invoke(data)
//    }
//}