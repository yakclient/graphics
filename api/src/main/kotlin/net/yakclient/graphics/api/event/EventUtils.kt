package net.yakclient.graphics.api.event

public open class IgnoredEventData internal constructor() : EventData

public data class SuccessfulEventData internal constructor(
    override var wrapped: EventData
) : WrappedEventData

public fun eventOf(it: EventData): EventData = if (it is WrappedEventData) it.wrapped else it

public fun <T : EventData> eventOf(it: EventData, expected: Class<T>): T? {
    val eventOf = eventOf(it)
    return if (expected.isAssignableFrom(eventOf::class.java)) eventOf as T else null
}