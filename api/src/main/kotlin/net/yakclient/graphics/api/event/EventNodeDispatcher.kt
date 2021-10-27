package net.yakclient.graphics.api.event

public class EventNodeDispatcher {
    private val nodes: MutableMap<String, MutableSet<EventNode<*>>> = HashMap()
    private lateinit var last: EventNode<*>

    public fun <T : EventData> register(node: EventNode<T>) {
        val event = node.subscriber.name
        last = node

        if (!nodes.containsKey(event)) EventManager.subscribe(node.subscriber) { dispatch(event, it) }

        (nodes[event] ?: HashSet<EventNode<*>>().also { nodes[event] = it }).add(node)
    }

    private fun <T : EventData> dispatch(key: String, data: T) {
        fun EventNode<*>.recursivelyAssertSatisfaction(): Boolean =
            isSatisfied && (previous?.recursivelyAssertSatisfaction() ?: true)

        //TODO This bit seems a bit controlling as the dispatcher should 100% not mutate event nodes.
        tailrec fun EventNode<*>.recursivelyCancelSatisfaction() {
            isSatisfied = false
            previous?.recursivelyCancelSatisfaction()
        }

        ((nodes[key] ?: HashSet<EventNode<T>>()) as? Set<EventNode<T>>
            ?: throw IllegalStateException("Nodes for subscriber: $key must all receive the event data type of: ${data::class.java.name}")
                ).find {
                !it.isSatisfied && it.satisfies(data) && (it.previous?.recursivelyAssertSatisfaction() ?: true)
            }?.invoke(data) ?: last.recursivelyCancelSatisfaction()
    }
}