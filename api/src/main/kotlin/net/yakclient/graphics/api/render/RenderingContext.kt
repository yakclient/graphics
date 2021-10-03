package net.yakclient.graphics.api.render

public interface RenderingContext {
    public fun useRenderer(type: RenderingType): Renderer<RenderingContext>
}