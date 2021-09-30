package net.yakclient.graphics.util

import java.awt.Font
import net.yakclient.graphics.util.YakFont.FontMetaData
import java.util.Objects

public interface YakFont {
    public val type: Font?
    public val name: String?
    public val meta: FontMetaData?
    public fun getWidth(value: String?): Double
    public val lineHeight: Double
    public fun getHeight(value: String?): Double
    public fun drawString(value: String?, color: RGBColor?, x: Double, y: Double)
    public fun drawString(value: String?, x: Double, y: Double)
     public data class FontMetaData(val name: String, val style: Int, val size: Int, val isAliased: Boolean)
}