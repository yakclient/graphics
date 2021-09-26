package net.yakclient.graphics.test.gui;


import net.yakclient.graphics.api.gui.ComponentRenderingContext;
import net.yakclient.graphics.api.gui.GuiComponent;
import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.components.Box;
import net.yakclient.graphics.util.ColorCodes;
import net.yakclient.graphics.util.SolidRGBColor;

public class OtherTestComponent extends GuiComponent {
    @Override
    public ComponentRenderingContext<?> render(GuiProperties properties) {
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
