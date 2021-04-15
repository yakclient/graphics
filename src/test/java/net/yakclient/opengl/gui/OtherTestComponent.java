package net.yakclient.opengl.gui;

import net.yakclient.opengl.gui.buriedcomponents.Divider;

public class OtherTestComponent extends GUIComponent {
    @Override
    public ComponentRenderingContext<?> render(GUIProperties properties) {
        return create(this.useComponent(new Divider(), 0))
                .addProp("width", 200d)
                .addProp("height", 100d)
                .addProp("x", 50d)
                .addProp("y", 160d).build();
    }
}
