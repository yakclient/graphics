package net.yakclient.graphics.util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.awt.image.BufferedImage
import java.io.InputStream
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

public object YakTextureFactory {
    private val provider: TextureProvider

    init {
        provider = ServiceLoader.load(TextureProvider::class.java).firstOrNull()
            ?: throw IllegalStateException("Failed to find a texture provider on the classpath!")
    }

    private fun loadAtlas(ins: List<BufferedImage>): List<YakTexture> {
        val packed = packTextures(ins)

        val parent = loadTexture(packed.first)
        return packed.second.map { parent.subTexture(it.x, it.y, it.width, it.height) }
    }

    private fun packTextures(ins: List<BufferedImage>): Pair<BufferedImage, List<PackedTexture>> {
        val images = ins.sortedByDescending(BufferedImage::getHeight)
        val bestArea = images.fold(0f) { area, image -> area + (image.width * image.height) }
        val length = sqrt(bestArea)

        val n = ceil(log(length, 2f)).toInt()

        for (i in n until ceil(log(sqrt(provider.maxTextureSize.toFloat()), 2f)).toInt()) {
            attemptPack(i, images)?.let { return@packTextures it }
        }

        throw IllegalArgumentException(
            "Failed to appropriately pack image data into atlas! Estimated atlas size: '${
                2.0.pow(n).toInt()
            } bytes', Max supported size: '${provider.maxTextureSize}'."
        )
    }

    private data class PackedTexture(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int
    )

    private fun attemptPack(
        n: Int, // The x value of 2^x = image dimensions
        images: List<BufferedImage> // Images to pack
    ): Pair<BufferedImage, List<PackedTexture>>? {
        val length: Int = 2.0.pow(n).toInt() // Get the length of one side of our atlas

        val image = BufferedImage(length, length, BufferedImage.TYPE_INT_RGB) // Create an image to pack the atlas in
        val graphics = image.graphics // Get the graphics

        var height = 0 // The starting height of the largest image in the row
        var rowHeight = 0 // The current height of the row
        var x = 0 // The current X position

        val packedTextures = images.map {
            if (it.height > height) height =
                it.height // If the height of this image is greater than the height of any other image in the row, update it (will only happen if sorting methods change)
            if ((rowHeight + height) > length) { // If the row height + the current height is greater than the maximum possible the atlas can be, then dispose the graphics and return null
                graphics.dispose()
                return null
            }
            if ((x + it.width) > length) { // If the current x + the images width is greater than the maximum length of the atlas, then switch to a new row.
                rowHeight += height
                height = it.height
                x = 0
            }

            graphics.drawImage(it, x, rowHeight, null) // Draw the image

            val tex = PackedTexture(x, rowHeight, it.width, it.height) // Create a packedTexture

            x += it.width // Update current width

            tex // Return texture
        }

        return image to packedTextures
    }

    private fun loadTexture(image: BufferedImage) = provider.load(image)

    internal fun loadImages(images: List<BufferedImage>) : List<YakTexture> = loadAtlas(images)

    public fun loadTextures(ins: List<InputStream>): List<YakTexture> {
        val images = runBlocking {
            ins.map { async(Dispatchers.IO) { ImageIO.read(it) } }.onEach(Deferred<*>::start).map { it.await() }
        }

        return loadImages(images)
    }

    public fun loadTexture(imageIn: InputStream) : YakTexture = loadTexture(ImageIO.read(imageIn))

    public fun loadTexture(url: URL) : YakTexture = loadTexture(ImageIO.read(url))

    public interface TextureProvider {
        public val maxTextureSize: Int

        public fun load(image: BufferedImage): YakTexture
    }
}