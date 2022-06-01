package net.yakclient.graphics.components

import net.yakclient.graphics.api.GuiPropertiesMap
import net.yakclient.graphics.api.IllegalPropertyException
import net.yakclient.graphics.api.SpecifiedProperties
import net.yakclient.graphics.util.Color
import net.yakclient.graphics.util.ColorCodes
import net.yakclient.graphics.util.TextureFont
import net.yakclient.graphics.util.FontFactory.fontOf
import java.util.*

public class TextSpecProps(properties: GuiPropertiesMap?) : SpecifiedProperties(properties!!) {
    public val value: String
        get() = this.get<String>("value").orElseThrow { IllegalPropertyException("value") }
    public val x: Double
        get() = this.get<Double>("x").orElseThrow { IllegalPropertyException("x") }
    public val y: Double
        get() = this.get<Double>("y").orElseThrow { IllegalPropertyException("y") }
    public val color: Color
        get() = this.get<Color?>("color").or { Optional.of(ColorCodes.WHITE) }
            .get()
    public val font: TextureFont
        get() = this.get<TextureFont>("font").or { Optional.of(fontOf()) }.get()
}