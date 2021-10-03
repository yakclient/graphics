package net.yakclient.graphics.util

import net.yakclient.graphics.util.YakFont.FontMetaData
import java.awt.Font
import java.io.File
import java.util.concurrent.ConcurrentHashMap

public object YakFontFactory {
    private val fonts: MutableMap<FontMetaData, YakFont> = ConcurrentHashMap()

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
            val yakFont = YakGLFont(font, name, metaData)
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
            val yakFont = YakGLFont(font, font.fontName, metaData)
            fonts[metaData] = yakFont
            yakFont
        }
    }

//    public companion object {
//        public const val DEFAULT_TEXT_SIZE : Int = 24
//        public const val FONT_DEFAULT : String = "Default"
//        private val fonts: MutableMap<FontMetaData, YakFont> = ConcurrentHashMap()
//
//        public fun create(fontIn: InputStream): YakFontFactory {
//            return YakCustomFontFactory(fontIn)
//        }
//
//        public fun create(name: String, fontSize: Int): YakFontFactory {
//            return YakNamedFontFactory(name, fontSize)
//        }
//
////        @JvmStatic
//        public fun create(): YakFontFactory {
//            return YakNamedFontFactory(FONT_DEFAULT, DEFAULT_TEXT_SIZE)
//        }
//    }
//
//    abstract fun buildFont(isAliased: Boolean): YakFont?
//    abstract fun addStyle(style: Int): YakFontFactory
//    fun buildFont(): YakFont? {
//        return this.buildFont(true)
//    }
//
//    private class YakCustomFontFactory (private val fontIn: InputStream) : YakFontFactory() {
//        override fun buildFont(isAliased: Boolean): YakFont {
//            val font = createFont()
//            val meta = FontMetaData(font.name, font.style, font.size, isAliased)
//            if (fonts.containsKey(meta)) return fonts[meta]!!
//            val yakFont = SlickFontWrapper(font, TrueTypeFont(font, isAliased), meta)
//            fonts[meta] = yakFont
//            return yakFont
//        }
//
//        private fun createFont(): Font {
//            return try {
//                Font.createFont(Font.TRUETYPE_FONT, fontIn)
//            } catch (e: FontFormatException) {
//                Font(null, Font.PLAIN, 12)
//            } catch (e: IOException) {
//                Font(null, Font.PLAIN, 12)
//            }
//        }
//
//        override fun addStyle(style: Int): YakFontFactory {
//            //Nothing to do
//            return this
//        }
//    }
//
//    private class YakNamedFontFactory constructor(private val name: String, private val size: Int) : YakFontFactory() {
//        private var style: Int
//        override fun buildFont(isAliased: Boolean): YakFont? {
//            val metaData = FontMetaData(name, style, size, isAliased)
//            if (fonts!!.containsKey(metaData)) return fonts[metaData]
//            val font = Font(name, style, size)
//            val yakFont = SlickFontWrapper(font, TrueTypeFont(font, isAliased), metaData)
//            fonts[metaData] = yakFont
//            return yakFont
//        }
//
//        override fun addStyle(style: Int): YakFontFactory {
//            this.style = this.style or style
//            return this
//        }
//
//        init {
//            style = Font.PLAIN
//        }
//    }
}