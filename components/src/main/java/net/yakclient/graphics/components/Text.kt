package net.yakclient.graphics.components;

import net.yakclient.graphics.api.gui.NativeGuiComponent;
import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.api.render.FontRenderingContext;
import net.yakclient.graphics.api.render.RenderingContext;

public class Text extends NativeGuiComponent {
    @Override
    public RenderingContext[] glRender(GuiProperties properties) {
        final var specs = new TextSpecProps(properties);

        final FontRenderingContext fontContext = new FontRenderingContext(specs.getFont(), specs.getValue(), specs.getColor(), specs.getX(), specs.getY());

        return this.combineContexts(fontContext, this.renderChildren(properties));
    }
}
