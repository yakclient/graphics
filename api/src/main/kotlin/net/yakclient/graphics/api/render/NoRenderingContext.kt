package net.yakclient.graphics.api.render

public object NoRenderingContext : RenderingContext {
    override fun useRenderer(type: RenderingType): Renderer<RenderingContext> =
        throw UnsupportedOperationException("Invalid action")
}