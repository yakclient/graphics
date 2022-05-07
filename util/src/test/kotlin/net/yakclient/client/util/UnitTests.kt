package net.yakclient.client.util

import net.yakclient.graphics.util.Orthographics
import net.yakclient.graphics.util.unit.native
import kotlin.test.Test

class UnitTests {
    @Test
    fun `Test Basic Orthographic projection transformer`() {
        Orthographics.setOrtho(0.0, 500.0, 500.0, 0.0)

        val x = Orthographics.transform(0.75f.native)
        val y = Orthographics.transform(0.5f.native)

        println("${x.normalizedX} ${y.normalizedY}")
    }

}