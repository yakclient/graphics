package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.RGBColor

class FontRenderingContext(font: YakFont, value: String, color: RGBColor, x: Double, y: Double) : RenderingContext {
    private val font: YakFont
    val value: String
    val color: RGBColor
    val x: Double
    val y: Double
    fun getFont(): YakFont {
        return font
    }

    override fun useRenderer(type: RenderingType): Renderer<*> {
        if (type != RenderingType.IMMEDIATE) throw UnsupportedRenderingTypeException(type, this.javaClass.name)
        return ImmediateFontRenderer(this)
    }

    override fun useRenderer(): Renderer<*> {
        return this.useRenderer(RenderingType.IMMEDIATE)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as FontRenderingContext
        return java.lang.Double.compare(that.x, x) == 0 && java.lang.Double.compare(that.y, y) == 0 && Objects.equals(
            font,
            that.font) && Objects.equals(value, that.value) && Objects.equals(color, that.color)
    }

    override fun hashCode(): Int {
        return Objects.hash(font, value, color, x, y)
    }

    init {
        this.font = font
        this.value = value
        this.color = color
        this.x = x
        this.y = y
    }
}