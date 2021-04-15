package net.yakclient.opengl.util.func;

public abstract class LinearFunction {
   private static final double RAD_90 = 1.5707963267948966D;
    private static final double RAD_180 = 3.141592653589793D;
    private static final double RAD_270 = 4.71238898038469D;
    private final int face;
    private final double offset;

    public LinearFunction(int face, double offset) {
        this.face = face;
        this.offset = offset;
    }

    public abstract boolean isBounding(double x, double y);

    public int getFace() {
        return face;
    }

    public double getOffset() {
        return offset;
    }

    public static LinearFunction applyFunc(double x, double y, double rads) {
        //Making sure we dont have a negative rotation
        final double rot = Math.abs(rads);

        //If it is a horizontal rotation we can just return a predetermined function
        if (rot == 0 || rot == RAD_180) return new YFunction(rot == 0 ? 1 : -1, y);

        //If it is vertical we can do the same
        if (rot == RAD_90 || rot == RAD_270) return new XFunction(rot == RAD_90 ? 1 : -1, x);

        //We first have to determine the slope and subtract by RAD_90 to get the correct orientation
        final double slope = Math.cos(RAD_90 - rot) / Math.sin(RAD_90 - rot);
        //Then we apply the face(int of 1 or -1 to determine which way the function is facing)
        final int face = rot < RAD_90 ? 1 : rot > RAD_90 && rot < RAD_180 ? -1 : rot > RAD_180 && rot < RAD_270 ? -1 : 1;
        //And then we apply to a linear function
        return new SlopedLinearFunction(face, y - (slope * x), slope);
    }
}
