package net.yakclient.graphics.util

public data class Color @JvmOverloads constructor(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float = 1.0f,
) {
    public fun setAlpha(alpha: Float) : Color = Color(red, green, blue, alpha)

    public fun toColorFunc() : ColorFunction = SolidColor(this)
}
