package net.yakclient.graphics.opengl3.util.buffer

import kotlin.test.Test

class TypedArraySpeedTests {
    @Test
    fun `Typed array of floats to float array speed test`() {
        val array = (1..1000).map(Int::toFloat).toTypedArray()

        val iterations = 1_000_000
        val startTime = System.nanoTime()

        repeat(iterations) {
            array.toFloatArray()
        }

        val endTime = System.nanoTime()

        val totalTime: Double = endTime.toDouble() - startTime.toDouble()

        println("Total time: ${totalTime * 1e-9}")
        println("Average time: $${(totalTime / iterations.toDouble()) * 1e-9}")
    }
}