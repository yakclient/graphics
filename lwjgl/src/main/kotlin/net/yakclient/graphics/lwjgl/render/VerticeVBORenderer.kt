package net.yakclient.graphics.lwjgl.render

import net.yakclient.graphics.util.YakGraphicsUtils
import org.lwjgl.opengl.GL15.*

public class VerticeVBORenderer : VerticeRenderer() {
    override fun bindPointers(data: GLRenderingData) {
//        data.
//        stackPush().use { stack ->
//            val array = stack.mallocFloat(data.vertices.size + data.colors.size + data.normals.size + data.texs.size)
//
//            for(i in 0 until data.verticeCount) {
//                array.put(data.vertices.get(i * data.verticeSize, (i * data.verticeSize) + data.verticeSize).toFloatArray())
//                if (data.colors.size >= i * data.colorSize) array.put(data.colors.get(i * data.colorSize, (i * data.colorSize)+data.colorSize).toFloatArray())
//                if (data.normals.size >= i * data.normalSize) array.put(data.normals.get(i * data.normalSize, (i * data.normalSize)+data.normalSize).toFloatArray())
//                if (data.texs.size >= i * data.texSize) array.put(data.texs.get(i * data.texSize, (i * data.texSize) + data.texSize).toFloatArray())
//            }
//
//            val handle: Int = glGenBuffers()
//            glBindBuffer(GL_ARRAY_BUFFER, handle)
//            glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.verticeBuf), GL_STATIC_DRAW)
//
//            glVertexPointer(data.verticeSize, GL_DOUBLE, 0, 0)
//            glColorPointer(data.colorSize, GL_FLOAT, 0, 0L)
//            glNormalPointer(GL_DOUBLE, 0, 0L)
//            glTexCoordPointer(data.texSize, GL_DOUBLE, 0, 0L)
//        }

        val verticesHandle: Int = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle)
        glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.verticeBuf), GL_STATIC_DRAW)
        glVertexPointer(data.verticeStride, GL_FLOAT, 0, 0)

        if (data.hasColors()) {
            val colorsHandle: Int = glGenBuffers()
            glBindBuffer(GL_ARRAY_BUFFER, colorsHandle)
            glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.colorBuf), GL_STATIC_DRAW)
            glColorPointer(data.colorStride, GL_FLOAT, 0, 0L)
        }

        if (data.hasNormals()) {
            val normalsHandle: Int = glGenBuffers()
            glBindBuffer(GL_ARRAY_BUFFER, normalsHandle)
            glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.normalBuf), GL_STATIC_DRAW)
            glNormalPointer(GL_FLOAT, 0, 0L)
        }

        if (data.hasTexs()) {
            val texsHandle: Int = glGenBuffers()
            glBindBuffer(GL_ARRAY_BUFFER, texsHandle)
            glBufferData(GL_ARRAY_BUFFER, YakGraphicsUtils.flipBuf(data.texBuf), GL_STATIC_DRAW)
            glTexCoordPointer(data.texStride, GL_FLOAT, 0, 0L)
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
}