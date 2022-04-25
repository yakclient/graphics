package net.yakclient.graphics.lwjgl.util

import net.yakclient.graphics.util.YakGraphicsUtils
import net.yakclient.graphics.util.YakTexture
import net.yakclient.graphics.util.YakTextureFactory
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import org.lwjgl.system.MemoryUtil
import java.awt.image.BufferedImage


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
    override val maxTextureLength: Int = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE) // Returns in bytes so we want to convet to piexels

    //Credit : https://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
    override fun load(image: BufferedImage): YakTexture {
        val hasAlpha = image.colorModel.hasAlpha()
        val buffer = MemoryUtil.memAlloc((image.width * image.height * if (hasAlpha) 4 else 3))

        fun Int.glValue(toShr: Int) = ((this shr toShr) and 255).toByte()

        val pixels = image.getRGB(
            0,
            0,
            image.width,
            image.height,
            IntArray(image.width * image.height),
            0,
            image.width
        )

        for (i in 0 until image.width * image.height) {
            val pixel = pixels[i]

            buffer.put(pixel.glValue(16)) // Red component
            buffer.put(pixel.glValue(8)) // Green component
            buffer.put(pixel.glValue(0)) // Blue component
            if (hasAlpha) buffer.put(pixel.glValue(24)) // Alpha component. Only for RGBA
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
            if (hasAlpha) GL11.GL_RGBA8 else GL11.GL_RGB8,
            image.width,
            image.height,
            0,
            if (hasAlpha) GL11.GL_RGBA else GL11.GL_RGB,
            GL11.GL_UNSIGNED_BYTE,
            YakGraphicsUtils.flipBuf(buffer)
        )

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)

        return YakGL3Texture(textureID, GL11.GL_TEXTURE_2D, image.height, image.width, 0, 0, null)
    }
}