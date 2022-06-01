package net.yakclient.graphics.util

public interface Texture {
    public val parent: Texture?

    public val height: Int
    public val width: Int
    public val offsetX: Int
    public val offsetY: Int

    public fun subTexture(x: Int, y: Int, width: Int, height: Int): Texture

    public fun bind()
    public fun release()
}