package net.yakclient.graphics.util

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.net.URL
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.imageio.ImageIO


private const val BYTES_PER_PIXEL = 4 //3 for RGB, 4 for RGBA

public object YakTextureFactory {
    private val textureCache: MutableMap<URL, YakTexture> = HashMap()


    public fun loadTexture(): YakTexture = VacantTexture()

    //Credit : https://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
    public fun loadTexture(image: BufferedImage): YakTexture {
        val pixels = image.getRGB(0, 0, image.width, image.height, IntArray(image.width * image.height), 0, image.width)
        val buffer = ByteBuffer.allocateDirect((image.width * image.height * BYTES_PER_PIXEL) shl 2).order(ByteOrder.nativeOrder()).asFloatBuffer()//; BufferUtils.createFloatBuffer(image.width * image.height * BYTES_PER_PIXEL) //4 for RGBA, 3 for RGB

        fun Int.glValue(toShr: Int): Float = ((this shr toShr) and 255) / 255f

        for (pixel in pixels) {
            buffer.put(pixel.glValue(16)) // Red component
            buffer.put(pixel.glValue(8)) // Green component
            buffer.put(pixel.glValue(0)) // Blue component
            buffer.put(pixel.glValue(24)) // Alpha component. Only for RGBA
        }

        buffer.flip()

        val textureID = glGenTextures() //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID) //Bind texture ID

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
            buffer
        )


        return YakGLTexture(textureID, GL_TEXTURE_2D, image.height, image.height)
    }

    public fun loadTexture(loc: URL): YakTexture = loadTexture(ImageIO.read(loc))
}