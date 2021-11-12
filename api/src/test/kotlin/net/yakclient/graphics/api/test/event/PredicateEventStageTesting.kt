package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.*
import net.yakclient.graphics.api.event.stage.*
import org.junit.jupiter.api.Test

fun testStageSatisfaction(stage: List<EventStage>): List<Boolean> =
    stage.filterIsInstance<SatisfiableEventStage<*>>().map { it.isSatisfied }

class PredicateEventStageTesting {
    @Test
    fun `Test passing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            true
        }
        val apply = stage.apply(TestEventOne())

        println(stage.isSatisfied)
    }

    @Test
    fun `Test failing Unary event Stage`() {
        val stage = UnaryPredicateStage(TestEventOne::class.java) {
            false
        }

        assert(stage.apply(TestEventOne()) is IgnoredEventData)
    }


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
            EventSupplierStage(TestEventOne::class.java) {
                "yay"
            },
            BinaryPredicateStage<TestEventOne, String>(TestEventOne::class.java) { _, data ->
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
            }.apply { reEval = true }
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
            }.apply { reEval = true },
            UnaryPredicateStage(TestEventTwo::class.java) {
                true
            },
            CheckPointStage(),
            UnaryPredicateStage(TestEventTwo::class.java) {
                true
            },
            EventSupplierStage(TestEventTwo::class.java) {
                "fine...${it.int}"
            },
            BinaryPredicateStage<TestEventOne, String>(TestEventOne::class.java) { _, data ->
                data == "fine...3"
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }
        )
        val pipeline = EventPipeline(stages)

        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventTwo(3))
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventOne())

        println(testStageSatisfaction(stages))
    }

    @Test
    fun `Test mis-configured Example`() {
        val stages = listOf(
            CheckPointStage(),
            EventSupplierStage(TestEventTwo::class.java) { },
            UnaryPredicateStage(TestEventTwo::class.java) { true }.apply { reEval = true },
            BinaryPredicateStage<TestEventOne, Any>(TestEventOne::class.java) { first, second ->
                assert(second is Unit)
                true
            }.apply { reEval = true },
            CheckPointStage(),
            CheckPointStage(),
            UnaryPredicateStage(TestEventOne::class.java) { true }.apply { reEval = true }

        )
        val pipeline = EventPipeline(stages)

        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventOne())
        //Reasonably sure this works as expected, output should be [true, false, true] as everything is evaluable and there are basically no dependencies.
        println(testStageSatisfaction(stages))
    }
}


