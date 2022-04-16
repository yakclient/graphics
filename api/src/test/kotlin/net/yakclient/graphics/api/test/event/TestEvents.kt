package net.yakclient.graphics.api.test.event

import net.yakclient.event.api.EventData
import net.yakclient.event.api.EventDispatcher

val testEventOne : Class<TestEventOneDispatcher> = TestEventOneDispatcher::class.java
val testEventTwo : Class<TestEventTwoDispatcher> = TestEventTwoDispatcher::class.java

class TestEventOneDispatcher : EventDispatcher<TestEventOne>() {
    override val eventType: Class<TestEventOne>
        get() = TestEventOne::class.java
}

class TestEventTwoDispatcher : EventDispatcher<TestEventTwo>() {
    override val eventType: Class<TestEventTwo>
        get() = TestEventTwo::class.java
}

class TestEventTwo(
    val int: Int
) : EventData

class TestEventOne : EventData