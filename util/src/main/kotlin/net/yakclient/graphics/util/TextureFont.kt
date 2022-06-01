package net.yakclient.graphics.util

import java.awt.Font

public interface TextureFont {
    public val type: Font
    public val name: String
    public val meta: FontMetaData

    public val chars: Set<Char>

    public operator fun get(char: Char) : FontCharacterMetaData?

    public fun getWidth(value: String): Int
    public fun getHeight(value: String): Int

    public data class FontMetaData(val name: String, val style: Int, val size: Int, val isAliased: Boolean)

    public data class FontCharacterMetaData(
        val c: Char,
        val width: Int,
        val height: Int,
        val backingTexture: Texture
    )
}