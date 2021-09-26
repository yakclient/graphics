package net.yakclient.graphics.opengl.render

import net.yakclient.graphics.util.ColorAggregation
import net.yakclient.graphics.util.VerticeAggregation
import net.yakclient.graphics.util.YakGLUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*

class VerticeVBORenderer(context: VerticeRenderingContext?) : VerticeRenderer(context) {
    override fun bindPointers() {
        val data = this.data
        val verticesHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data!!.vertices), GL_STATIC_DRAW)
        glVertexPointer(VerticeAggregation.VERTICE_SIZE, GL_DOUBLE, 0, 0L)
        val colorsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, colorsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data!!.colors), GL_STATIC_DRAW)
        glColorPointer(ColorAggregation.COLOR_INDEX_SIZE, GL_FLOAT, 0, 0L)
        val normalsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, normalsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data!!.normals), GL_STATIC_DRAW)
        glNormalPointer(GL_DOUBLE, 0, 0L)
        val texsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, texsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data!!.texs), GL_STATIC_DRAW)
        glNormalPointer(GL_DOUBLE, 0, 0L)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
}