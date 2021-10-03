package net.yakclient.graphics.util

import java.awt.Font

public interface YakFont {
//    public val backingTex: YakTexture
    public val type: Font
    public val name: String
    public val meta: FontMetaData

//    public val characters: Map<Int, FontCharacterMetaData>

    public operator fun get(char: Char) : FontCharacterMetaData?

//    public val lineHeight: Double

    public fun getWidth(value: String): Int
    public fun getHeight(value: String): Int

    public data class FontMetaData(val name: String, val style: Int, val size: Int, val isAliased: Boolean)

    public data class FontCharacterMetaData(val c: Char, val height: Int, val width: Int, val backingTexture: YakTexture)
}