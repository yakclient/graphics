package net.yakclient.graphics.api.render

public interface Renderer<in T: RenderingContext>{
    public fun render(context: T)
}