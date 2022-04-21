package net.yakclient.client.util

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.test.BeforeTest
import kotlin.test.Test

class TextureLoadingTests {
    @BeforeTest
    fun init() {
        System.setProperty("java.awt.headless", "true")
    }

    @Test
    fun `Test texture packing`() {
        val ins = (0..25).map {
            BufferedImage(
                (Math.random() * 100).toInt() + 1,
                (Math.random() * 100).toInt() + 1,
                BufferedImage.TYPE_INT_RGB
            )
        }.onEach {
            val graphics = it.graphics

            graphics.color = Color(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat())
            graphics.fillRect(0, 0, it.width, it.height)
            graphics.dispose()
        }

        val images = ins.sortedByDescending(BufferedImage::getHeight)
        val bestArea = images.fold(0f) { area, image -> area + (image.width * image.height) }
        val length = sqrt(bestArea)

        val maxTexSize = 20

        val n = ceil(log(length, 2f)).toInt()

        var buf: BufferedImage? = null

        completionLoop@ for (i in n until maxTexSize) {
            val pow2Length: Int = 2.0.pow(i).toInt()

            println("Here too")

            val tempBuf = BufferedImage(pow2Length, pow2Length, BufferedImage.TYPE_INT_RGB)
            val graphics = tempBuf.graphics

            var height = 0
            var rowHeight = 0
            var x = 0

            for (it in images) {
                if (it.height > height) height = it.height
                if ((rowHeight + height) > pow2Length) {
                    println("AHAHAH")
                    graphics.dispose()
                    continue@completionLoop
                }
                if (x + it.width > pow2Length) {
                    rowHeight += height
                    height = it.height
                    x = 0
                }

                graphics.drawImage(it, x, rowHeight, null)

                x += it.width
            }

            buf = tempBuf

//            graphics.dispose()

            break
        }

        checkNotNull(buf) {"Buf must not be null"}

        println("here")
    }
}