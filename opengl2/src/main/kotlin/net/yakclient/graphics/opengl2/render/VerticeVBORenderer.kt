package net.yakclient.graphics.opengl2.render

import net.yakclient.graphics.util.YakGraphicsUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*

public class VerticeVBORenderer : VerticeRenderer() {
    override fun bindPointers(data: GLRenderingData) {
        val verticesHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.verticeBuf), GL_STATIC_DRAW)
        glVertexPointer(data.verticeSize, GL_DOUBLE, 0, 0)

        val colorsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, colorsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.colorBuf), GL_STATIC_DRAW)
        glColorPointer(data.colorSize, GL_FLOAT, 0, 0L)

        val normalsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, normalsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.normalBuf), GL_STATIC_DRAW)
        glNormalPointer(GL_DOUBLE, 0, 0L)

        val texsHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, texsHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.texBuf), GL_STATIC_DRAW)
        glTexCoordPointer(data.texSize, GL_DOUBLE, 0, 0L)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
}