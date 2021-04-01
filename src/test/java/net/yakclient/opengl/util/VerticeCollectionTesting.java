package net.yakclient.opengl.util;

import net.yakclient.opengl.OpenGLSetup;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class VerticeCollectionTesting {
    @Test
    public void testVerticeCollection() {
        VerticeCollection2 vertices = new VerticeCollection2();
        vertices.addVertice(20, 20).addColor3f(10, 10, 10);

        vertices.addNormal(10, 10, 10);

        System.out.println(vertices);
    }

    //-Djava.library.path=/Users/durgan/IdeaProjects/YakClient/YakOpenGLAPI/lib/natives/osx
    public static void main(String[] args) {
        final VerticeCollection3 vertices = new VerticeCollection3();
        vertices.addVertice(-0.5d, -0.5d, 5)
                .addColor3f(1, 0, 0)
                .addVertice(0.5d, -0.5d, 1)
                .addColor3f(0, 1, 0)
                .addVertice(1, 1, 1)
                .addColor3f(0, 0, 1);


        final DoubleBuffer vertexBuf = YakGLUtils.flipBuf(vertices.verticesAsBuffer());
        final FloatBuffer colorBuf = YakGLUtils.flipBuf(vertices.colorsAsBuffer());

        OpenGLSetup.setupAndStart(() -> {
          GL11.glMatrixMode(GL_PROJECTION);
        }, () -> {
            GL11.glLoadIdentity();

            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);

            glVertexPointer(2, 0, vertexBuf);
            glColorPointer(4, 0, colorBuf);

            glDrawArrays(GL_TRIANGLES, 0, 3);

            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);
        });
    }
}