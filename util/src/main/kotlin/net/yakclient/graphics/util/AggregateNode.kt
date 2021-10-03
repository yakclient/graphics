package net.yakclient.graphics.util

public interface AggregateNode

public data class Vertice @JvmOverloads constructor(
    val x: Double,
    val y: Double,
    val z: Double = VerticeAggregation.Z_INDEX_2D,
    val r: Double = VerticeAggregation.DEFAULT_R,
) : AggregateNode {
    @JvmOverloads
    public constructor(
        x: Int,
        y: Int,
        z: Int = VerticeAggregation.Z_INDEX_2D.toInt(),
        r: Int =  VerticeAggregation.DEFAULT_R.toInt()
    ) : this(x.toDouble(), y.toDouble(), z.toDouble(), r.toDouble())
}

public data class Normal constructor(
    val x: Double,
    val y: Double,
    val z: Double,
) : AggregateNode

public data class Color @JvmOverloads constructor(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float = ColorAggregation.DEFAULT_ALPHA,
) : AggregateNode

public data class TexNode @JvmOverloads constructor(
    val s: Float,
    val t: Float,
    val r: Float = TexAggregation.DEFAULT_R,
) : AggregateNode

