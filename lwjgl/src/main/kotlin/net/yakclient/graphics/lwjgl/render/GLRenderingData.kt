package net.yakclient.graphics.lwjgl.render

import net.yakclient.graphics.util.*
import net.yakclient.graphics.util.buffer.SafeFloatBuffer
import net.yakclient.graphics.util.buffer.safeFloatBufOf
import java.io.Closeable
import java.nio.FloatBuffer

/**
 * GLRendering data is considered actual data that needs to be
 * passed to opengl. It could for example be anything in a buffer
 * or a texture bound to opengl. It maintains data like vertice
 * count.
 *
 *
 * `GLRenderingContext` however doesnt contain what we consider
 * data but contains settings for opengl to process this data with.
 *
 * @author Durgan McBroom
 * @see VerticeRenderingContext
 *
 * @see Aggregation
 *
 * @see YakTexture
 */
public class GLRenderingData @JvmOverloads constructor(
    public val vertices: SafeFloatBuffer,
    public val verticeStride: Int = 4, // The size of 1 vertice: X, Z, Y, R
    public val colors: SafeFloatBuffer = safeFloatBufOf(),
    public val colorStride: Int = 4, // The size of 1 color: R, G, B, A
    public val normals: SafeFloatBuffer = safeFloatBufOf(),
    public val normalStride: Int = 3, // The size of 1 normal : X, Z, Y
    public val texs: SafeFloatBuffer = safeFloatBufOf(),
    public val texStride: Int = 3, // The size of 1 tex coordinate: X, Z, Y
    public val texture: YakTexture = VacantTexture(),
    public val verticeCount: Int = vertices.size / verticeStride,
) : Closeable {
    public val verticeBuf: FloatBuffer by vertices::buffer
    public val colorBuf: FloatBuffer by colors::buffer
    public val normalBuf: FloatBuffer by normals::buffer
    public val texBuf: FloatBuffer by texs::buffer
    init {
        assert(verticeCount != 0 && vertices.size != 0) {"Must be $verticeCount vertices!"}
    }

    public fun hasColors(): Boolean = colors.size != 0
    public fun hasNormals(): Boolean = normals.size != 0
    public fun hasTexs(): Boolean = texture !is VacantTexture

    override fun close() {
        vertices.close()
        colors.close()
        normals.close()
        texs.close()
    }
}