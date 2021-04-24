package net.yakclient.opengl.api.render;

import net.yakclient.opengl.util.TexAggregation;
import net.yakclient.opengl.util.YakGLUtils;
import org.lwjgl.opengl.GL11;

public abstract class GLRenderer implements Renderer {
    private final GLRenderingContext context;

    public GLRenderer(GLRenderingContext context) {
        this.context = context;
    }

    protected abstract void bindPointers();

    protected GLRenderingData getData() {
        return this.context.getData();
    }

    @Override
    public void glRender() {
        final GLRenderingData data = this.getData();

        this.bindPointers();
        if (data.hasVertices()) GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        if (data.hasColors()) GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        if (data.hasNormals()) GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        if (data.hasTexs()) GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glEnable(this.context.getTextureType());

        data.getTexture().bind();

        GL11.glTexCoordPointer(TexAggregation.VERTICE_SIZE, 0, YakGLUtils.flipBuf(data.getTexs()));

        GL11.glDrawArrays(this.context.getDrawType(), 0, data.getVerticeCount());

        data.getTexture().release();

        GL11.glDisable(this.context.getTextureType());

        if (data.hasTexs()) GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        if (data.hasNormals()) GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
        if (data.hasColors()) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        if (data.hasVertices()) GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }
}
