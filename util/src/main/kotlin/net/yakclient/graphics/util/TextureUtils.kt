package net.yakclient.graphics.util

public val YakTexture.totalOffsetX : Int
    get() = offsetX + (parent?.offsetX ?: 0)

public val YakTexture.totalOffsetY : Int
    get() = offsetY + (parent?.offsetY ?: 0)

public val YakTexture.totalWidth: Int
    get() = parent?.totalWidth ?: width

public val YakTexture.totalHeight: Int
    get() = parent?.totalHeight ?: height