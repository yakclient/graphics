package net.yakclient.opengl.gui;

import net.yakclient.opengl.gui.buriedcomponents.Divider;

public class TestComponent extends GUIComponent {
    @Override
    public ComponentRenderingContext<?> render(GUIProperties properties) {
       return create(this.useComponent(new Divider(), 0))
               .addProp("width", 100d)
               .addProp("height", 100d)
               .addProp("x", 0d)
               .addProp("y", 0d)
               .addChild(create(this.useComponent(new OtherTestComponent(), 1)).build()).build();
    }
}
