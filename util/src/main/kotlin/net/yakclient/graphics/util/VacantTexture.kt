package net.yakclient.graphics.util

public class VacantTexture : YakTexture {
    override val height: Int = 0
    override val width: Int = 0

    override fun bind() {}

    override fun release() {}
}