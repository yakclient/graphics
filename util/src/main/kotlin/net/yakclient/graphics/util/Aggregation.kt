package net.yakclient.graphics.util

import java.nio.DoubleBuffer
import java.nio.FloatBuffer

public abstract class Aggregation<T : AggregateNode>(
    delegate: List<T> = ArrayList(),
) : List<T> by delegate {
    public abstract fun asDoubleBuffer(): DoubleBuffer

    public abstract fun asFloatBuffer(): FloatBuffer
}

public class VerticeAggregation(
    delegate: List<Vertice> = ArrayList(),
) : Aggregation<Vertice>(delegate) {
    override fun asDoubleBuffer(): DoubleBuffer =
        DoubleBuffer.allocate(size)
            .put(YakGraphicsUtils.toPrimitiveArray(this.flatMap { (x, y, z, r) -> listOf(x, y, z, r) }.toTypedArray()))

    override fun asFloatBuffer(): FloatBuffer =
        FloatBuffer.allocate(size)
            .put(YakGraphicsUtils.toPrimitiveArray(this.flatMap { (x, y, z, r) -> listOf(x, y, z, r) }
                .map { it.toFloat() }.toTypedArray()))

    public companion object {
        public const val VERTICE_SIZE: Int = 4
        public const val Z_INDEX_2D: Double = 0.0
        public const val DEFAULT_R: Double = 1.0
    }
}

public fun verticesOf(vararg vertices: Vertice): VerticeAggregation = VerticeAggregation(ArrayList<Vertice>().apply { addAll(vertices) })

public class ColorAggregation(
    delegate: List<Color> = ArrayList(),
) : Aggregation<Color>(delegate) {
    public companion object {
        public const val DEFAULT_ALPHA: Float = 1.0f
        public const val COLOR_INDEX_SIZE: Int = 4
    }

    override fun asDoubleBuffer(): DoubleBuffer =
        DoubleBuffer.allocate(size)
            .put(YakGraphicsUtils.toPrimitiveArray(this.flatMap { (r, g, b, a) -> listOf(r, g, b, a) }
                .map { it.toDouble() }.toTypedArray()))

    override fun asFloatBuffer(): FloatBuffer =
        FloatBuffer.allocate(size)
            .put(YakGraphicsUtils.toPrimitiveArray(this.flatMap { (r, g, b, a) -> listOf(r, g, b, a) }.toTypedArray()))
}

public class TexAggregation(
    delegate: List<TexNode> = ArrayList(),
) : Aggregation<TexNode>(delegate) {
    public companion object {
        public const val VERTICE_SIZE: Int = 3
        public const val DEFAULT_R: Float = 0f
    }

    override fun asDoubleBuffer(): DoubleBuffer = DoubleBuffer.allocate(size)
        .put(YakGraphicsUtils.toPrimitiveArray(flatMap { (s, t, r) -> listOf(s, t, r) }.map { it.toDouble() }
            .toTypedArray()))

    override fun asFloatBuffer(): FloatBuffer = FloatBuffer.allocate(size)
        .put(YakGraphicsUtils.toPrimitiveArray(flatMap { (s, t, r) -> listOf(s, t, r) }
            .toTypedArray()))
}

public fun texsOf(vararg texs: TexNode) : TexAggregation = TexAggregation(listOf(*texs))

public class NormalAggregation(
    delegate: List<Normal> = ArrayList(),
) : Aggregation<Normal>(delegate) {
    public companion object {
        public const val NORMAL_INDEX_SIZE: Int = 3
    }

    override fun asDoubleBuffer(): DoubleBuffer = DoubleBuffer.allocate(size)
        .put(YakGraphicsUtils.toPrimitiveArray(flatMap { (x, y, z) -> listOf(x, y, z) }
            .toTypedArray()))

    override fun asFloatBuffer(): FloatBuffer = FloatBuffer.allocate(size)
        .put(YakGraphicsUtils.toPrimitiveArray(flatMap { (x, y, z) -> listOf(x, y, z) }.map { it.toFloat() }
            .toTypedArray()))

}