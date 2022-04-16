package net.yakclient.graphics.opengl2.util

import net.yakclient.graphics.util.YakFont
import net.yakclient.graphics.util.YakFont.FontCharacterMetaData
import net.yakclient.graphics.util.YakFontFactory
import net.yakclient.graphics.util.YakTextureFactory
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage

public class YakGLFont(
    override val type: Font,
    override val name: String,
    override val meta: YakFont.FontMetaData,
) : YakFont {
    private val characters: Map<Int, FontCharacterMetaData> = object : HashMap<Int, FontCharacterMetaData>() {
        override fun get(key: Int): FontCharacterMetaData? =
            if (!containsKey(key)) getChar(key.toChar()).also { super.put(key, it) } else super.get(key)
    }

    override operator fun get(char: Char): FontCharacterMetaData? = characters[char.code]

    override fun getWidth(value: String): Int =
        value.fold(0) { acc, char -> acc + (characters[char.code]?.width ?: 0) }

    override fun getHeight(value: String): Int =
        value.fold(0) { acc, char -> ((characters[char.code]?.height) ?: acc).takeIf { it > acc } ?: acc }

    private fun getChar(c: Char): FontCharacterMetaData {
        val temp = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

        val tempGraphics = temp.graphics as Graphics2D
        tempGraphics.font = type

        val metrics = tempGraphics.fontMetrics

        val width = metrics.charWidth(c)

        val fontImage = BufferedImage(width, metrics.height, BufferedImage.TYPE_INT_ARGB)
        val graphics = fontImage.graphics as Graphics2D

        graphics.font = type
        graphics.color = Color.white

        graphics.drawString(c.toString(), 0, 0 + metrics.ascent)

        return FontCharacterMetaData(c, metrics.height, width, YakTextureFactory.loadTexture(fontImage))
    }
}

public class YakGL2FontProvider : YakFontFactory.FontProvider {
    override fun load(font: Font, name: String, meta: YakFont.FontMetaData): YakFont = YakGLFont(font, name, meta)
}
