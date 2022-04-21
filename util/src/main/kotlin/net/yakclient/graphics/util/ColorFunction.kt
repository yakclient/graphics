package net.yakclient.graphics.util

import net.yakclient.graphics.util.buffer.SafeFloatBuffer

public fun interface ColorFunction {
    public fun toAggregation(vertices: SafeFloatBuffer): SafeFloatBuffer

    public companion object {
        private fun rotateToArray(arr: List<Float>, d: Int) : FloatArray {
            val toReturn = FloatArray(arr.size)

            val temp = FloatArray(d)

            for (i in 0 until d) temp[i] = arr[i]

            for (i in d until arr.size) {
                toReturn[i - d] = arr[i]
            }

            for (i in 0 until d) {
                toReturn[i + arr.size - d] = temp[i]
            }

            return toReturn
        }

        public fun applyColorEffect(
            offset: Int,
            vertices: SafeFloatBuffer,
            vararg colors: Color
        ): SafeFloatBuffer {

            TODO()
//            if (colors.isEmpty()) return safeFloatBufOf()
//
//            val floats = rotateToArray(colors.flatMap { listOf(it.red, it.blue, it.green, it.alpha) }, offset)
//
//
//            val pointMappings = ArrayList<Int>()
//            for (color in colors.indices) {
//                val initialSize = vertices.size / colors.size
//                val colorSize =
//                    if (color + 1 == colors.size) vertices.size - initialSize * colors.size + initialSize else initialSize
//                for (i in 0 until colorSize) {
//                    pointMappings[i + color * initialSize] = color
//                }
//            }
//
//            val mappings = rotateToArray(pointMappings)

//
//            if (offset != 0) Collections.rotate(pointMappings, offset)
//
//            val floats = safeFloatBufOf(pointMappings.size)
//            for (color in pointMappings) {
//                floats.put(colors)
//            }
//            val points = Array()
//
//            for (color in pointMappings.indices) {
//                val (red, green, blue, a) = colors[pointMappings[(color + offset) % pointMappings.size]]
//                points.add(Color(red, blue, green, a))
//            }
//            return safeFloatBufOf(points)
        }
    }
}

public fun functionalColorFunc(func: (SafeFloatBuffer) -> SafeFloatBuffer) : ColorFunction =
    ColorFunction { aggregation -> func(aggregation) }

