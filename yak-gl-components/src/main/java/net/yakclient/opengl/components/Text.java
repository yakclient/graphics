package net.yakclient.opengl.components;

import net.yakclient.opengl.api.gui.BuriedGUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.render.FontRenderingContext;
import net.yakclient.opengl.api.render.GLRenderingContext;

public class Text extends BuriedGUIComponent {
    @Override
    public GLRenderingContext[] glRender(GUIProperties properties) {
        final var specs = new TextSpecProps(properties);

        final FontRenderingContext fontContext = new FontRenderingContext(specs.getFont(), specs.getValue(), specs.getColor(), specs.getX(), specs.getY());

        return this.combineContexts(fontContext, this.renderChildren(properties));
    }
}
