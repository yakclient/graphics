package net.yakclient.graphics.api.test.event

import net.yakclient.graphics.api.event.*
import org.junit.jupiter.api.Test

class EventPipelineBuilderTesting {
    @Test
    fun `Test Construction`() {
        val builder = EventPipelineChainer()

        builder.next(testEventOne) {
            true
        }.next(testEventOne) {
            true
        }.checkPoint().next(testEventTwo) {
            true
        }.supply {
            "yay!"
        }.next(testEventTwo) { _, data ->
            "yay!" == data
        }.reEval().reEval().reEval().checkPoint().next(testEventTwo) {
            true
        }
        val pipeline = builder.toPipe()

        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventOne())
        pipeline.dispatch(TestEventTwo(1))
        pipeline.dispatch(TestEventTwo(2))
        pipeline.dispatch(TestEventTwo(3))

        println(testStageSatisfaction(pipeline))
    }


}