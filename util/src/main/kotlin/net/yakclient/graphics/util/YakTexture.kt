package net.yakclient.graphics.util

public interface YakTexture {
    public val height: Int
    public val width: Int

    public fun bind()
    public fun release()
}