package net.yakclient.graphics.util

public interface YakTexture {
    public val parent: YakTexture?

    public val height: Int
    public val width: Int
    public val offsetX: Int
    public val offsetY: Int

    public fun subTexture(x: Int, y: Int, width: Int, height: Int): YakTexture

    public fun bind()
    public fun release()
}