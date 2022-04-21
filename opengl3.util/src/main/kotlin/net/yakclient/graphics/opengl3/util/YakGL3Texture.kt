package net.yakclient.graphics.opengl3.util

import net.yakclient.graphics.util.YakGraphicsUtils
import net.yakclient.graphics.util.YakTexture
import net.yakclient.graphics.util.YakTextureFactory
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.nio.ByteBuffer
import java.nio.ByteOrder

public data class YakGL3Texture(
    private val texId: Int,
    private val target: Int,
    override val height: Int,
    override val width: Int,
    override val offsetX: Int,
    override val offsetY: Int, override val parent: YakTexture?,
) : YakTexture {
    override fun subTexture(x: Int, y: Int, width: Int, height: Int): YakTexture =
        YakGL3Texture(texId, target, height, width, x, y, this)

    override fun bind() {
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glBindTexture(target, texId)
    }

    override fun release() {
        if (parent == null) GL11.glDeleteTextures(texId)
    }
}

private const val BYTES_PER_PIXEL = 4 //3 for RGB, 4 for RGBA

public class YakGL3TextureProvider : YakTextureFactory.TextureProvider {
    override val maxTextureSize: Int = GL11.glGetInteger(GL_MAX_TEXTURE_SIZE)

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

        val textureID = GL11.glGenTextures()
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID)

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA8,
            image.width,
            image.height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            YakGraphicsUtils.flipBuf(buffer)
        )

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)

        return YakGL3Texture(textureID, GL11.GL_TEXTURE_2D, image.height, image.width, 0, 0, null)
    }
}
