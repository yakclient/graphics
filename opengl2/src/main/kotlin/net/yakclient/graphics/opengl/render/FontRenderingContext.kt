package net.yakclient.graphics.opengl.render

import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.render.RenderingType
import net.yakclient.graphics.api.render.UnsupportedRenderingTypeException
import net.yakclient.graphics.util.RGBColor
import net.yakclient.graphics.util.YakFont

public class FontRenderingContext(
    public val font: YakFont,
    public val value: String,
    public val color: RGBColor,
    public val x: Double,
    public val y: Double,
) : RenderingContext {
    override fun useRenderer(type: RenderingType): Renderer<*> {
        if (type != RenderingType.IMMEDIATE) throw UnsupportedRenderingTypeException(type, this.javaClass.name)
        return ImmediateFontRenderer(this)
    }

    override fun useRenderer(): Renderer<*> {
        return this.useRenderer(RenderingType.IMMEDIATE)
    }

}