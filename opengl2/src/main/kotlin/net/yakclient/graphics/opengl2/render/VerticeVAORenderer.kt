package net.yakclient.graphics.opengl2.render

import net.yakclient.graphics.util.YakGraphicsUtils
import org.lwjgl.opengl.GL11

public class VerticeVAORenderer : VerticeRenderer() {
    override fun bindPointers(data: GLRenderingData) {
        GL11.glVertexPointer(data.verticeSize, 0, YakGraphicsUtils.flipBuf(data.verticeBuf))
        GL11.glColorPointer(data.colorSize, 0, YakGraphicsUtils.flipBuf(data.colorBuf))
        GL11.glNormalPointer(0, YakGraphicsUtils.flipBuf(data.normalBuf))
        GL11.glTexCoordPointer(data.texSize, 0, YakGraphicsUtils.flipBuf(data.texBuf))
    }
}