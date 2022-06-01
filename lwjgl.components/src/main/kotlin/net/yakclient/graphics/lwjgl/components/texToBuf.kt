package net.yakclient.graphics.lwjgl.components

import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.safeFloatBufOf

public fun Texture.texToBuf() : SafeFloatBuffer = texToBufInternal(this)

private fun texToBufInternal(tex: Texture) : SafeFloatBuffer {
   return safeFloatBufOf(8)
        .put(tex.totalOffsetX.toFloat() / tex.totalWidth)
        .put(tex.totalOffsetY.toFloat() / tex.totalHeight)
        .put((tex.totalOffsetX + tex.width).toFloat() / tex.totalWidth)
        .put(tex.totalOffsetY.toFloat() / tex.totalHeight)
        .put((tex.totalOffsetX + tex.width).toFloat() / tex.totalWidth)
        .put((tex.totalOffsetY + tex.height).toFloat() / tex.totalHeight)
        .put(tex.totalOffsetX.toFloat() / tex.totalWidth)
        .put((tex.totalOffsetY + tex.height).toFloat() / tex.totalHeight)
}