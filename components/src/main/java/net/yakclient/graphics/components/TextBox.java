package net.yakclient.graphics.components;

import net.yakclient.graphics.api.gui.ComponentRenderingContext;
import net.yakclient.graphics.api.gui.GuiComponent;
import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.util.ColorFunction;
import net.yakclient.graphics.util.PaddingFunc;

import java.io.InputStream;
import java.util.Optional;

public class TextBox extends GuiComponent {
    @Override
    public ComponentRenderingContext<?> render(GuiProperties properties) {
        final var specs = new TextSpecProps(properties);
        final var padding = this.requestProp(properties.<PaddingFunc>get("padding")).or(() -> Optional.of(PaddingFunc.pad(0d))).get();
        final var backgroundColor = this.requestProp(properties.<ColorFunction>get("backgroundcolor"));
        final var backgroundImage = this.requestProp(properties.<InputStream>get("backgroundimage"));


        return this.create(this.useComponent(new Box(), 0))
                .addProp("x", specs.getX())
                .addProp("y", specs.getY())
                .addProp("width", specs.getFont().getWidth(specs.getValue()) + padding.getPaddingHorizontal())
                .addProp("height", specs.getFont().getHeight(specs.getValue()) + padding.getPaddingVertical())
                .addProp("backgroundcolor", backgroundColor.isPresent(), backgroundColor::get)
                .addProp("backgroundimage", backgroundImage.isPresent(), backgroundImage::get)

                .addChild(this.create(this.useComponent(new Text(), 1))
                        .addProp("x", specs.getX() + padding.getPaddingLeft())
                        //Assuming the origin is the top left
                        .addProp("y", specs.getY() + padding.getPaddingTop())
                        .addProp("value", specs.getValue())
                        .addProp("color", specs.getColor())
                        .addProp("font", specs.getFont())

                )
                .build();
    }
}
