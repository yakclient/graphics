package net.yakclient.graphics.test.util.tex;

import net.yakclient.graphics.test.OpenGLSetup;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class VAO2dTextureTests {
    public static void main(String[] args) {
        OpenGLSetup.setupAndStart(() -> {
            try {
                final var resource = BasicTextureTests.class.getResourceAsStream("/wood.png");
                if (resource == null) throw new NullPointerException("Texture resource is null");
                final var tex = TextureLoader.getTexture("PNG", resource);

                final var vertices = BufferUtils.createDoubleBuffer(12);
                vertices.put(new double[]{
                        10, 10, 0,
                        10 + tex.getTextureWidth(), 10, 0,
                        10 + tex.getTextureWidth(), 10 + tex.getTextureHeight(), 0,
                        10, 10 + tex.getTextureHeight(), 0});

                final var texs = BufferUtils.createFloatBuffer(12);
                texs.put(new float[]{
                        0, 0, 0,
                        1, 0, 0,
                        1, 1, 0,
                        0, 1, 0
                });

                vertices.flip();
                texs.flip();

                tex.bind();

//             if (Mouse.isButtonDown(0))   System.out.println("left");
//              if (Mouse.isButtonDown(1)) System.out.println("right");
//              if (Mouse.isButtonDown(2)) System.out.println("middle");


                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

                GL11.glEnable(GL11.GL_TEXTURE_2D);

                GL11.glVertexPointer(3, 0, vertices);
                GL11.glTexCoordPointer(3, 0, texs);

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
