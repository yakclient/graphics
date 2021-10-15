package net.yakclient.graphics.api.render

public interface RenderingContext {
    public var needsReRender : Boolean

    public fun useRenderer(type: RenderingType = RenderingType.VBO): Renderer<RenderingContext>
}