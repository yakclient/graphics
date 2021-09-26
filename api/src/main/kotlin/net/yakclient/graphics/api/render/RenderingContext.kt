package net.yakclient.graphics.api.render

interface RenderingContext {
    fun useRenderer(type: RenderingType): Renderer<RenderingContext?>?
    fun useRenderer(): Renderer<RenderingContext?>?
}