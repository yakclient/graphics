package net.yakclient.graphics.api

import net.yakclient.graphics.api.render.RenderingContext

public interface ContextPropsRenderable {
    public fun glRender(properties: GuiPropertiesMap): Array<RenderingContext>
}