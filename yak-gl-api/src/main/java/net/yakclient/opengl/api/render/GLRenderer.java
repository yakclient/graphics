package net.yakclient.opengl.api.render;

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
        this.bindPointers();
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

        final GLRenderingData data = this.getData();

        data.getTexture().bind();
        GL11.glDrawArrays(this.context.getDrawType(), 0, data.getVerticeCount());
        data.getTexture().release();

        GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }
}
