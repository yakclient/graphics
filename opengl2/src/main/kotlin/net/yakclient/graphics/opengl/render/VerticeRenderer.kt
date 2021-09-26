package net.yakclient.graphics.opengl.render

import net.yakclient.graphics.api.render.GLRenderingData
import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.util.TexAggregation
import net.yakclient.graphics.util.YakGLUtils
import org.lwjgl.opengl.GL11

public abstract class VerticeRenderer(override val context: VerticeRenderingContext) : Renderer<VerticeRenderingContext> {
    protected abstract fun bindPointers()
    protected val data: GLRenderingData
        protected get() = context.data

    override fun render() {
        val data = data
        bindPointers()
        if (data.hasVertices()) GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        if (data.hasColors()) GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
        if (data.hasNormals()) GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY)
        if (data.hasTexs()) GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
        if (data.hasTexs()) {
            GL11.glEnable(context.textureType)
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        }
        data.texture.bind()
        GL11.glTexCoordPointer(TexAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data.texs.))
        GL11.glDrawArrays(context.getDrawType(), 0, data.verticeCount)
        data.texture.release()
        if (data.hasTexs()) {
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glDisable(context.getTextureType())
        }
        if (data.hasTexs()) GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
        if (data.hasNormals()) GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY)
        if (data.hasColors()) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
        if (data.hasVertices()) GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)
    }
}