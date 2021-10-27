package net.yakclient.graphics.api.test.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*
import org.junit.jupiter.api.Test

class EventNodeTesting {
    @Test
    fun `Test the Unary event node with a single predicate`() {
        EventManager.forceLoad(TestEventOneSubscriber())
        val subscriber = TestEventOneSubscriber::class.java
        UnaryEventNode(EventNodeDispatcher(), subscriber, null) {
            true
        }.event {
            println("Event got through")
        }

        TickManager.tickThem()
    }

    @Test
    fun `Test with multiple predicates`() {
        EventManager.forceLoad(TestEventOneSubscriber())
        val subscriber = TestEventOneSubscriber::class.java
        UnaryEventNode(EventNodeDispatcher(), subscriber, null) {
            true
        }.next(subscriber) {
            true
        }.event {
            println("Did this get through????")
        }

        TickManager.tickThem()
        TickManager.tickThem()

    }

    @Test
    fun `Test with multiple predicates and filtering`() {
        EventManager.forceLoad(TestEventOneSubscriber())
        val subscriber = TestEventOneSubscriber::class.java
        UnaryEventNode(EventNodeDispatcher(), subscriber, null) {
            true
        }.filter {
            System.currentTimeMillis()
        }.next(subscriber) { event, filter ->
            println(System.currentTimeMillis() - filter)
            System.currentTimeMillis() - filter < 1000
        }.event {
            println("Yay!")
        }

        TickManager.tickThem()
        TickManager.tickThem()
//        TickManager.tickThem()

    }
}

class TestEventOne : EventData

@Provides(EventSubscriber::class)
class TestEventOneSubscriber : EventSubscriber<TestEventOne>(), Ticking {
    override fun hook() {
        TickManager.register(this)
    }

    override fun tick() {
        notify(TestEventOne())
    }
}