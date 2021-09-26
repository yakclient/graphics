package net.yakclient.graphics.util.func;

public class SlopedLinearFunction extends LinearFunction {
    private final double xSlope;

    public SlopedLinearFunction(int face, double offset, double xSlope) {
        super(face, offset);
        this.xSlope = xSlope;
    }

    @Override
    public boolean isBounding(double x, double y) {
        //y=this.slope * x + y
        return y * this.getFace() >= (x * this.xSlope + this.getOffset()) * this.getFace();
    }
}
