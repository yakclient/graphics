package net.yakclient.graphics.util

import org.lwjgl.BufferUtils
import java.nio.*
import kotlin.math.floor
import kotlin.collections.toByteArray as toKBA

public abstract class Aggregation<out T : AggregateNode, out B : Buffer>(
    delegate: List<T> = ArrayList(),
) : List<T> by delegate {
    public abstract fun asBuffer(): B

//    public abstract fun asFloatBuffer(): FloatBuffer

}

private fun List<Number>.toByteArray(): ByteArray = map { it.toInt().toByte() }.toKBA()

private fun byteBufOf(size: Int): ByteBuffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())

private fun <T : AggregateNode, N : Number> aggregationDelegateOf(
    verticeSize: Int,
    points: List<N>,
    pointBuilder: (List<N>) -> T
): List<T> =
    points.withIndex()
        .groupBy { floor((it.index / verticeSize).toDouble()) }
        .map { it.value.map { v -> v.value } }
        .map(pointBuilder)

public class VerticeAggregation(
    delegate: List<Vertice> = ArrayList(),
) : Aggregation<Vertice, DoubleBuffer>(delegate) {
    public companion object {
        public const val VERTICE_SIZE: Int = 4
        public const val Z_INDEX_2D: Double = 0.0
        public const val DEFAULT_R: Double = 1.0
    }

    override fun asBuffer(): DoubleBuffer = //ByteBuffer.allocateDirect().asFloatBuffer()
//        BufferUtils.createDoubleBuffer(size * VERTICE_SIZE)
        byteBufOf((size * VERTICE_SIZE) shl 3).asDoubleBuffer()
            .put(this.flatMap { (x, y, z, r) -> listOf(x, y, z, r) }.toDoubleArray())
}

public fun verticesOf(vararg vertices: Vertice): VerticeAggregation =
    VerticeAggregation(ArrayList<Vertice>().apply { addAll(vertices) })

public fun verticesOf(verticeSize: Int, vararg points: Double): VerticeAggregation {
    require(verticeSize >= 2) { "A vertice must have atleast 2 points!" };

    return VerticeAggregation(aggregationDelegateOf(verticeSize, points.toList()) {
        Vertice(
            it[0],
            it[1],
            it.getOrElse(2) { VerticeAggregation.Z_INDEX_2D },
            it.getOrElse(3) { VerticeAggregation.DEFAULT_R }
        )
    })
}

public class ColorAggregation(
    delegate: List<Color> = ArrayList(),
) : Aggregation<Color, FloatBuffer>(delegate) {
    public companion object {
        public const val DEFAULT_ALPHA: Float = 1.0f
        public const val COLOR_SIZE: Int = 4
    }

    override fun asBuffer(): FloatBuffer = //ByteBuffer.allocateDirect().asFloatBuffer()
//        BufferUtils.createFloatBuffer(size * COLOR_SIZE)
        byteBufOf(size * COLOR_SIZE shl 2).asFloatBuffer()
            /*byteBufOf((size * COLOR_SIZE) shl 2).asFloatBuffer()*/
            .put(this.flatMap { (r, g, b, a) -> listOf(r, g, b, a) }.toFloatArray())
}

public fun colorsOf(vararg colors: Color): ColorAggregation =
    ColorAggregation(ArrayList<Color>().apply { addAll(colors) })

@JvmOverloads
public fun colorsOf(vararg colors: Float, hasAlpha: Boolean = false): ColorAggregation {
    return ColorAggregation(aggregationDelegateOf((3 + if (hasAlpha) 1 else 0), colors.toList()) {
        Color(
            it[0],
            it[1],
            it[2],
            it.getOrElse(3) { ColorAggregation.DEFAULT_ALPHA }
        )
    })

//    return ColorAggregation(colors.withIndex()
//        .groupBy { floor((it.index / (3 + if (hasAlpha) 1 else 0)).toDouble()) }
//        .map { it.value.map { v -> v.value } }
//        .map {
//            Color(
//                it[0],
//                it[1],
//                it[2],
//                it.getOrElse(3) { ColorAggregation.DEFAULT_ALPHA }
//            )
//        })
}

public class TexAggregation(
    delegate: List<TexNode> = ArrayList(),
) : Aggregation<TexNode, FloatBuffer>(delegate) {
    public companion object {
        public const val VERTICE_SIZE: Int = 3
        public const val DEFAULT_R: Float = 0f
    }

    override fun asBuffer(): FloatBuffer = //ByteBuffer.allocateDirect().asFloatBuffer()
//        BufferUtils.createFloatBuffer(size * VERTICE_SIZE)
            byteBufOf((size * VERTICE_SIZE) shl 2).asFloatBuffer()
            .put(this.flatMap { (s, t, r) -> listOf(s, t, r) }.toFloatArray())

}

public fun texsOf(vararg texs: TexNode): TexAggregation = TexAggregation(listOf(*texs))

public class NormalAggregation(
    delegate: List<Normal> = ArrayList(),
) : Aggregation<Normal, DoubleBuffer>(delegate) {
    public companion object {
        public const val NORMAL_SIZE: Int = 3
    }

    override fun asBuffer(): DoubleBuffer = //ByteBuffer.allocateDirect().asFloatBuffer()
//        BufferUtils.createDoubleBuffer(size * NORMAL_SIZE)
               byteBufOf((size * NORMAL_SIZE) shl 3).asDoubleBuffer()
            .put(this.flatMap { (x, y, z) -> listOf(x, y, z) }.toDoubleArray())

}