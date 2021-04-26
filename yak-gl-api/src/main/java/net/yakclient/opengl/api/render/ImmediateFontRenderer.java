package net.yakclient.opengl.api.render;

import org.lwjgl.opengl.GL11;

/**
 * Unfortunately the only implementation that slick util has
 * of rendering fonts is using the immediate mode. In version
 * 1.0 this is the only method of font rendering we have but
 * eventually there will be a VBO and VAO implementation.
 */
public class ImmediateFontRenderer implements Renderer {
    private final FontRenderingContext context;

    public ImmediateFontRenderer(FontRenderingContext context) {
        this.context = context;
    }

    @Override
    public void glRender() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.context.getFont().drawString(this.context.getValue(), this.context.getColor(), this.context.getX(), this.context.getY());
        GL11.glDisable(GL11.GL_BLEND);
    }
}
