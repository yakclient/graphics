package net.yakclient.graphics.api.render

public interface Renderer<out T: RenderingContext>{
    public val context: T

    public abstract fun render()
}