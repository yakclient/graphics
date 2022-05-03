package net.yakclient.graphics.api.render

public interface RenderingContext {
    public var needsReRender: Boolean

    public fun reduce(): List<RenderingContext>

    public fun combine(other: RenderingContext): RenderingContext?

    public fun combinable(other: RenderingContext): Boolean

    public fun useRenderer(type: RenderingType = RenderingType.VBO): Renderer<*>

    public fun renderUsingDefault(type: RenderingType = RenderingType.VBO): Unit =
        (useRenderer(type) as Renderer<RenderingContext>).render(this)
}

public operator fun RenderingContext.plus(other: RenderingContext): RenderingContext? = combine(other)
