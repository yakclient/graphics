package net.yakclient.graphics.components

import net.yakclient.graphics.api.gui.GuiProperties
import net.yakclient.graphics.api.gui.NativeGuiComponent
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.opengl.render.FontRenderingContext

public class Text : NativeGuiComponent() {
    public override fun renderNatively(props: GuiProperties): List<RenderingContext> {
        val specs = TextSpecProps(props)
        return this.combine(FontRenderingContext(specs.font, specs.value, specs.color, specs.x, specs.y), this.applyChildren(props))
    }
}