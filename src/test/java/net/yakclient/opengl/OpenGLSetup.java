package net.yakclient.opengl;


import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLSetup {
    private static final int FPS_GOAL = 30;

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
        glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();


//        GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1,-1);

        while (!Display.isCloseRequested()) {
            gameLoop.run();

            Display.update();
            Display.sync(FPS_GOAL);
        }
        Display.destroy();

    }
}
