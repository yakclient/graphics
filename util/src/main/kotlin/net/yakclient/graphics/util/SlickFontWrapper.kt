package net.yakclient.graphics.util

//public class SlickFontWrapper(
//    override val type: Font,
//    private val trueType: TrueTypeFont,
//    override val meta: FontMetaData,
//) : YakFont {
//    override val name: String
//        get() = type.name
//
//    override val lineHeight: Double
//        get() = trueType.lineHeight.toDouble()
//
//
//    override fun getWidth(value: String): Int =
//        trueType.getWidth(value)
//
//
//    override fun getHeight(value: String): Int =
//        trueType.getHeight(value)
//
//
//    override fun stringContext(value: String, color: Color, x: Double, y: Double) {
//        trueType.drawString(x.toFloat(), y.toFloat(), value, SlickTranslator.toColor(color))
//    }
//
//    override fun stringContext(value: String, x: Double, y: Double) {
//        this.stringContext(value, ColorCodes.WHITE, x, y)
//    }
//}