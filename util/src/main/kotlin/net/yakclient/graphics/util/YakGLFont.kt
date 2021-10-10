package net.yakclient.graphics.util

import net.yakclient.graphics.util.YakFont.FontCharacterMetaData
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

//private class LazyHashMap<K, out V>(
//    private val  _delegate: MutableMap<K, V> = HashMap(),
//    private val lazyInitialization: (K) -> V?
//) : Map<K, V> by _delegate {
//    override fun get(key: K): V? = if (!containsKey(key)) lazyInitialization(key)?.also { _delegate[key] = it } else _delegate[key]
//}

public class YakGLFont(
    override val type: Font,
    override val name: String,
    override val meta: YakFont.FontMetaData,
) : YakFont {
    private val characters: Map<Int, FontCharacterMetaData> = object : HashMap<Int, FontCharacterMetaData>() {
        override fun get(key: Int): FontCharacterMetaData? = if (!containsKey(key)) getChar(key.toChar()).also { super.put(key, it) } else super.get(key)
    }
//    LazyHashMap {
//        getChar(it.toChar())
//    }

//    override val backingTex: YakTexture

//    init {
//        var x = 0
//        var y = 0
//
//        var rowHeight = 0
//        val fontImage = BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB)
//        val graphics = (fontImage.graphics as Graphics2D).apply { font = type }
//        val metrics = graphics.fontMetrics
//        val height = metrics.height
//
//        for (i in 0..255) {
//            val char = i.toChar()
//
//
//            val width = metrics.charWidth(char)
//
//            characters[i] = YakFont.FontCharacterMetaData(char, height, width, x, y)
//
//            if (height > rowHeight) rowHeight = height
//            if (x + width < fontImage.width) x += width else x = 0; y += rowHeight
//        }
//
//        backingTex = YakTextureFactory.loadTexture(fontImage)
//    }

    override operator fun get(char: Char): FontCharacterMetaData? = characters[char.code]

    override fun getWidth(value: String): Int =
        value.fold(0) { acc, char -> acc + (characters[char.code]?.width ?: 0) }

    override fun getHeight(value: String): Int =
        value.fold(0) {acc, char -> ((characters[char.code]?.height) ?: acc).takeIf { it > acc } ?: acc }

    private fun getChar(c: Char): FontCharacterMetaData {
        val temp = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

        val tempGraphics = temp.graphics as Graphics2D
//        tempGraphics.setRenderingHint(
//                RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON
//            )
        tempGraphics.font = type


        val metrics = tempGraphics.fontMetrics


        val width = metrics.charWidth(c)
        val fontImage = BufferedImage(width, metrics.height, BufferedImage.TYPE_INT_ARGB)
        val graphics = fontImage.graphics as Graphics2D
//        graphics.setRenderingHint(
//            RenderingHints.KEY_ANTIALIASING,
//            RenderingHints.VALUE_ANTIALIAS_ON
//        )
        graphics.font = type

        graphics.color = Color.white

        graphics.drawString(c.toString(), 0, 0 + metrics.ascent)


//        fontImage.getSubimage(0,0,width, metrics.height)
//        org.newdawn.slick.Font
        return FontCharacterMetaData(c, metrics.height, width, YakTextureFactory.loadTexture(fontImage))
    }

}