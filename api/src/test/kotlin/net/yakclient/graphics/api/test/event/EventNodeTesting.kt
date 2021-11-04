package net.yakclient.graphics.api.test.event

import io.github.emilyydev.asp.Provides
import net.yakclient.graphics.api.event.*
import org.junit.jupiter.api.Test

class EventNodeTesting {
    @Test
    fun `Test passing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            true
        }
        val apply = stage.apply(TestEventOne())

        println(stage.isSatisfied)
        (apply as SatisfiableEventStage.EventStageReference).fail()
        println(stage.isSatisfied)
    }

    @Test
    fun `Test failing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            false
        }

        assert(stage.apply(TestEventOne()) is FailedEventData)
    }

    fun testStageSatisfaction(stage: List<SatisfiableEventStage<*>>) : List<Boolean> = stage.map { it.isSatisfied }

    @Test
    fun `Test event stage pipeline`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }
        )

        println(testStageSatisfaction(stages))

        println(EventPipeline(stages).dispatch(TestEventOne()))
    }

    @Test
    fun `Test event providing`() {
        val stage = EventProviderStage<TestEventOne, String> {
            "yay"
        }

        println(stage.apply(TestEventOne()))
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