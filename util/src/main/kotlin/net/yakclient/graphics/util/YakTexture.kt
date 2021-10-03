package net.yakclient.graphics.util

public interface YakTexture {
//    fun hasAlpha(): Boolean

//    public val imageHeight: Int
//    public val imageWidth: Int
    public val height: Int
    public val width: Int
//    public val textureHeight: Int
//    public val textureWidth: Int

    public fun bind()
    public fun release()

//    public fun generateTexs(vertices: VerticeAggregation): TexAggregation
}