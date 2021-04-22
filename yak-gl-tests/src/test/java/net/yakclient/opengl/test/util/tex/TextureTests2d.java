package net.yakclient.opengl.test.util.tex;

import net.yakclient.opengl.test.OpenGLSetup;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class TextureTests2d {
    public static void main(String[] args) throws IOException {
        OpenGLSetup.setupAndStart(() -> {
            try {
                final Texture tex = TextureLoader.getTexture("PNG", BasicTextureTests.class.getResourceAsStream("/wood.png"));
                tex.bind();
                GL11.glEnable(GL_TEXTURE_2D);

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(100, 100);
                GL11.glTexCoord2f(0, 0);

                GL11.glVertex2f(100 + tex.getTextureWidth(), 100);
                GL11.glTexCoord2f(1, 0);

                GL11.glVertex2f(100 + tex.getTextureWidth(), 100 + tex.getTextureHeight());
                GL11.glTexCoord2f(1, 1);

                GL11.glVertex2f(100, 100 + tex.getTextureHeight());

                GL11.glTexCoord2f(0, 1);
                GL11.glEnd();

                GL11.glDisable(GL_TEXTURE_2D);
                tex.release();
            } catch (IOException e) {
                System.out.println("error");
            }
        });
    }
}
