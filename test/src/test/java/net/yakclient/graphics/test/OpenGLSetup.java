package net.yakclient.graphics.test;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLSetup {
    private static final int FPS_GOAL = 60;
    private static final int LONGEST_RUNTIME = 600; /* seconds */

    public static void setupAndStart(Runnable gameLoop) {
        setupAndStart(() -> {
        }, gameLoop);
    }

    public static void setupAndStart(Runnable configurations, Runnable gameLoop) {
        try {
            Display.setDisplayMode(new DisplayMode(500, 500));
            Display.setResizable(true);
            Display.create();
        } catch (LWJGLException e) {
            System.out.println("FAILED: " + e);
            System.exit(1);
        }



        configurations.run();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
//        GLU.gluPerspective(100, 640f/480f, 0.001f, 100f);
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        int highestFPS = 0;
        int averageFPS = 0;

        final long startTime = System.currentTimeMillis();

        long lastSecond = System.currentTimeMillis();
        int fps = 0;
        while (!Display.isCloseRequested() && System.currentTimeMillis() - startTime < LONGEST_RUNTIME*1000) {
            if (System.currentTimeMillis() - lastSecond >= 1000) {
                lastSecond = System.currentTimeMillis();
                Display.setTitle("Current FPS: " + fps);
                if (fps > highestFPS) highestFPS = fps;
                averageFPS = (averageFPS + fps) / 2;
                fps = 0;
            } else fps++;

            gameLoop.run();

            Display.update();
            Display.sync(FPS_GOAL);
        }
        System.out.println("Attempting to cleanup resources");
        System.out.println();
        System.out.println("----- RUNTIME STATS -----");
        System.out.println("HIGHEST FPS: " + highestFPS);
        System.out.println("AVERAGE FPS: " + averageFPS);

        Display.destroy();

    }
}
