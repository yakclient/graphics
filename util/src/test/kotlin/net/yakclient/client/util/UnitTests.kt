package net.yakclient.client.util

import net.yakclient.graphics.util.OrthographicTransformer
import net.yakclient.graphics.util.unit.native
import kotlin.test.Test

class UnitTests {
    @Test
    fun `Test Basic Orthographic projection transformer`() {
        OrthographicTransformer.setOrtho(0, 500, 500, 0)

        val x = OrthographicTransformer.transform(0.75f.native)
        val y = OrthographicTransformer.transform(0.5f.native)

        println("${x.asX} ${y.asY}")
    }

}