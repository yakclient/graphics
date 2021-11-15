package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.EventData
import net.yakclient.graphics.api.event.EventDispatcher

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