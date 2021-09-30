package net.yakclient.graphics.opengl.render

import net.yakclient.graphics.util.ColorAggregation
import net.yakclient.graphics.util.TexAggregation
import net.yakclient.graphics.util.VerticeAggregation
import net.yakclient.graphics.util.YakGraphicsUtils
import org.lwjgl.opengl.GL11

public class VerticeVAORenderer(context: VerticeRenderingContext) : VerticeRenderer(context) {
    override fun bindPointers() {
        GL11.glVertexPointer(VerticeAggregation.VERTICE_SIZE, 0, YakGraphicsUtils.flipBuf(data.verticeBuf))
        GL11.glColorPointer(ColorAggregation.COLOR_INDEX_SIZE, 0, YakGraphicsUtils.flipBuf(data.colorBuf))
        GL11.glNormalPointer(0, YakGraphicsUtils.flipBuf(data.normalBuf))
        GL11.glTexCoordPointer(TexAggregation.VERTICE_SIZE, 0, YakGraphicsUtils.flipBuf(data.texBuf))
    }
}