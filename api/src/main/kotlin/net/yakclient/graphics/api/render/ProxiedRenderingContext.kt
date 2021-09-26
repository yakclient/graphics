package net.yakclient.graphics.api.render

public class ProxiedRenderingContext(
    private val delegates: List<RenderingContext>
) : RenderingContext
