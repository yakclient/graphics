package net.yakclient.graphics.opengl2.render

import net.yakclient.graphics.util.*
import java.nio.DoubleBuffer
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
    private val vertices: VerticeAggregation,
    private val colors: ColorAggregation = ColorAggregation(),
    private val normals: NormalAggregation = NormalAggregation(),
    private val texs: TexAggregation = TexAggregation(),
    public val texture: YakTexture = VacantTexture(),
    public val verticeCount: Int = vertices.size,
) {
    public val verticeBuf: DoubleBuffer by lazy { vertices.asBuffer()}
    public val colorBuf: FloatBuffer by lazy { colors.asBuffer() }
    public val normalBuf: DoubleBuffer by lazy { normals.asBuffer() }
    public val texBuf: FloatBuffer by lazy { texs.asBuffer() }

    public fun hasVertices(): Boolean = !vertices.isEmpty()
    public fun hasColors(): Boolean = !colors.isEmpty()
    public fun hasNormals(): Boolean = !normals.isEmpty()
    public fun hasTexs(): Boolean = !texs.isEmpty()
}