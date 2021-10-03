package net.yakclient.graphics.util

import net.yakclient.graphics.util.YakTexture
import net.yakclient.graphics.util.VerticeAggregation
import net.yakclient.graphics.util.TexAggregation

public class VacantTexture : YakTexture {

//    override val imageHeight: Int
//        get() = 0
//    override val imageWidth: Int
//        get() = 0
    override val height: Int
        get() = 0
    override val width: Int
        get() = 0


    override fun bind() {}

    override fun release() {}

//    override fun generateTexs(vertices: VerticeAggregation): TexAggregation = TexAggregation()
}