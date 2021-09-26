package net.yakclient.graphics.test;

import net.yakclient.graphics.util.func.LinearFunction;
import net.yakclient.graphics.util.func.SlopedLinearFunction;
import org.junit.jupiter.api.Test;

public class GeneralTests {
   @Test
    public void testLinearFuncBounding() {
       final LinearFunction function = new SlopedLinearFunction(1,  0, 1);

       System.out.println(function.isBounding(2.5, 2.4));
   }
}