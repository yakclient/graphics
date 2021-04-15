package net.yakclient.opengl.render;

import net.yakclient.opengl.util.ColorAggregation;
import net.yakclient.opengl.util.VerticeAggregation;
import net.yakclient.opengl.util.YakGLUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class VBORenderer extends GLRenderer {
    public VBORenderer(GLRenderingContext context) {
        super(context);
    }

    @Override
    protected void bindPointers() {
        final GLRenderingData data = this.getData();

        final int verticesHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesHandle);
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data.getVertices()), GL_STATIC_DRAW);
        glVertexPointer(VerticeAggregation.VERTICE_SIZE, GL_DOUBLE, 0, 0L);

        final int colorsHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorsHandle);
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data.getColors()), GL_STATIC_DRAW);
        glColorPointer(ColorAggregation.COLOR_INDEX_SIZE, GL_FLOAT, 0, 0L);

        final int normalsHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalsHandle);
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data.getNormals()), GL_STATIC_DRAW);
        glNormalPointer(GL_DOUBLE, 0, 0L);


        final int texsHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texsHandle);
        glBufferData(GL_ARRAY_BUFFER, YakGLUtils.flipBuf(data.getTexs()), GL_STATIC_DRAW);
        glNormalPointer(GL_DOUBLE, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
