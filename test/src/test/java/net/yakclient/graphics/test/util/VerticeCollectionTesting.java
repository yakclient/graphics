package net.yakclient.graphics.test.util;

import net.yakclient.graphics.util.VerticeAggregation;
import org.junit.jupiter.api.Test;


public class VerticeCollectionTesting {
    @Test
    public void testVerticeCollection() {
        VerticeAggregation vertices = new VerticeAggregation();
        vertices.add(20, 20).add(10, 10, 10);

        vertices.add(10, 10, 10);

        System.out.println(vertices);
    }

    //-Djava.library.path=/Users/durgan/IdeaProjects/YakClient/YakOpenGLAPI/lib/natives/osx
    public static void main(String[] args) {
//        final VerticeAggregation vertices = new VerticeAggregation();
//        vertices.add(-0.5d, -0.5d, 5)
//                .addColor3f(1, 0, 0)
//                .addVertice(0.5d, -0.5d, 1)
//                .addColor3f(0, 1, 0)
//                .addVertice(1, 1, 1)
//                .addColor3f(0, 0, 1);
//
//
//        final DoubleBuffer vertexBuf = YakGLUtils.flipBuf(vertices.asBuf(vertices.createBuf()));
//        final FloatBuffer colorBuf = YakGLUtils.flipBuf(vertices.colorsAsBuffer());
//
//        OpenGLSetup.setupAndStart(() -> {
//          GL11.glMatrixMode(GL_PROJECTION);
//        }, () -> {
//            GL11.glLoadIdentity();
//
//            glEnableClientState(GL_VERTEX_ARRAY);
//            glEnableClientState(GL_COLOR_ARRAY);
//
//            glVertexPointer(2, 0, vertexBuf);
//            glColorPointer(4, 0, colorBuf);
//
//            glDrawArrays(GL_TRIANGLES, 0, 3);
//
//            glDisableClientState(GL_COLOR_ARRAY);
//            glDisableClientState(GL_VERTEX_ARRAY);
//        });
    }
}