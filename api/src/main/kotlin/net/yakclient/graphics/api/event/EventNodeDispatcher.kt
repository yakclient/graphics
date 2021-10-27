package net.yakclient.graphics.api.event

public class EventNodeDispatcher {
    private val nodes: MutableMap<String, MutableSet<EventNode<*>>> = HashMap()

    public fun <T : EventData> register(node: EventNode<T>) {
        val event = node.subscriber.name

        if (!nodes.containsKey(event)) EventManager.subscribe(node.subscriber) { dispatch(event, it) }

        (nodes[event] ?: HashSet<EventNode<*>>().also { nodes[event] = it }).add(node)
    }

    private fun <T : EventData> dispatch(key: String, data: T) {
        fun EventNode<*>.recursivelyAssertSatisfaction(): Boolean =
            isSatisfied && (previous?.recursivelyAssertSatisfaction() ?: true)

        ((nodes[key] ?: HashSet<EventNode<T>>()) as? Set<EventNode<T>>
              ?: throw IllegalStateException("Nodes for subscriber: $key must all receive the event data type of: ${data::class.java.name}")
        ).find {
                !it.isSatisfied && it.satisfies(data) && (it.previous?.recursivelyAssertSatisfaction() ?: true)
        }?.invoke(data)
    }
}