package net.yakclient.graphics.util.unit

import net.yakclient.graphics.util.OrthographicTransformer
import net.yakclient.graphics.util.ScreenAccess

private class PixelScreenUnit(
    private val value: Float
) : ScreenUnit {
    override val asX: Float
        get() = value / ScreenAccess.access.width
    override val asY: Float
        get() = value / ScreenAccess.access.height
}

private class ViewWidthScreenUnit(
    private val value: Float
) : ScreenUnit {
    override val asX: Float = value
    override val asY: Float
        get() = value * ((ScreenAccess.access.width.toFloat()) / (ScreenAccess.access.height))
}

private class ViewHeightScreenUnit(
    private val value: Float
) : ScreenUnit {
    override val asX: Float
        get() = value * ((ScreenAccess.access.height.toFloat()) / (ScreenAccess.access.width))
    override val asY: Float = value
}

private class NativeScreenUnit(
    value: Float
) : ScreenUnit {
    override val asX: Float = value
    override val asY: Float = value
}

public val Int.px: ScreenUnit
    get() = PixelScreenUnit(toFloat())

public val Float.vw: ScreenUnit
    get() = ViewWidthScreenUnit(this)

public val Float.vh: ScreenUnit
    get() = ViewHeightScreenUnit(this)

public val Float.native: ScreenUnit
    get() = NativeScreenUnit(this)


