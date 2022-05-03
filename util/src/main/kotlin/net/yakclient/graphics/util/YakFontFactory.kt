package net.yakclient.graphics.util

import net.yakclient.graphics.util.YakFont.FontMetaData
import net.yakclient.graphics.util.impl.YakFontImpl
import net.yakclient.graphics.util.impl.YakFontProviderImpl
import java.awt.Font
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap

public object YakFontFactory {
    private val fonts: MutableMap<FontMetaData, YakFont> = HashMap()
    private val provider: FontProvider
    init {
        provider = YakFontProviderImpl() //ServiceLoader.load(FontProvider::class.java).firstOrNull() ?: throw IllegalStateException("Failed to find a font provider in the classpath!")
    }

    @JvmOverloads
    public fun fontOf(
        name: String = "Default",
        fontSize: Int = 24,
        style: Int = Font.PLAIN,
        aliased: Boolean = false,
    ) : YakFont {
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
    ) : YakFont {
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
        public fun load(font: Font, name: String, meta: FontMetaData) : YakFont
    }
}