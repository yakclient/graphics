package net.yakclient.graphics.util

import net.yakclient.graphics.util.TextureFont.FontMetaData
import net.yakclient.graphics.util.impl.YakFontProviderImpl
import java.awt.Font
import java.io.File
import kotlin.collections.HashMap

public object FontFactory {
    private val fonts: MutableMap<FontMetaData, TextureFont> = HashMap()
    private val provider: FontProvider = YakFontProviderImpl()

    @JvmOverloads
    public fun fontOf(
        name: String = "Default",
        fontSize: Int = 24,
        style: Int = Font.PLAIN,
        aliased: Boolean = false,
    ) : TextureFont {
        val metaData = FontMetaData(name, style, fontSize, aliased)
        if (fonts.containsKey(metaData)) return fonts[metaData]!!
        return fonts[metaData] ?: run {
            val font = Font(name, style, fontSize)
            val yakFont = provider.load(font, name, metaData)//YakGLFont(font, name, metaData)
            fonts[metaData] = yakFont
            yakFont
        }
    }

    @JvmOverloads
    public fun fontOf(
        fontIn: File,
        fontSize: Int = 24,
        style: Int = Font.PLAIN,
        aliased: Boolean = false,
    ) : TextureFont {
        val metaData = FontMetaData(fontIn.absolutePath, style, fontSize, aliased)
        if (fonts.containsKey(metaData)) return fonts[metaData]!!
        return fonts[metaData] ?: run {
            val font = Font.createFont(Font.TRUETYPE_FONT, fontIn)
            val yakFont =  provider.load(font, font.name, metaData)
            fonts[metaData] = yakFont
            yakFont
        }
    }

    public interface FontProvider {
        public fun load(font: Font, name: String, meta: FontMetaData) : TextureFont
    }
}