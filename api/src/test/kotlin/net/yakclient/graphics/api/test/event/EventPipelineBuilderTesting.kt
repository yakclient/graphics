package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.EventPipelineBuilder
import org.junit.jupiter.api.Test

class EventPipelineBuilderTesting {
    @Test
    fun `Test Construction`() {
        val builder = EventPipelineBuilder()

        builder.start(TestEventOne::class.java) {
            true
        }.next(TestEventOne::class.java) {
            true
        }.checkPoint().next(TestEventTwo::class.java) {
            true
        }.supply {
            "yay!"
        }.next(TestEventTwo::class.java) { event, data ->
            "yay!" == data
        }.reEval().reEval().reEval().checkPoint().next(TestEventTwo::class.java) {
            true
        }

        val pipeline = builder.build()
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventTwo(2))
        pipeline.dispatch(TestEventTwo(3))

        println(testStageSatisfaction(pipeline))
    }
}