package net.yakclient.opengl.test.util;

import net.yakclient.opengl.util.func.LinearFunction;
import net.yakclient.opengl.util.func.SlopedLinearFunction;
import org.junit.jupiter.api.Test;

public class LinearFunctionTests {
    @Test
    public void testNegativeYSlopedFunction() {
        final LinearFunction function = new SlopedLinearFunction(1, 0, 1);
        System.out.println(function.isBounding(10,11));
    }

    @Test
    public void testApplyLinFunction() {
        final LinearFunction function = LinearFunction.applyFunc(0,0, Math.toRadians(150));
        System.out.println(function);
    }
}
