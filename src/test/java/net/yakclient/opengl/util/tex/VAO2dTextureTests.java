package net.yakclient.opengl.util.tex;

import net.yakclient.opengl.OpenGLSetup;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public class VAO2dTextureTests {
    public static void main(String[] args) {
        OpenGLSetup.setupAndStart(() -> {
            try {
                final Texture tex = TextureLoader.getTexture("PNG", BasicTextureTests.class.getResourceAsStream("/wood.png"));

                final DoubleBuffer vertices = BufferUtils.createDoubleBuffer(8);
                vertices.put(new double[]{
                        10, 10,
                        10 + tex.getTextureWidth(), 10,
                        10 + tex.getTextureWidth(), 10 + tex.getTextureHeight(),
                        10, 10 + tex.getTextureHeight()});

                final FloatBuffer texs = BufferUtils.createFloatBuffer(8);
                texs.put(new float[]{
                        0, 0,
                        1, 0,
                        1, 1,
                        0, 1
                });

                vertices.flip();
                texs.flip();

                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                tex.bind();

                GL11.glVertexPointer(2, 0, vertices);
                GL11.glTexCoordPointer(2,0, texs);
                GL11.glTexCoordPointer(2, 0, texs);

                GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

                tex.release();
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

//                final Texture texture = TextureLoader.getTexture("PNG", BasicTextureTests.class.getResourceAsStream("/wood.png"));
//                texture.bind();
//                GL11.glEnable(GL_TEXTURE_2D);
//
//                GL11.glBegin(GL11.GL_QUADS);
//                GL11.glVertex2f(100, 100);
//                GL11.glTexCoord2f(0, 0);
//
//                GL11.glVertex2f(100 + texture.getTextureWidth(), 100);
//                GL11.glTexCoord2f(1, 0);
//
//                GL11.glVertex2f(100 + texture.getTextureWidth(), 100 + texture.getTextureHeight());
//                GL11.glTexCoord2f(1, 1);
//
//                GL11.glVertex2f(100, 100 + texture.getTextureHeight());
//
//                GL11.glTexCoord2f(0, 1);
//                GL11.glEnd();

            } catch (IOException e) {
                System.out.println("IO exception");
            }
        });

    }
}
