package net.yakclient.opengl.test.util.tex;

import net.yakclient.opengl.test.OpenGLSetup;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class VAO2dTextureTests {
    public static void main(String[] args) {
        OpenGLSetup.setupAndStart(() -> {
            try {
                final var resource = BasicTextureTests.class.getResourceAsStream("/wood.png");
                if (resource == null) throw new NullPointerException("Texture resource is null");
                final var tex = TextureLoader.getTexture("PNG", resource);

                final var vertices = BufferUtils.createDoubleBuffer(8);
                vertices.put(new double[]{
                        10, 10,
                        10 + tex.getTextureWidth(), 10,
                        10 + tex.getTextureWidth(), 10 + tex.getTextureHeight(),
                        10, 10 + tex.getTextureHeight()});

                final var texs = BufferUtils.createFloatBuffer(8);
                texs.put(new float[]{
                        0, 0,
                        1, 0,
                        1, 1,
                        0, 1
                });

                vertices.flip();
                texs.flip();

                tex.bind();


                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

                GL11.glEnable(GL11.GL_TEXTURE_2D);

                GL11.glVertexPointer(2, 0, vertices);
                GL11.glTexCoordPointer(2,0, texs);

                GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);

                tex.release();
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

            } catch (IOException e) {
                System.out.println("IO exception");
            }
        });

    }
}
