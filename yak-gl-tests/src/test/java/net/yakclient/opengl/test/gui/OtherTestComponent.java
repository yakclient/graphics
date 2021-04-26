package net.yakclient.opengl.test.gui;


import net.yakclient.opengl.api.gui.ComponentRenderingContext;
import net.yakclient.opengl.api.gui.GUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.components.Box;
import net.yakclient.opengl.util.ColorCodes;
import net.yakclient.opengl.util.SolidRGBColor;

public class OtherTestComponent extends GUIComponent {
    @Override
    public ComponentRenderingContext<?> render(GUIProperties properties) {
        return create(this.useComponent(new Box(), 0))
                .addProp("width", 200d)
                .addProp("height", 100d)
                .addProp("x", 50d)
                .addProp("y", 160d)
                .addProp("backgroundcolor", new SolidRGBColor(ColorCodes.BISQUE, 0.1f))
                .addProp("backgroundimage", getClass().getResourceAsStream("/wood.png"))
                .build();
    }
}
