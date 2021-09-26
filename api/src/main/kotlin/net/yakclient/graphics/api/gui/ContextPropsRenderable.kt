package net.yakclient.graphics.api.gui

import net.yakclient.graphics.api.render.RenderingContext

public interface ContextPropsRenderable {
    public fun glRender(properties: GuiProperties): Array<RenderingContext>
}