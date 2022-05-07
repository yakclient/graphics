package net.yakclient.graphics.lwjgl.render

import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.lwjgl.util.YakGL3Texture
import net.yakclient.graphics.util.VacantTexture
import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.safeFloatBufOf


internal fun VerticeRenderingContext.couldBatch(other: VerticeRenderingContext): Boolean {
    val otherData = other.data

    val check1 = drawType == other.drawType
            && textureType == other.textureType


    val dataTex = data.texture
    val otherDataTex = otherData.texture

    val check2 = dataTex is YakGL3Texture && otherDataTex is YakGL3Texture

    val check3 = dataTex is YakGL3Texture && otherDataTex is YakGL3Texture && dataTex.texId == otherDataTex.texId
            && dataTex.target == otherDataTex.target

    return check1 && (check3 || !check2)
}

internal fun VerticeRenderingContext.tryBatch(other: VerticeRenderingContext): RenderingContext? {
    val otherData = other.data

    if (!couldBatch(other)) return null

    fun SafeFloatBuffer.addRemaining(toAdd: Int, default: (i: Int) -> Float = { 0f }): SafeFloatBuffer {
        if (toAdd < 1) return this

        val newBuf = safeFloatBufOf(size + toAdd)

        forEach(newBuf::put)

        repeat(toAdd) { newBuf.put(default(it)) }

        return newBuf
    }

    val fullVertices = data.vertices.addRemaining(data.verticeStride * data.verticeCount - data.vertices.size)
    val fullColors = data.colors.addRemaining(data.colorStride * data.verticeCount - data.colors.size)
//    val fullNormals = data.normals.addRemaining(data.normalStride * data.verticeCount - data.normals.size)
    val fullTexs = data.texs.addRemaining(data.texStride * data.verticeCount - data.texs.size)

    val fullOtherVertices =
        otherData.vertices.addRemaining((otherData.verticeStride * otherData.verticeCount) - otherData.vertices.size)
    val fullOtherColors =
        otherData.colors.addRemaining((otherData.verticeStride * otherData.verticeCount) - otherData.colors.size)
//    val fullOtherNormals =
//        otherData.normals.addRemaining((otherData.verticeStride * otherData.verticeCount) - otherData.normals.size)
    val fullOtherTexs =
        otherData.texs.addRemaining((otherData.verticeStride * otherData.verticeCount) - otherData.texs.size)

    val vertices = fullVertices.normalizeWith(fullOtherVertices, data.verticeStride, otherData.verticeStride)
    val colors = fullColors.normalizeWith(fullOtherColors, data.colorStride, otherData.colorStride)
//    val normals = fullNormals.normalizeWith(fullOtherNormals, data.normalStride, otherData.normalStride)
    val texs = fullTexs.normalizeWith(fullOtherTexs, data.texStride, otherData.texStride)

    val newData = GLRenderingData(
        vertices,
        data.verticeStride.coerceAtLeast(otherData.verticeStride),
        colors,
        data.colorStride.coerceAtLeast(otherData.colorStride),
        safeFloatBufOf(),
        0,
//        normals,
//        data.normalStride.coerceAtLeast(otherData.normalStride),
        texs,
        data.texStride.coerceAtLeast(otherData.texStride),
        if (data.texture is VacantTexture) otherData.texture else data.texture,
        data.verticeCount + otherData.verticeCount
    )

    return VerticeRenderingContext(drawType, textureType, newData)
}

private fun SafeFloatBuffer.normalizeWith(other: SafeFloatBuffer, thisStride: Int, otherStride: Int): SafeFloatBuffer {
    val thisNeedsInterleaving = thisStride < otherStride

    val firstBuf = if (thisNeedsInterleaving) interleaveAfter(thisStride, otherStride - thisStride) { 0f } else this
    val secondBUf =
        if (!thisNeedsInterleaving) other.interleaveAfter(otherStride, thisStride - otherStride) { 0f } else this

    return firstBuf + secondBUf
}

internal operator fun SafeFloatBuffer.plus(other: SafeFloatBuffer): SafeFloatBuffer {
    val newBuffer = safeFloatBufOf(size + other.size)

    // TODO Use putAll method instead
    for (fl in this) {
        newBuffer.put(fl)
    }
    for (fl in other) {
        newBuffer.put(fl)
    }

    return newBuffer
}

internal fun SafeFloatBuffer.interleaveAfter(
    stride: Int /* In Floats! */, leafSize: Int /* In Floats! */, provider: (index: Int) -> Float
): SafeFloatBuffer {
    if (stride <= 0 || leafSize <= 0 || size == 0) return this
    val newBuf = safeFloatBufOf(size + ((size / stride) * leafSize))

    var leafIterator = 0

    forEachIndexed { i, it ->
        newBuf.put(it)
        if ((i + 1) % stride == 0) repeat(leafSize) { newBuf.put(provider(leafIterator++)) }
    }

    close()
    return newBuf
}
