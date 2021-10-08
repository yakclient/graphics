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

//    public constructor(builder: RenderingDataBuilder) : this(builder.vertices,
//        builder.colors,
//        builder.normals,
//        builder.texs,
//        builder.texture,
//        builder.verticeCount) {
//    }

    public fun hasVertices(): Boolean = !vertices.isEmpty()


    public fun hasColors(): Boolean = !colors.isEmpty()


    public fun hasNormals(): Boolean = !normals.isEmpty()


    public fun hasTexs(): Boolean = !texs.isEmpty()


//    public class RenderingDataBuilder(
//        private val texture: YakTexture
//    ) {
//        private val vertices: List<Vertice> = ArrayList()
//        private val colors: List<ColorNode> = ArrayList()
//        private val normals: List<NormalNode> = ArrayList()
//        private val texs: List<TexNode> = ArrayList()
//        private var verticeCount = 0
//
//
//        fun addColor3f(r: Float, g: Float, b: Float): RenderingDataBuilder {
//            colors.add(r, g, b)
//            return this
//        }
//
//        fun addColor4f(r: Float, g: Float, b: Float, alpha: Float): RenderingDataBuilder {
//            colors.add(r, b, b, alpha)
//            return this
//        }
//
//        fun addColors(aggregation: ColorAggregation): RenderingDataBuilder {
//            colors.combine(aggregation)
//            return this
//        }
//
//        fun addVertice2d(x: Double, y: Double): RenderingDataBuilder {
//            verticeCount++
//            vertices.add(x, y)
//            return this
//        }
//
//        fun addVertice3d(x: Double, y: Double, z: Double): RenderingDataBuilder {
//            verticeCount++
//            vertices.add(x, y, z)
//            return this
//        }
//
//        fun addVertices(aggregation: VerticeAggregation): RenderingDataBuilder {
//            verticeCount += aggregation.size()
//            vertices.combine(aggregation)
//            return this
//        }
//
//        fun addNormal(x: Double, y: Double, z: Double): RenderingDataBuilder {
//            normals.add(x, y, z)
//            return this
//        }
//
//        fun addNormals(aggregation: NormalAggregation): RenderingDataBuilder {
//            normals.combine(aggregation)
//            return this
//        }
//
//        fun addTexVertice(t: Float, s: Float): RenderingDataBuilder {
//            texs.add(t, s)
//            return this
//        }
//
//        fun addTexs(aggregation: TexAggregation): RenderingDataBuilder {
//            texs.combine(aggregation)
//            return this
//        }
//
//        fun build(): GLRenderingData {
//            return GLRenderingData(this)
//        }
//    }

//    companion object {
//        fun create(): RenderingDataBuilder {
//            return RenderingDataBuilder()
//        }
//
//        @JvmStatic
//        fun create(texture: YakTexture): RenderingDataBuilder {
//            return RenderingDataBuilder(texture)
//        }
//    }
}