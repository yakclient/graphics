package net.yakclient.opengl.api.render;

import net.yakclient.opengl.util.ColorAggregation;
import net.yakclient.opengl.util.TexAggregation;
import net.yakclient.opengl.util.VerticeAggregation;
import net.yakclient.opengl.util.YakGLUtils;
import org.lwjgl.opengl.GL11;

public class VAORenderer extends GLRenderer {
    public VAORenderer(GLRenderingContext context) {
        super(context);
    }

    @Override
    protected void bindPointers() {
        final GLRenderingData data = this.getData();
        GL11.glVertexPointer(VerticeAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data.getVertices()));
        GL11.glColorPointer(ColorAggregation.COLOR_INDEX_SIZE, 0, YakGLUtils.flipBuf(data.getColors()));
        GL11.glNormalPointer(0, YakGLUtils.flipBuf(data.getNormals()));
        GL11.glTexCoordPointer(TexAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data.getTexs()));
    }
}
