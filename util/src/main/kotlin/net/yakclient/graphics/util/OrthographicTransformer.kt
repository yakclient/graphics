package net.yakclient.graphics.util

import net.yakclient.graphics.util.unit.ScreenUnit

public object OrthographicTransformer {
    private var _left: Int = 1
    private var _right: Int = 1
    private var _top: Int = 1
    private var _bottom: Int = 1

    public val left : Int
        get() = _left
    public val right : Int
        get() = _right
    public val top : Int
        get() = _top
    public val bottom : Int
        get() = _bottom

    public fun setOrtho(left: Int, right: Int, bottom: Int, top: Int) {
        this._left = left
        this._right = right
        this._top = top
        this._bottom = bottom
    }

    private fun transformX(x: Float): Float = (x * (_left + _right)) - _left

    private fun transformY(y: Float): Float = (y * (_top + _bottom)) - _top

    public fun transform(unit: ScreenUnit): ScreenUnit = object : ScreenUnit {
        override val asX: Float
            get() = transformX(unit.asX)
        override val asY: Float
            get() = transformY(unit.asY)
    }
}