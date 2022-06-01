package net.yakclient.graphics.util

public val Texture.totalOffsetX : Int
    get() = offsetX + (parent?.offsetX ?: 0)

public val Texture.totalOffsetY : Int
    get() = offsetY + (parent?.offsetY ?: 0)

public val Texture.totalWidth: Int
    get() = parent?.totalWidth ?: width

public val Texture.totalHeight: Int
    get() = parent?.totalHeight ?: height