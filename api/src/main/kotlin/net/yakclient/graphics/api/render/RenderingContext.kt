package net.yakclient.graphics.api.render

public interface RenderingContext {
    public var needsReRender : Boolean

    public fun useRenderer(type: RenderingType = RenderingType.VBO): Renderer<*>

    public fun renderUsingDefault(type: RenderingType = RenderingType.VBO) : Unit = (useRenderer(type) as Renderer<RenderingContext>).render(this)
}