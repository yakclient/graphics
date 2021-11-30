package net.yakclient.graphics.util

import java.awt.image.BufferedImage
import java.io.InputStream
import java.net.URL
import java.util.*
import javax.imageio.ImageIO


public object YakTextureFactory {
    private val urlToTex: MutableMap<URL, YakTexture> = HashMap()
    private val texToUrl: MutableMap<YakTexture, URL> = HashMap()
    private val provider: TextureProvider

    init {
        provider = ServiceLoader.load(TextureProvider::class.java).firstOrNull()
            ?: throw IllegalStateException("Failed to find a texture provider on the classpath!")
    }

    public fun loadTexture(): YakTexture = VacantTexture()

    public fun removeTexture(texture: YakTexture): Unit =
        texToUrl[texture].let { urlToTex.remove(it); texToUrl.remove(texture) }

    public fun loadTexture(image: BufferedImage): YakTexture = provider.load(image)

    // TODO Asynchronous completion for loading images(should return a CompletableYakTexture that does nothing on bind and release until completed)
    public fun loadTexture(loc: URL): YakTexture =
        urlToTex[loc] ?: loadTexture(ImageIO.read(loc)).also { urlToTex[loc] = it; texToUrl[it] = loc }

    public interface TextureProvider {
        public fun load(image: BufferedImage): YakTexture
    }
}