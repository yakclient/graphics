package net.yakclient.graphics.util

import org.lwjgl.opengl.GL11

public class YakGLTexture(
    private val texId: Int,
    private val target: Int,
//    override val imageHeight: Int,
//    override val imageWidth: Int,
    override val height: Int,
    override val width: Int,

) : YakTexture {
    override fun bind() {
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glBindTexture(target, texId)
    }

    override fun release() {
        GL11.glDeleteTextures(texId)
    }

//    override fun generateTexs(vertices: VerticeAggregation): TexAggregation {
//        if (vertices.isEmpty()) return TexAggregation()
//        var maxX = vertices[0].x
//        var maxY = vertices[0].y
//        var maxZ = vertices[0].z
//        var minX = vertices[0].x
//        var minY = vertices[0].y
//        var minZ = vertices[0].z
//        for ((x, y, z) in vertices) {
//            if (maxX < x) maxX = x
//            if (maxY < y) maxY = y
//            if (maxZ < z) maxZ = z
//            if (minX > x) minX = x
//            if (minY > y) minY = y
//            if (minZ > z) minZ = z
//        }
//
//        val aggregation = ArrayList<TexNode>()
//        for ((x, y, z) in vertices) {
//            val xDif = maxX - minX
//            val yDif = maxY - minY
//            val zDif = maxZ - minZ
//            aggregation.add(TexNode(
//                (if (xDif == 0.0) 0 else (x - minX) / xDif).toFloat(),
//                (if (yDif == 0.0) 0 else (y - minY) / yDif).toFloat(),
//                (if (zDif == 0.0) 0 else (z - minZ) / zDif).toFloat()))
//        }
//        return TexAggregation(aggregation)
//    }
}