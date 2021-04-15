package net.yakclient.opengl.gui;

import org.lwjgl.opengl.GL11;

public class GUIOrigin {
    private final double x;
    private final double y;

    public GUIOrigin(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static GUIOrigin originCenter() {
        return new GUIOrigin(0,0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
