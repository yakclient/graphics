package net.yakclient.graphics.api.render

import net.yakclient.graphics.util.VerticeAggregation
import net.yakclient.graphics.util.ColorAggregation
import net.yakclient.graphics.util.NormalAggregation
import net.yakclient.graphics.util.TexAggregation
import net.yakclient.graphics.util.YakTexture
import java.nio.DoubleBuffer
import java.nio.FloatBuffer
import net.yakclient.graphics.util.VacantTexture

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
class GLRenderingData(
    public val vertices: VerticeAggregation,
    private val colors: ColorAggregation,
    private val normals: NormalAggregation,
    private val texs: TexAggregation,
    val texture: YakTexture,
    val verticeCount: Int
) {

    constructor(builder: RenderingDataBuilder) : this(builder.vertices,
        builder.colors,
        builder.normals,
        builder.texs,
        builder.texture,
        builder.verticeCount) {
    }



//    fun getVertices(): DoubleBuffer {
//        return vertices.asBuf(vertices.createBuf())
//    }
//
//    fun getNormals(): DoubleBuffer {
//        return normals.asBuf(normals.createBuf())
//    }
//
//    fun getColors(): FloatBuffer {
//        return colors.asBuf(colors.createBuf())
//    }
//
//    fun getTexs(): FloatBuffer {
//        return texs.asBuf(texs.createBuf())
//    }

    public fun hasVertices(): Boolean {
        return !vertices.isEmpty
    }

    public fun hasColors(): Boolean {
        return !colors.isEmpty
    }

    public fun hasNormals(): Boolean {
        return !normals.isEmpty
    }

    public fun hasTexs(): Boolean {
        return !texs.isEmpty
    }

    class RenderingDataBuilder {
        val vertices: VerticeAggregation
        val colors: ColorAggregation
        val normals: NormalAggregation
        val texs: TexAggregation
        val texture: YakTexture
        var verticeCount = 0

        constructor() {
            vertices = VerticeAggregation()
            colors = ColorAggregation()
            normals = NormalAggregation()
            texs = TexAggregation()
            texture = VacantTexture()
        }

        constructor(texture: YakTexture) {
            vertices = VerticeAggregation()
            colors = ColorAggregation()
            normals = NormalAggregation()
            texs = TexAggregation()
            this.texture = texture
        }

        fun addColor3f(r: Float, g: Float, b: Float): RenderingDataBuilder {
            colors.add(r, g, b)
            return this
        }

        fun addColor4f(r: Float, g: Float, b: Float, alpha: Float): RenderingDataBuilder {
            colors.add(r, b, b, alpha)
            return this
        }

        fun addColors(aggregation: ColorAggregation): RenderingDataBuilder {
            colors.combine(aggregation)
            return this
        }

        fun addVertice2d(x: Double, y: Double): RenderingDataBuilder {
            verticeCount++
            vertices.add(x, y)
            return this
        }

        fun addVertice3d(x: Double, y: Double, z: Double): RenderingDataBuilder {
            verticeCount++
            vertices.add(x, y, z)
            return this
        }

        fun addVertices(aggregation: VerticeAggregation): RenderingDataBuilder {
            verticeCount += aggregation.size()
            vertices.combine(aggregation)
            return this
        }

        fun addNormal(x: Double, y: Double, z: Double): RenderingDataBuilder {
            normals.add(x, y, z)
            return this
        }

        fun addNormals(aggregation: NormalAggregation): RenderingDataBuilder {
            normals.combine(aggregation)
            return this
        }

        fun addTexVertice(t: Float, s: Float): RenderingDataBuilder {
            texs.add(t, s)
            return this
        }

        fun addTexs(aggregation: TexAggregation): RenderingDataBuilder {
            texs.combine(aggregation)
            return this
        }

        fun build(): GLRenderingData {
            return GLRenderingData(this)
        }
    }

    companion object {
        fun create(): RenderingDataBuilder {
            return RenderingDataBuilder()
        }

        @JvmStatic
        fun create(texture: YakTexture): RenderingDataBuilder {
            return RenderingDataBuilder(texture)
        }
    }
}