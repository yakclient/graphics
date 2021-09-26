package net.yakclient.graphics.test.util.tex;

import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class BasicTextureTests {
    @Test
    public void getImageDataFromFile() throws IOException {
        System.out.println(this.getImageData("/WoodTex.jpeg"));
    }

    private ByteBuffer getImageData(String name) throws IOException {
        try (final InputStream in = this.getClass().getResourceAsStream(name)) {
            if (in == null) throw new IOException("Failed to fine given image");
            try (final DataInputStream dIn = new DataInputStream(in)) {
                final ByteBuffer buf = BufferUtils.createByteBuffer(in.available());
                final byte[] bytes = new byte[in.available()];

                dIn.readFully(bytes);
                buf.put(bytes);

                return buf;
            }
        }
    }

    private BufferedImage getImage(String name) throws IOException {
        try (final InputStream stream = this.getClass().getResourceAsStream(name)) {
            if (stream == null) throw new IOException("Failed to find specified image");
            return ImageIO.read(stream);
        }
    }

    @Test
    public void bindTex() throws IOException {
        final String imageName = "/WoodTex.jpeg";
        final BufferedImage image = this.getImage(imageName);
        final int width = image.getWidth(), height = image.getHeight();

        final int tex = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, tex);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        final ByteBuffer imageData = this.getImageData(imageName);
        //Type of texture(1d, 2d, 3d,), Mipmap levels(0 since our image doesnt have any), width, height, the border argument should always be 0 as its legacy, Defines the type of color(we are using RGB), the type of data, our bytes wont be negative so they are unsigned, the image data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imageData);
        GL30.glGenerateMipmap(GL_TEXTURE_2D);

        final double[] vertices = new double[]{
                //POSITIONS          COLORS              TEXTURE COORDS
                0.5d, 0.5d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 1.0d,
                0.5d, -0.5d, 0.0d, 0.0d, 1.0d, 0.0d, 1.0d, 0.0d,
                -0.5d, -0.5d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d,
                -0.5d, 0.5d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d, 1.0d
        };

        GL20.glVertexAttribPointer(2, 2, GL_FLOAT, false, 8, 8L);


    }


    public static void main(String[] args) throws IOException {
        try {
            Display.setDisplayMode(new DisplayMode(1000, 1000));
            Display.setResizable(true);
            Display.create();
        } catch (LWJGLException e) {
            System.out.println("FAILED: " + e);
            System.exit(1);
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(150, 640f / 480f, 0.001f, 100f);
//        glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
        glClearDepth(1.0f);                   // Set background depth to farthest
        glEnable(GL_DEPTH_TEST);   // Enable depth testing for z-culling
        glDepthFunc(GL_LEQUAL);    // Set the type of depth-test
        glShadeModel(GL_SMOOTH);   // Enable smooth shading
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nic

        int highestFPS = 0;
        int averageFPS = 0;

        final long startTime = System.currentTimeMillis();

        long lastSecond = System.currentTimeMillis();
        int fps = 0;
        while (!Display.isCloseRequested() && System.currentTimeMillis() - startTime < 60 * 1000) {
            if (System.currentTimeMillis() - lastSecond >= 1000) {
                lastSecond = System.currentTimeMillis();
                Display.setTitle("Current FPS: " + fps);
                if (fps > highestFPS) highestFPS = fps;
                averageFPS = (averageFPS + fps) / 2;
                fps = 0;
            } else fps++;

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers
            glMatrixMode(GL_MODELVIEW);     // To operate on model-view matrix

            // Render a color-cube consisting of 6 quads with different colors
            glLoadIdentity();                 // Reset the model-view matrix
            glTranslatef(1.5f, 1.5f, -1.5f);

            final Texture tex = TextureLoader.getTexture("JPEG", BasicTextureTests.class.getResourceAsStream("/WoodTex.jpeg"));
            tex.bind();

            GL11.glEnable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);

            // Top face (y = 1.0f)
            // Define vertices in counter-clockwise (CCW) order with normal pointing out
            glColor3f(0.0f, 1.0f, 0.0f);     // Green
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);

            // Bottom face (y = -1.0f)
            glColor3f(1.0f, 0.5f, 0.0f);     // Orange
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);

            // Front face  (z = 1.0f)
            glColor3f(1.0f, 0.0f, 0.0f);// Red
            glVertex3f(1.0f, 1.0f, 1.0f);
            glTexCoord2d(1.0f, 1.0f);

            glVertex3f(-1.0f, 1.0f, 1.0f);
            glTexCoord2d(-1.0f, 1.0f);

            glVertex3f(-1.0f, -1.0f, 1.0f);
            glTexCoord2d(-1.0f, -1.0f);

            glVertex3f(1.0f, -1.0f, 1.0f);
            glTexCoord2d(1.0f, -1.0f);


            // Back face (z = -1.0f)
            glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
            glVertex3f(1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, -1.0f);

            // Left face (x = -1.0f)
            glColor3f(0.0f, 0.0f, 1.0f);     // Blue
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glVertex3f(-1.0f, -1.0f, 1.0f);

            // Right face (x = 1.0f)
            glColor3f(1.0f, 0.0f, 1.0f);     // Magenta
            glVertex3f(1.0f, 1.0f, -1.0f);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glVertex3f(1.0f, -1.0f, -1.0f);
            glEnd();  // End of drawing c

            Display.update();
            Display.sync(60);
        }
        System.out.println("Attempting to cleanup resources");

        System.out.println("----- RUNTIME STATS -----");
        System.out.println("HIGHEST FPS: " + highestFPS);
        System.out.println("AVERAGE FPS: " + averageFPS);

        Display.destroy();

    }
}
