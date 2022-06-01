package net.yakclient.graphics.util.impl

import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.TextureFactory.identifyBy
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage


public class YakFontImpl(
    override val type: Font,
    override val name: String,
    override val meta: TextureFont.FontMetaData,
) : TextureFont {
    private val characters: Map<Char, TextureFont.FontCharacterMetaData>
    override val chars: Set<Char>

    init {
        val tmp = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

        val tempGraphics = tmp.graphics
        tempGraphics.dispose()
        tempGraphics.font = type

        val metrics = tempGraphics.fontMetrics

        val images = (0..Char.MAX_VALUE.code).asSequence().mapNotNull { c ->
            val char = c.toChar()
            if (!type.canDisplay(char)) null
            else if (metrics.charWidth(char) > 0) (BufferedImage(
                metrics.charWidth(char),
                metrics.height,
                BufferedImage.TYPE_INT_ARGB
            ) identifyBy c).also { (_, image) ->
                val graphics = image.graphics

                graphics.font = type
                graphics.color = Color.white

                graphics.drawString(char.toString(), 0, 0 + metrics.ascent)
                graphics.dispose()
            } else null
        }.filterNot { it.value.width < 1 }

        characters = TextureFactory.loadImages(images).asSequence()
            .map { (id, tex) -> TextureFont.FontCharacterMetaData(id.toChar(), tex.width, tex.height, tex) }
            .associateBy { it.c }
        chars = characters.keys
    }

    override operator fun get(char: Char): TextureFont.FontCharacterMetaData? = characters[char]

    override fun getWidth(value: String): Int =
        value.fold(0) { acc, char -> acc + (characters[char]?.width ?: 0) }

    override fun getHeight(value: String): Int =
        value.fold(0) { acc, char -> ((characters[char]?.height) ?: acc).takeIf { it > acc } ?: acc }
}

public class YakFontProviderImpl : FontFactory.FontProvider {
    override fun load(font: Font, name: String, meta: TextureFont.FontMetaData): TextureFont = YakFontImpl(font, name, meta)
}