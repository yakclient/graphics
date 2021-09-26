package net.yakclient.graphics.test.gui;

import net.yakclient.graphics.api.gui.ComponentRenderingContext;
import net.yakclient.graphics.api.gui.GuiComponent;
import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.api.gui.state.Stateful;
import net.yakclient.graphics.components.Box;
import net.yakclient.graphics.components.Text;
import net.yakclient.graphics.components.TextBox;
import net.yakclient.graphics.util.ColorCodes;
import net.yakclient.graphics.util.PaddingFunc;
import net.yakclient.graphics.util.SolidRGBColor;

import java.util.function.Consumer;

public class TestComponent extends GuiComponent {
    @Override
    public ComponentRenderingContext<?> render(GuiProperties properties) {
        final Stateful<Integer> hovered = this.useState(0, 0);
//        return create(useComponent(new Text(), 2))
//                .addProp("value", "Wow!")
//                .addProp("x", 200D)
//                .addProp("y", 200D).build();
        return create(this.useComponent(new Box(), 0))
                .addProp("width", 100d)
                .addProp("height", 100d)
                .addProp("x", 50d)
                .addProp("y", 50d)
//               .<Runnable>addProp("onHover", ()-> System.out.println("Hovering"))
//               .<Runnable>addProp("onclick", ()-> System.out.println("Clicked"))
//               .<Runnable>addProp("ondbclick", ()-> System.out.println("Double Clicked"))
//               .<Runnable>addProp("onHover", ()-> System.out.println("Hovering"))
//               .<Runnable>addProp("onmousedown", ()-> System.out.println("Mouse Down"))
//               .<Runnable>addProp("onmouseup", ()-> System.out.println("Mouse Up"))
//               .<Runnable>addProp("onmouseover", ()-> System.out.println("Mouse Over"))
//               .<Runnable>addProp("onmousemove", ()-> System.out.println("Mouse Move"))
                .<Consumer<Integer>>addProp("onkeydown", (key) -> System.out.println("Key Down: " + key))
                .<Consumer<Integer>>addProp("onkeyup", (key) -> System.out.println("Key Up: " + key))
                .addProp("backgroundcolor", new SolidRGBColor(ColorCodes.WHITE, 1f))
               .addProp("backgroundimage", getClass().getResourceAsStream("/wood.png"))

                .addChild(create(this.useComponent(new OtherTestComponent(), 1)))
                .addChild(create(useComponent(new Text(), 2))
                        .addProp("value", "Wow!")
                        .addProp("x", 75.0d)
                        .addProp("y", 75.0d)
                )
                .addChild(create(useComponent(new TextBox(), 3))
                        .addProp("value", "Im gonna type a really long sentence and see how this wraps!")
                        .addProp("x", 200d)
                        .addProp("y", 200d)
                        .addProp("backgroundcolor", new SolidRGBColor(ColorCodes.TOMATO))
                        .addProp("backgroundimage", getClass().getResourceAsStream("/wood.png"))
                        .addProp("padding", PaddingFunc.pad(20))
                )
                .build();
    }
}
