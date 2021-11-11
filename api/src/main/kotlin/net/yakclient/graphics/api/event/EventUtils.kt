package net.yakclient.graphics.api.event

public open class IgnoredEventData internal constructor() : EventData

public data class SuccessfulEventData internal constructor(
    override val event: EventData
) : HierarchicalEventData

public fun eventOf(it: EventData): EventData = if (it is HierarchicalEventData) it.event else it

public fun <T : EventData> eventOf(it: EventData, expected: Class<T>): T? {
    val eventOf = eventOf(it)
    return if (expected.isAssignableFrom(eventOf::class.java)) eventOf as T else null
}