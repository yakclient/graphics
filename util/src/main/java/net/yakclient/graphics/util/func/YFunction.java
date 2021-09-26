package net.yakclient.graphics.util.func;

public class YFunction extends LinearFunction {
    public YFunction(int face, double offset) {
        super(face, offset);
    }

    @Override
    public boolean isBounding(double x, double y) {
        return y * this.getFace()
                >= this.getOffset() * this.getFace();
    }
}
