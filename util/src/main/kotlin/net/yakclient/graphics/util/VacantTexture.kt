package net.yakclient.graphics.util

public class VacantTexture : Texture {
    override val parent: Texture? = null
    override val height: Int = 0
    override val width: Int = 0
    override val offsetX: Int = 0
    override val offsetY: Int = 0

    override fun subTexture(x: Int, y: Int, width: Int, height: Int): Texture = this

    override fun bind() {}

    override fun release() {}
}