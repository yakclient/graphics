package net.yakclient.graphics.util

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

private const val BYTES_PER_PIXEL = 4 //3 for RGB, 4 for RGBA

public object YakTextureFactory {

    public fun loadTexture(): YakTexture = VacantTexture()


//    private fun getType(path: String): TextureType {
//        return TextureType.extensionsType(path.substring(path.indexOf(EXTENSION_SEPARATOR)))
//            ?: return TextureType.PNG
//    }
//


//
//    @Throws(IOException::class)
//    fun createTex(`in`: InputStream?): YakTexture {
//        return createTex(TextureType.PNG, `in`)
//    }

    //    @Throws(IOException::class)
//    fun createTex(type: TextureType, `in`: InputStream?): YakTexture {
//        return SlickTextureWrapper(TextureLoader.getTexture(type.extensionsType(), `in`))
//    }


    //Credit : https://stackoverflow.com/questions/10801016/lwjgl-textures-and-strings
    public fun loadTexture(image: BufferedImage): YakTexture {
        val pixels = image.getRGB(0, 0, image.width, image.height, IntArray(image.width * image.height), 0, image.width)


        val buffer = BufferUtils.createByteBuffer(image.width * image.height * BYTES_PER_PIXEL) //4 for RGBA, 3 for RGB

        //BLUE  GREEN RED   ALPHA
        //8 BIT 8 BIT 8 BIT 8 BIT

        for (pixel in pixels) {
            buffer.put(((pixel shr 16) and 0xFF).toByte()) // Red component
            buffer.put(((pixel shr 8) and 0xFF).toByte()) // Green component
            buffer.put(((pixel shr 0) and 0xFF).toByte()) // Blue component
            buffer.put(((pixel shr 24) and 0xFF).toByte()) // Alpha component. Only for RGBA
        }

//        for (y in 0 until image.height) {
//            for (x in 0 until image.width) {
//                val pixel = pixels[y * image.width + x]
//                buffer.put((pixel shr 16 and 0xFF).toByte()) // Red component
//                buffer.put((pixel shr 8 and 0xFF).toByte()) // Green component
//                buffer.put((pixel and 0xFF).toByte()) // Blue component
//                buffer.put((pixel shr 24 and 0xFF).toByte()) // Alpha component. Only for RGBA
//            }
//        }
        buffer.flip()

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:
        val textureID = GL11.glGenTextures() //Generate texture ID
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID) //Bind texture ID

        //Setup wrap mode
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        //Send texel data to OpenGL
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA8,
            image.width,
            image.height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            buffer)


        //Return the texture ID so we can bind it later again
        return YakGLTexture(textureID, GL11.GL_TEXTURE_2D, image.height, image.height)
    }

    public fun loadImage(loc: URL): BufferedImage {
        return ImageIO.read(loc)
    }

//    enum class TextureType(private val extensionsType: String) {
//        PNG("PNG"), JPEG("JPEG"), GIF("GIF"), TIFF("TIFF"), RAW("RAW"), BITMAP("BMP");
//
//        fun extensionsType(): String {
//            return extensionsType
//        }
//
//        companion object {
//            fun extensionsType(type: String): TextureType? {
//                for (value in values()) {
//                    if (value.extensionsType() == type) return value
//                }
//                return null
//            }
//        }
//    }
}