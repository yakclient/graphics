package net.yakclient.opengl.gui;

import net.yakclient.opengl.gui.buriedcomponents.Divider;
import net.yakclient.opengl.util.state.Stateful;

public class TestComponent extends GUIComponent {
    @Override
    public ComponentRenderingContext<?> render(GUIProperties properties) {
        final Stateful<Integer> hovered = this.useState(0, 0);
       return create(this.useComponent(new Divider(), 0))
               .addProp("width", 100d)
               .addProp("height", 100d)
               .addProp("x", 50d)
               .addProp("y", 50d)
               .<Runnable>addProp("onHover", ()-> System.out.println(hovered.set(hovered.get() + 1)))
               .addChild(create(this.useComponent(new OtherTestComponent(), 1))).build();
    }
}
