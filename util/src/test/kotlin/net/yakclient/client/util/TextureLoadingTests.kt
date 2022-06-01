package net.yakclient.client.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.Executors
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
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

        checkNotNull(buf) { "Buf must not be null" }

        println("here")
    }


    private data class PackedTexture(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int
    )

    @Test
    fun `Pack textures with max texture size`() {
        val maxSize = 16_000

        val ins = (0..250).asSequence().map {
            BufferedImage(
                (Math.random() * 25).toInt() + 1,
                (Math.random() * 25).toInt() + 1,
                BufferedImage.TYPE_INT_RGB
            )
        }.onEach {
            val graphics = it.graphics

            graphics.color = Color(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat())
            graphics.fillRect(0, 0, it.width, it.height)
            graphics.dispose()
        }

        val images = ins.sortedByDescending(BufferedImage::getHeight)
//        val bestArea = images.fold(0f) { area, image -> area + (image.width * image.height) }

        val length = sqrt(maxSize.toFloat()).toInt()

        val output = ArrayList<Pair<BufferedImage, List<PackedTexture>>>()

        var buf = BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB)
        val graphics by object : ReadOnlyProperty<Nothing?, Graphics> {
            private var lastId: Int = -1
            private lateinit var lastGraphics: Graphics

            override fun getValue(thisRef: Nothing?, property: KProperty<*>): Graphics {
                if (buf.hashCode() != lastId) {
                    lastId = buf.hashCode()
                    lastGraphics = buf.graphics
                }

                return lastGraphics
            }
        }
        var rects = ArrayList<PackedTexture>()

        var height = 0
        var rowHeight = 0
        var x = 0

        images.forEach {
            if ((it.height * it.width) > maxSize) throw IllegalArgumentException("Image too big!")

            if (it.height > height) height = it.height
            if ((rowHeight + height) > buf.height) {
                graphics.dispose()

                height = 0
                rowHeight = 0
                x = 0

                output.add(buf to rects)

                rects = ArrayList()
                buf = BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB)
            }
            if (x + it.width > buf.width) {
                rowHeight += height
                height = it.height
                x = 0
            }

            graphics.drawImage(it, x, rowHeight, null)
            rects.add(PackedTexture(x, rowHeight, it.width, it.height))

            x += it.width
        }

        val last = BufferedImage(length, rowHeight + height, BufferedImage.TYPE_INT_ARGB)
        val lastG = last.graphics
        lastG.drawImage(buf, 0, 0, null)
        lastG.dispose()

        output.add(last to rects)

        println(output)
    }

    @Test
    fun `Completely nonblocking coroutines`() {
        val scope = CoroutineScope(Executors.newCachedThreadPool().asCoroutineDispatcher())

        scope.launch {
            println("hey")
        }
        println("aa")
    }
}