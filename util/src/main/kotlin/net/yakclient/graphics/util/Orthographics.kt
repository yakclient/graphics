package net.yakclient.graphics.util

import net.yakclient.graphics.util.unit.ScreenUnit

public object Orthographics {
    private var _left: Double = 1.0
    private var _right: Double = 1.0
    private var _top: Double = 1.0
    private var _bottom: Double = 1.0

    public val left : Double
        get() = _left
    public val right : Double
        get() = _right
    public val top : Double
        get() = _top
    public val bottom : Double
        get() = _bottom

    public fun setOrtho(left: Double, right: Double, bottom: Double, top: Double) {
        this._left = left
        this._right = right
        this._top = top
        this._bottom = bottom
    }

    public fun transformX(x: Float): Float = ((x * (_left + _right)) - _right  ).toFloat()

    public fun transformY(y: Float): Float = ((y * (_top + _bottom)) - _bottom).toFloat()

    public fun transform(unit: ScreenUnit): ScreenUnit = object : ScreenUnit {
        override val normalizedX: Float
            get() =
                transformX(unit.normalizedX)
        override val normalizedY: Float
            get() =
                transformY(unit.normalizedY)
    }
}