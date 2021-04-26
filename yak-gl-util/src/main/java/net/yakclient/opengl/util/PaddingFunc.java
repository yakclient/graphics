package net.yakclient.opengl.util;

public class PaddingFunc {
    private final double paddingTop,
            paddingBottom,
            paddingRight,
            paddingLeft;

    private PaddingFunc(double paddingTop, double paddingBottom, double paddingRight, double paddingLeft) {
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingRight = paddingRight;
        this.paddingLeft = paddingLeft;
    }

    public static PaddingFunc pad(double top, double bottom, double right, double left) {
        return new PaddingFunc(top, bottom, right, left);
    }

    public static PaddingFunc pad(double vertical, double horizontal) {
        return new PaddingFunc(vertical, vertical, horizontal, horizontal);
    }

    public static PaddingFunc pad(double all) {
        return new PaddingFunc(all, all, all, all);
    }

    public double getPaddingTop() {
        return paddingTop;
    }

    public double getPaddingBottom() {
        return paddingBottom;
    }

    public double getPaddingRight() {
        return paddingRight;
    }

    public double getPaddingLeft() {
        return paddingLeft;
    }

    public double getPaddingVertical() {
        return getPaddingBottom() + getPaddingTop();
    }

    public double getPaddingHorizontal() {
        return getPaddingRight() + getPaddingLeft();
    }
}
