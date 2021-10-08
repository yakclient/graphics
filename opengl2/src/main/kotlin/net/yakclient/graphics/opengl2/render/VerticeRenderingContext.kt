package net.yakclient.graphics.opengl2.render

import net.yakclient.graphics.api.render.*
import org.lwjgl.opengl.GL11

/**
 * Rendering context provides "context" to render. What
 * this means is that it provides needed GL settings, data
 * and a suggested type of rendering. It represents a
 * singular "model" that can be rendered at any point without
 * interfering with any other rendering done synchronously.
 *
 * @author Durgan McBroom
 *
 * @see RenderingType
 *
 * @see GLRenderingData
 */
public class VerticeRenderingContext(
    public val drawType: Int,
    public val textureType: Int = GL11.GL_TEXTURE_2D,
    public val data: GLRenderingData,
) : RenderingContext {
    public override fun useRenderer(type: RenderingType): Renderer<VerticeRenderingContext> {
        return when (type) {
            RenderingType.VBO -> VerticeVBORenderer(this)
            RenderingType.VAO -> VerticeVAORenderer(this)
            else -> throw UnsupportedRenderingTypeException(type, this.javaClass.name)
        }
    }
}