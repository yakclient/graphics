package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.*
import org.junit.jupiter.api.Test
import java.lang.invoke.MethodHandles
import java.lang.reflect.Field


class PredicateEventStageTesting {
    @Test
    fun `Test passing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            true
        }
        val apply = stage.apply(TestEventOne())

        println(stage.isSatisfied)
        ((apply as EventMetaData).ref as SatisfiableEventStage<*>).isSatisfied = false
        println(stage.isSatisfied)
    }

    @Test
    fun `Test failing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            false
        }

        assert(stage.apply(TestEventOne()) is IgnoredEventData)
    }

    fun testStageSatisfaction(stage: List<EventStage>): List<Boolean> =
        stage.filterIsInstance<SatisfiableEventStage<*>>().map { it.isSatisfied }

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
    fun `Test if events are ignored after satisfaction of stage`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }
        )

        val pipeline = EventPipeline(stages)
        println(pipeline.dispatch(TestEventOne()))
        println(testStageSatisfaction(stages))
        println(pipeline.dispatch(TestEventOne()))
        println(testStageSatisfaction(stages))
    }

    @Test
    fun `Test event providing and binary consuming`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventOne::class.java) { true },
            EventProviderStage(TestEventOne::class.java) {
                "yay"
            },
            BinaryPredicateStage<TestEventOne, String> { _, data ->
                println(data)
                data == "yay"
            }
        )

        for (i in 1..2) EventPipeline(stages).dispatch(TestEventOne())
        println()

        println(testStageSatisfaction(stages))
    }

    @Test
    fun `Test checkpoint stage`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            CheckPointStage(),

            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                false
            },
        )

        val pipeline = EventPipeline(stages)
        //Expected: true, true, true, true, false, false, false, false
        for (i in 0..7) pipeline.dispatch(TestEventOne())
        println(testStageSatisfaction(stages))
    }

    @Test
    fun `Test re-evaluation`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventTwo::class.java) {
                it.int == 2
            }.apply { `re-eval` = true }
        )

        val pipeline = EventPipeline(stages)

        //Expected false, true, false

        pipeline.dispatch(TestEventTwo(1))
        println(testStageSatisfaction(stages))

        pipeline.dispatch(TestEventTwo(2))
        println(testStageSatisfaction(stages))

        pipeline.dispatch(TestEventTwo(3))
        println(testStageSatisfaction(stages))

    }

    @Test
    fun `Assert event is ignored`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) { true }

        assert(stage.apply(TestEventTwo(1)) is IgnoredEventData)
    }

    @Test
    fun `Test complicated example`() {
        val stages = listOf(
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }.apply { `re-eval` = true },
            UnaryPredicateStage(TestEventTwo::class.java) {
                true
            },
            CheckPointStage(),
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            EventProviderStage(TestEventTwo::class.java) {
                "fine...${it.int}"
            },
            BinaryPredicateStage<TestEventOne, String> { _, data ->
                data == "fine...3"
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }
        )

        val pipeline = EventPipeline(stages)

        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(3))
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventOne())

        testStageSatisfaction(stages)
    }
}


class TestEventOne : EventData

class TestEventTwo(
    val int: Int
) : EventData