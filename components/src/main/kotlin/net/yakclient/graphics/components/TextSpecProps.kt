package net.yakclient.graphics.components

import net.yakclient.graphics.api.gui.GuiProperties
import net.yakclient.graphics.api.gui.IllegalPropertyException
import net.yakclient.graphics.api.gui.SpecifiedProperties
import net.yakclient.graphics.util.Color
import net.yakclient.graphics.util.ColorCodes
import net.yakclient.graphics.util.YakFont
import net.yakclient.graphics.util.YakFontFactory.fontOf
import java.util.*

public class TextSpecProps(properties: GuiProperties?) : SpecifiedProperties(properties!!) {
    public val value: String
        get() = this.get<String>("value").orElseThrow { IllegalPropertyException("value") }
    public val x: Double
        get() = this.get<Double>("x").orElseThrow { IllegalPropertyException("x") }
    public val y: Double
        get() = this.get<Double>("y").orElseThrow { IllegalPropertyException("y") }
    public val color: Color
        get() = this.get<Color?>("color").or { Optional.of(ColorCodes.WHITE) }
            .get()
    public val font: YakFont
        get() = this.get<YakFont>("font").or { Optional.of(fontOf()) }.get()
}