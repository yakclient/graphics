package net.yakclient.opengl.test.gui;

import net.yakclient.opengl.api.gui.ComponentRenderingContext;
import net.yakclient.opengl.api.gui.GUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.gui.state.Stateful;
import net.yakclient.opengl.components.Divider;
import net.yakclient.opengl.util.ColorCodes;
import net.yakclient.opengl.util.SolidRGBColor;

import java.util.function.Consumer;

public class TestComponent extends GUIComponent {
    @Override
    public ComponentRenderingContext<?> render(GUIProperties properties) {
        final Stateful<Integer> hovered = this.useState(0, 0);
       return create(this.useComponent(new Divider(), 0))
               .addProp("width", 100d)
               .addProp("height", 100d)
               .addProp("x", 50d)
               .addProp("y", 70d)
//               .<Runnable>addProp("onHover", ()-> System.out.println("Hovering"))
//               .<Runnable>addProp("onclick", ()-> System.out.println("Clicked"))
//               .<Runnable>addProp("ondbclick", ()-> System.out.println("Double Clicked"))
//               .<Runnable>addProp("onHover", ()-> System.out.println("Hovering"))
//               .<Runnable>addProp("onmousedown", ()-> System.out.println("Mouse Down"))
//               .<Runnable>addProp("onmouseup", ()-> System.out.println("Mouse Up"))
//               .<Runnable>addProp("onmouseover", ()-> System.out.println("Mouse Over"))
//               .<Runnable>addProp("onmousemove", ()-> System.out.println("Mouse Move"))
               .<Consumer<Integer>>addProp("onkeydown", (key)-> System.out.println("Key Down: " + key))
               .<Consumer<Integer>>addProp("onkeyup", (key)-> System.out.println("Key Up: " + key))
//               .addProp("backgroundcolor", new SolidRGBColor(ColorCodes.BISQUE, 0.5f))
//               .addProp("backgroundimage", getClass().getResourceAsStream("/wood.png"))

               .addChild(create(this.useComponent(new OtherTestComponent(), 1))).build();
    }
}
