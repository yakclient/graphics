package net.yakclient.graphics.util.impl

import net.yakclient.graphics.util.YakFont
import net.yakclient.graphics.util.YakFontFactory
import net.yakclient.graphics.util.YakTextureFactory
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage


public class YakFontImpl(
    override val type: Font,
    override val name: String,
    override val meta: YakFont.FontMetaData,
) : YakFont {
    private val characters: Map<Char, YakFont.FontCharacterMetaData> = run {
        val tmp = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)

        val tempGraphics = tmp.graphics as Graphics2D
        tempGraphics.font = type

        val metrics = tempGraphics.fontMetrics

        YakTextureFactory.loadImages((0..Character.MAX_VALUE.code).filter(type::canDisplay).map { c ->
            val char = c.toChar()
            BufferedImage(metrics.charWidth(char), metrics.height, BufferedImage.TYPE_INT_RGB).also { image ->
                val graphics = image.graphics

                graphics.font = type
                graphics.color = Color.white

                graphics.drawString(char.toString(), 0, 0 + metrics.ascent)
            }
        }).withIndex().map { (c, tex) -> YakFont.FontCharacterMetaData(c.toChar(), tex.width, tex.height, tex) }
            .associateBy { it.c }
    }

    override operator fun get(char: Char): YakFont.FontCharacterMetaData? = characters[char]

    override fun getWidth(value: String): Int =
        value.fold(0) { acc, char -> acc + (characters[char]?.width ?: 0) }

    override fun getHeight(value: String): Int =
        value.fold(0) { acc, char -> ((characters[char]?.height) ?: acc).takeIf { it > acc } ?: acc }

//    private fun getChar(c: Char): YakFont.FontCharacterMetaData {
//        val temp = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
//
//        val tempGraphics = temp.graphics as Graphics2D
//        tempGraphics.font = type
//
//        val metrics = tempGraphics.fontMetrics
//
//        val width = metrics.charWidth(c)
//
//        val fontImage = BufferedImage(width, metrics.height, BufferedImage.TYPE_INT_ARGB)
//        val graphics = fontImage.graphics as Graphics2D
//
//        graphics.font = type
//        graphics.color = Color.white
//
//        graphics.drawString(c.toString(), 0, 0 + metrics.ascent)
//
//        return YakFont.FontCharacterMetaData(c, width, metrics.height, YakTextureFactory.loadTexture(fontImage))
//    }
}

public class YakFontProviderImpl : YakFontFactory.FontProvider {
    override fun load(font: Font, name: String, meta: YakFont.FontMetaData): YakFont = YakFontImpl(font, name, meta)
}