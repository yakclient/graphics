package net.yakclient.opengl.util.func;

public class XFunction extends LinearFunction {
    public XFunction(int face, double offset) {
        super(face, offset);
    }

    @Override
    public boolean isBounding(double x, double y) {
        return x * this.getFace()
                >= this.getOffset() * this.getFace();
    }
}
