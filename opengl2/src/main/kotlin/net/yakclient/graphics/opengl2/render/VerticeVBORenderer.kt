package net.yakclient.graphics.opengl2.render

import net.yakclient.graphics.util.VerticeAggregation
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.GL_DOUBLE
import org.lwjgl.opengl.GL11.glVertexPointer
import org.lwjgl.opengl.GL15.*

public class VerticeVBORenderer(context: VerticeRenderingContext) : VerticeRenderer(context) {
    override fun bindPointers() {
        val put = BufferUtils.createDoubleBuffer(8).put(
            doubleArrayOf(
                100.0, 100.0, 100.0, 500.0, 500.0, 100.0, 500.0, 500.0
            )
        )

        put.flip()
// YakGraphicsUtils.flipBuf(data.verticeBuf)
        val verticesHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle)
        glBufferData(GL_ARRAY_BUFFER,put, GL_STATIC_DRAW)
        glVertexPointer(VerticeAggregation.VERTICE_SIZE, GL_DOUBLE, 0, 0L)

//        val colorsHandle: Int = glGenBuffers()
//        glBindBuffer(GL_ARRAY_BUFFER, colorsHandle)
//        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.colorBuf), GL_STATIC_DRAW)
//        glColorPointer(ColorAggregation.COLOR_SIZE, GL_FLOAT, 0, 0L)

//        val normalsHandle: Int = glGenBuffers()
//        glBindBuffer(GL_ARRAY_BUFFER, normalsHandle)
//        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.normalBuf), GL_STATIC_DRAW)
//        glNormalPointer(GL_DOUBLE, 0, 0L)

//        val texsHandle: Int = glGenBuffers()
//        glBindBuffer(GL_ARRAY_BUFFER, texsHandle)
//        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.texBuf), GL_STATIC_DRAW)
//        glNormalPointer(GL_DOUBLE, 0, 0L)

//        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
}