package net.yakclient.graphics.lwjgl.test

import net.yakclient.graphics.lwjgl.render.interleaveAfter
import net.yakclient.graphics.util.buffer.safeFloatBufOf
import kotlin.test.Test

class TestBatcher {
    @Test
    fun `Test interleaving`() {
        val buf = safeFloatBufOf(floatArrayOf(
            10f, 9f, 20f, 19f, 30f, 29f, 40f, 39f, 50f, 49f
        ))

        val interleaved =buf.interleaveAfter(2, 1) {
            0f
        }

        println(buf)
    }
}