package net.yakclient.graphics.util

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import java.awt.image.BufferedImage
import java.net.URL
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.imageio.ImageIO



public object YakTextureFactory {
    private const val BYTES_PER_PIXEL = 4 //3 for RGB, 4 for RGBA

    //TODO figure out a better solution.
    private val urlToTex: MutableMap<URL, YakTexture> = HashMap()
    //dangerous if the texture doesn't implement hashcode and equals, but kinda the only way to do it quickly...
    private val texToUrl: MutableMap<YakTexture, URL> = HashMap()
     public fun loadTexture(): YakTexture = VacantTexture()

    internal fun removeTexture(texture: YakTexture) : Unit = texToUrl[texture].let { urlToTex.remove(it); texToUrl.remove(texture) }

    //Credit : https://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
    public fun loadTexture(image: BufferedImage): YakTexture {
        val buffer = ByteBuffer.allocateDirect((image.width * image.height * BYTES_PER_PIXEL) shl 2)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        fun Int.glValue(toShr: Int): Float = ((this shr toShr) and 255) / 255f

//        val colors =
//            ColorAggregation(pixels.map { Color(it.glValue(16), it.glValue(8), it.glValue(0), it.glValue(24)) })

        for (pixel in image.getRGB(
            0,
            0,
            image.width,
            image.height,
            IntArray(image.width * image.height),
            0,
            image.width
        )) {
            buffer.put(pixel.glValue(16)) // Red component
            buffer.put(pixel.glValue(8)) // Green component
            buffer.put(pixel.glValue(0)) // Blue component
            buffer.put(pixel.glValue(24)) // Alpha component. Only for RGBA
        }

        val textureID = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureID)

//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

        glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA8,
            image.width,
            image.height,
            0,
            GL_RGBA,
            GL_FLOAT,
            YakGraphicsUtils.flipBuf(buffer)
        )

        return YakGLTexture(textureID, GL_TEXTURE_2D, image.height, image.width)
    }

    public fun loadTexture(loc: URL): YakTexture = urlToTex[loc] ?: loadTexture(ImageIO.read(loc)).also { urlToTex[loc] = it; texToUrl[it] = loc }
}