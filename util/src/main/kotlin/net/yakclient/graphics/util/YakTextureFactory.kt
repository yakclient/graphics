package net.yakclient.graphics.util

import kotlinx.coroutines.*
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.InputStream
import java.net.URL
import java.util.*
import java.util.concurrent.Executors
import javax.imageio.ImageIO
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public object YakTextureFactory {
    private val provider: TextureProvider by lazy { providerProvider() }
    private val scope = CoroutineScope(Executors.newCachedThreadPool().asCoroutineDispatcher())
    private val placeholderTexture by lazy {
        val buf = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val graphics = buf.graphics

        graphics.color = Color.RED
        graphics.fillRect(0, 0, 1, 1)
        graphics.dispose()

        loadTexture(buf)
    }

    public var providerProvider: () -> TextureProvider = {
        ServiceLoader.load(TextureProvider::class.java).firstOrNull()
            ?: throw IllegalStateException("Failed to find a texture provider on the classpath!")
    }

    private fun loadAtlas(ins: Sequence<IdentifiedValue<BufferedImage>>): List<IdentifiedValue<YakTexture>> {
//        val completed = HashMap<Int, YakTexture>()


//        scope.launch {
        val packed = packTextures(ins)

        return packed.flatMap { (image, texs) ->
            val parent = loadTexture(image)
            texs.map { (id, it) -> parent.subTexture(it.x, it.y, it.width, it.height) identifyBy id }
        }
//        }

//        return ins.mapTo(ArrayList()) { (id, _) ->
//            DeferredTexture { completed[id] } identifyBy id
//        }
    }

    private fun packTextures(ins: Sequence<IdentifiedValue<BufferedImage>>): List<Pair<BufferedImage, List<IdentifiedValue<PackedTexture>>>> {
        val images = ins.sortedByDescending { it.value.height }.map { (id, image) ->
            image identifyBy id
        }

        return attemptPack(images)
    }

    private data class PackedTexture(
        val x: Int, val y: Int, val width: Int, val height: Int
    )


    private fun attemptPack(
        images: Sequence<IdentifiedValue<BufferedImage>> // Images to pack
    ): List<Pair<BufferedImage, List<IdentifiedValue<PackedTexture>>>> {
        val length = provider.maxTextureLength

        val output = ArrayList<Pair<BufferedImage, List<IdentifiedValue<PackedTexture>>>>()

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
        var rects = ArrayList<IdentifiedValue<PackedTexture>>()

        var height = 0
        var rowHeight = 0
        var x = 0

        images.forEach { (id, it) ->
            if (it.height > length || it.width > length) throw IllegalArgumentException("Image too large! Maximum texture dimensions are '${length}x${length} pixels' but the provided texture is '${it.width}x${it.height} pixels'")

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
            rects.add(PackedTexture(x, rowHeight, it.width, it.height) identifyBy id)

            x += it.width
        }

        val last = BufferedImage(if (rowHeight == 0) x else length, rowHeight + height, BufferedImage.TYPE_INT_ARGB)
        val lastG = last.graphics
        lastG.drawImage(buf, 0, 0, null)
        lastG.dispose()

        output.add(last to rects)

        return output
    }

    private fun loadTexture(image: BufferedImage): YakTexture {
        return provider.load(image)
    }

    internal fun loadImages(images: Sequence<IdentifiedValue<BufferedImage>>): List<IdentifiedValue<YakTexture>> =
        loadAtlas(images)

    public fun loadTextures(ins: Sequence<IdentifiedValue<InputStream>>): List<IdentifiedValue<YakTexture>> {
        return loadImages(ins.map { (id, v) -> ImageIO.read(v) identifyBy id })
    }

    public fun loadTexture(imageIn: InputStream): YakTexture = loadTexture(ImageIO.read(imageIn))

    public fun loadTexture(url: URL): YakTexture = loadTexture(ImageIO.read(url))

    public interface TextureProvider {
        public val maxTextureLength: Int

        public fun load(image: BufferedImage): YakTexture
    }

    public data class IdentifiedValue<T>(public val id: Int, public val value: T)

    public infix fun <T> T.identifyBy(id: Int): IdentifiedValue<T> = IdentifiedValue(id, this)

    private class DeferredTexture(
        private val awaiting: () -> YakTexture?
    ) : YakTexture {
        private val texOrNull
            get() = awaiting()

        override val parent: YakTexture?
            get() = texOrNull?.parent ?: placeholderTexture.parent
        override val height: Int = texOrNull?.height ?: placeholderTexture.height
        override val width: Int = texOrNull?.width ?: placeholderTexture.width
        override val offsetX: Int = texOrNull?.offsetX ?: placeholderTexture.offsetX
        override val offsetY: Int = texOrNull?.offsetY ?: placeholderTexture.offsetY

        override fun subTexture(x: Int, y: Int, width: Int, height: Int): YakTexture =
            texOrNull?.subTexture(x, y, width, height) ?: this

        override fun bind() {
            texOrNull?.bind() ?: placeholderTexture.bind()
        }

        override fun release() {
            texOrNull?.release() ?: placeholderTexture.release()
        }
    }
}

