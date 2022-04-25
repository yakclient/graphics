package net.yakclient.graphics.lwjgl.legacy.util

import net.yakclient.graphics.util.YakGraphicsUtils
import net.yakclient.graphics.util.YakTexture
import net.yakclient.graphics.util.YakTextureFactory
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.nio.ByteBuffer
import java.nio.ByteOrder

public data class YakGL2Texture(
    private val texId: Int,
    private val target: Int,
    override val height: Int,
    override val width: Int,
    override val offsetX: Int,
    override val offsetY: Int, override val parent: YakTexture?,
) : YakTexture {
    override fun subTexture(x: Int, y: Int, width: Int, height: Int): YakTexture =
        YakGL2Texture(texId, target, height, width, x, y, this)


    override fun bind() {
        glEnable(GL_TEXTURE_2D)
        glBindTexture(target, texId)
    }

    override fun release() {
        if (parent == null) glDeleteTextures(texId)
    }
}

private const val BYTES_PER_PIXEL = 4 //3 for RGB, 4 for RGBA

public class YakGL2TextureProvider : YakTextureFactory.TextureProvider {
    override val maxTextureLength: Int = glGetInteger(GL_MAX_TEXTURE_SIZE)

    //Credit : https://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
    override fun load(image: BufferedImage): YakTexture {

        val buffer = ByteBuffer.allocateDirect((image.width * image.height * BYTES_PER_PIXEL) shl 2)
            .order(ByteOrder.nativeOrder())

        fun Int.glValue(toShr: Int) = ((this shr toShr) and 255).toByte()

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

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA8,
            image.width,
            image.height,
            0,
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            YakGraphicsUtils.flipBuf(buffer)
        )

        return YakGL2Texture(textureID, GL_TEXTURE_2D, image.height, image.width,0,0,null)
    }
}