package net.yakclient.graphics.lwjgl.render

import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.api.render.RenderingContext
import net.yakclient.graphics.api.render.RenderingType
import net.yakclient.graphics.api.render.UnsupportedRenderingTypeException
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

    override var needsReRender: Boolean = true

    override fun reduce(): List<RenderingContext> = listOf()
    override fun combine(other: RenderingContext): RenderingContext? =
        if (other is VerticeRenderingContext) tryBatch(other) else null

    override fun combinable(other: RenderingContext): Boolean =
        other is VerticeRenderingContext && couldBatch(other)

    public override fun useRenderer(type: RenderingType): Renderer<VerticeRenderingContext> {
        return when (type) {
            RenderingType.VBO -> VerticeVBORenderer()
            RenderingType.VAO -> VerticeVAORenderer()
            else -> throw UnsupportedRenderingTypeException(type, this.javaClass.name)
        }
    }
}