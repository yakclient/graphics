package net.yakclient.graphics.lwjgl.legacy.render

import net.yakclient.graphics.api.render.Renderer
import net.yakclient.graphics.util.YakGraphicsUtils
import org.lwjgl.opengl.GL11.*

public abstract class VerticeRenderer : Renderer<VerticeRenderingContext> {

    protected abstract fun bindPointers(data: GLRenderingData)

    override fun render(context: VerticeRenderingContext) {
        bindPointers(context.data )

        val data = context.data

        if (data.hasVertices()) glEnableClientState(GL_VERTEX_ARRAY)
        if (data.hasColors()) glEnableClientState(GL_COLOR_ARRAY)
        if (data.hasNormals()) glEnableClientState(GL_NORMAL_ARRAY)
        if (data.hasTexs()) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY)
            glEnable(context.textureType)
//            GL11.glEnable(GL11.GL_BLEND)
            glDisable(GL_BLEND)
//            glEnable(GLCULL)
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        }

        data.texture.bind()
        glTexCoordPointer(data.texSize, 0, YakGraphicsUtils.flipBuf(data.texBuf))
        glDrawArrays(context.drawType, 0, data.verticeCount)
//        data.texture.release()
        if (data.hasTexs()) {
//            GL11.glDisable(GL11.GL_BLEND)
            glDisable(context.textureType)
            glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        }
        if (data.hasNormals()) glDisableClientState(GL_NORMAL_ARRAY)
        if (data.hasColors()) glDisableClientState(GL_COLOR_ARRAY)
        if (data.hasVertices()) glDisableClientState(GL_VERTEX_ARRAY)

    }
}