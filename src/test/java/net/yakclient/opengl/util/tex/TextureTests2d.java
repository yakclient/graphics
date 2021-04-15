package net.yakclient.opengl.util.tex;

import net.yakclient.opengl.OpenGLSetup;
import org.junit.jupiter.api.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class TextureTests2d {
    //-Djava.library.path=/Users/durgan/IdeaProjects/YakClient/YakOpenGLAPI/lib/natives/osx
    public static void main(String[] args) throws IOException {
        OpenGLSetup.setupAndStart(() -> {
            try {
                final Texture texture = TextureLoader.getTexture("PNG", BasicTextureTests.class.getResourceAsStream("/wood.png"));
                texture.bind();
                GL11.glEnable(GL_TEXTURE_2D);

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(100, 100);
                GL11.glTexCoord2f(0, 0);

                GL11.glVertex2f(100 + texture.getTextureWidth(), 100);
                GL11.glTexCoord2f(1, 0);

                GL11.glVertex2f(100 + texture.getTextureWidth(), 100 + texture.getTextureHeight());
                GL11.glTexCoord2f(1, 1);

                GL11.glVertex2f(100, 100 + texture.getTextureHeight());

                GL11.glTexCoord2f(0, 1);
                GL11.glEnd();
            } catch (IOException e) {
                System.out.println("error");
            }
        });
    }
}
