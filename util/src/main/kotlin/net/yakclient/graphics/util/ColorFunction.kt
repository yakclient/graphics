package net.yakclient.graphics.util

public fun interface ColorFunction {
    public fun toAggregation(aggregation: VerticeAggregation): ColorAggregation

    public companion object {
        public fun applyColorEffect(
            offset: Int,
            vertices: VerticeAggregation,
            vararg colors: Color
        ): ColorAggregation {
            if (colors.isEmpty()) return ColorAggregation()

            val pointMappings = IntArray(vertices.size)
            for (color in colors.indices) {
                val initialSize = vertices.size / colors.size
                val colorSize =
                    if (color + 1 == colors.size) vertices.size - initialSize * colors.size + initialSize else initialSize
                for (i in 0 until colorSize) {
                    pointMappings[i + color * initialSize] = color
                }
            }
            val points = ArrayList<Color>()

            for (color in pointMappings.indices) {
                val (red, green, blue, a) = colors[pointMappings[(color + offset) % pointMappings.size]]
                points.add(Color(red, blue, green, a))
            }
            return ColorAggregation(points)
        }
    }
}

public fun functionalColorFunc(func: (VerticeAggregation) -> ColorAggregation) : ColorFunction =
    ColorFunction { aggregation -> func(aggregation) }

