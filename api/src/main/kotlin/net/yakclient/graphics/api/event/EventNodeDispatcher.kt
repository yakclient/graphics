package net.yakclient.graphics.api.event

public class EventNodeDispatcher {
    //    private val nodes: MutableMap<String, MutableSet<EventNode<*>>> = HashMap()
    private val subscribedEvents: MutableSet<String> = HashSet()
    private val nodes: MutableList<Pair<String, EventNode<*>>> = ArrayList()

    public fun <T : EventData> register(node: EventNode<T>) {
        val event = node.subscriber.name
        nodes.add(event to node)
        if (subscribedEvents.add(event)) EventManager.subscribe(node.subscriber) { dispatch(event, it) }
//
//        (nodes[event] ?: HashSet<EventNode<*>>().also { nodes[event] = it }).add(node)
    }

    private fun <T : EventData> dispatch(key: String, data: T) {
        fun EventNode<*>.recursivelyAssertSatisfaction(): Boolean =
            isSatisfied && (previous?.recursivelyAssertSatisfaction() ?: true)

        //TODO This bit seems a bit controlling as the dispatcher should 100% not mutate event nodes.
        tailrec fun EventNode<*>.recursivelyCancelSatisfaction() {
            isSatisfied = false
            previous?.recursivelyCancelSatisfaction()
        }

        for ((event, node) in nodes) {
            if (node.isSatisfied) continue
            if (event == key)
                if ((node as EventNode<T>).satisfies(data)) {
                    node(data)
                    break
                } else {
                    node.recursivelyCancelSatisfaction()
                    break
                }
        }

//        ((nodes[key] ?: HashSet<EventNode<T>>()) as? Set<EventNode<T>>
//            ?: throw IllegalStateException("Nodes for subscriber: $key must all receive the event data type of: ${data::class.java.name}")
//                ).find {
//                !it.isSatisfied && it.satisfies(data) && (it.previous?.recursivelyAssertSatisfaction() ?: true)
//            }?.invoke(data)
    }
}