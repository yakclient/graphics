package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.EventPipeline
import net.yakclient.graphics.api.event.stage.predicate.*
import org.junit.jupiter.api.Test

inline fun runSpeedTest(runnable: () -> Unit) {
    val start = System.nanoTime()
    val iterations = 100000
    for (i in 0 until iterations) {
        val startIteration = System.nanoTime()
        runnable()
        println("Iteration $i took ${System.nanoTime() - startIteration} nanos")
    }
    println("\nIterations complete")
    println("Total time was ${System.nanoTime() - start} nanos")
    println("Average time was ${(System.nanoTime() - start) / iterations} nanos")
}

class EventStageSpeedTesting {
    //5070
    @Test
    fun `Test speed of a single unary predicate`() = runSpeedTest {
        UnaryPredicateStage(TestEventOne::class.java) {
            true
        }.apply(TestEventOne())
    }

    @Test
    fun `Test speed of a predicate ignoring an event based on type`() = runSpeedTest {
        UnaryPredicateStage(TestEventOne::class.java) {
            true
        }.apply(TestEventTwo(1))
    }

    //10051
    @Test
    fun `Test speed of multiple predicates and a consuming event`() {
        val pipeline = EventPipeline(listOf(
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            UnaryPredicateStage(TestEventTwo::class.java) {
                true
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            },
            EventConsumingStage(TestEventTwo::class.java) {
                println("yay ${it.int}")
            }
        ))
        runSpeedTest {
            pipeline.dispatch(TestEventOne())
            pipeline.dispatch(TestEventTwo(5))
            pipeline.dispatch(TestEventOne())
            pipeline.dispatch(TestEventTwo(1))

            pipeline.filterIsInstance<PredicateEventStage<*>>().forEach { it.isSatisfied = false }
        }
    }

    @Test
    fun `Test speed of complicated example`() {
        val pipeline = EventPipeline(listOf(
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
            BinaryPredicateStage(TestEventOne::class.java, String::class.java) { _, data ->
                data == "fine...3"
            },
            UnaryPredicateStage(TestEventOne::class.java) {
                true
            }
        ))
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventTwo(3))
        pipeline.dispatch(TestEventOne())

        runSpeedTest {
            pipeline.dispatch(TestEventTwo(1))
        }
        println(testStageSatisfaction(pipeline))
    }
}