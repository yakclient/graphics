package net.yakclient.graphics.util
//
//import org.newdawn.slick.opengl.Texture
//
//public class SlickTextureWrapper(private val tex: Texture) : YakTexture {
////    override val imageHeight: Int
////        get() = tex.imageHeight
////    override val imageWidth: Int
////        get() = tex.imageWidth
//    override val height: Int
//        get() = tex.textureHeight
//    override val width: Int
//        get() = tex.textureWidth
////    override val textureHeight: Int
////        get() = tex.textureHeight
////    override val textureWidth: Int
////        get() = tex.textureWidth
//
//    override fun bind() { tex.bind() }
//
//    override fun release() { tex.release() }
//
//    //Currently all generated textures will be a stretch fit however this most likely will be refactored to en-compass more stretching types.
////    override fun generateTexs(vertices: VerticeAggregation): TexAggregation {
////        if (vertices.isEmpty()) return TexAggregation()
////        var maxX = vertices[0].x
////        var maxY = vertices[0].y
////        var maxZ = vertices[0].z
////        var minX = vertices[0].x
////        var minY = vertices[0].y
////        var minZ = vertices[0].z
////        for ((x, y, z) in vertices) {
////            if (maxX < x) maxX = x
////            if (maxY < y) maxY = y
////            if (maxZ < z) maxZ = z
////            if (minX > x) minX = x
////            if (minY > y) minY = y
////            if (minZ > z) minZ = z
////        }
////        val aggregation = ArrayList<TexNode>()
////        for ((x, y, z) in vertices) {
////            val xDif = maxX - minX
////            val yDif = maxY - minY
////            val zDif = maxZ - minZ
////            aggregation.add(TexNode(
////                (if (xDif == 0.0) 0 else (x - minX) / xDif).toFloat(),
////                (if (yDif == 0.0) 0 else (y - minY) / yDif).toFloat(),
////                (if (zDif == 0.0) 0 else (z - minZ) / zDif).toFloat()))
////        }
////        return TexAggregation(aggregation)
////    }
//}