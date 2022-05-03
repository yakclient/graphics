package net.yakclient.graphics.lwjgl.render

import net.yakclient.graphics.api.render.Renderer
import org.lwjgl.opengl.GL11.*

public abstract class VerticeRenderer : Renderer<VerticeRenderingContext> {

    protected abstract fun bindPointers(data: GLRenderingData)

    override fun render(context: VerticeRenderingContext) {
        //TODO batch rendering, seems like textures arent the issue but instead lots of draw calls...
        val data = context.data

        bindPointers(data)

         glEnableClientState(GL_VERTEX_ARRAY)
        if (data.hasColors()) glEnableClientState(GL_COLOR_ARRAY)
        if (data.hasNormals()) glEnableClientState(GL_NORMAL_ARRAY)
        if (data.hasTexs()) {
            glEnableClientState(GL_TEXTURE_COORD_ARRAY)
            glEnable(context.textureType)
            glDisable(GL_BLEND)
            data.texture.bind()
        }

        glDrawArrays(context.drawType, 0, data.verticeCount)

        if (data.hasTexs()) {
            glDisable(GL_BLEND)
            glDisable(context.textureType)
            glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        }
        if (data.hasNormals()) glDisableClientState(GL_NORMAL_ARRAY)
        if (data.hasColors()) glDisableClientState(GL_COLOR_ARRAY)
         glDisableClientState(GL_VERTEX_ARRAY)

        context.data.close()
    }
}