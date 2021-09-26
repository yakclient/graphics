package net.yakclient.graphics.opengl.render

import org.lwjgl.opengl.GL11

class VerticeVAORenderer(context: VerticeRenderingContext?) : VerticeRenderer(context) {
    override fun bindPointers() {
        val data = this.data
        GL11.glVertexPointer(VerticeAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data!!.vertices))
        GL11.glColorPointer(ColorAggregation.COLOR_INDEX_SIZE, 0, YakGLUtils.flipBuf(data.colors))
        GL11.glNormalPointer(0, YakGLUtils.flipBuf(data.normals))
        GL11.glTexCoordPointer(TexAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data.texs))
    }
}