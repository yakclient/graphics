package net.yakclient.graphics.opengl.render

import net.yakclient.graphics.api.render.Renderer
import org.lwjgl.opengl.GL11

/**
 * Unfortunately the only implementation that slick util has
 * of rendering fonts is using the immediate mode. In version
 * 1.0 this is the only method of font rendering we have but
 * eventually there will be a VBO and VAO implementation.
 */
public class ImmediateFontRenderer(override val context: FontRenderingContext) : Renderer<FontRenderingContext> {
    override fun render() {
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        context.font.drawString(context.value, context.color, context.x, context.y)
        GL11.glDisable(GL11.GL_BLEND)
    }
}