package net.yakclient.opengl.components;


import net.yakclient.opengl.api.gui.BuriedGUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.api.render.GLRenderingData;
import net.yakclient.opengl.util.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;


public class Divider extends BuriedGUIComponent {
    @Override
    public GLRenderingContext[] glRender(GUIProperties properties) {
        try {
            final PropertyManager manager = this.manageProps(properties);

//        final GUIOrigin origin = manager.requireProp(PropertyFactory.ORIGIN_INDEX);
            final double width = manager.requireProp("width");
            final double height = manager.requireProp("height");
            final double x = manager.<Double>requireProp("x");
            final double y = manager.<Double>requireProp("y");
            final var onHover = manager.<Runnable>requestProp("onHover");

            final VerticeAggregation collection = new VerticeAggregation()
                    .add(x, y)
                    .add(x + width, y)
                    .add(x + width, y + height)
                    .add(x, y + height);

            final ColorAggregation colorsDefault = new ColorAggregation()
                    .add(0, 0, 0)
                    .add(0, 0, 0)
                    .add(1, 0, 0)
                    .add(1, 1, 1);

            final ColorAggregation colorsHover = new ColorAggregation()
                    .add(0.5f, 0.5f, 0.5f)
                    .add(0.5f, 0.5f, 0.5f)
                    .add(0.5f, 0.5f, 0.5f)
                    .add(0.5f, 0.5f, 0.5f);

            final ColorAggregation aggregation = ColorFunction.applyColorEffect(0, collection, ColorCodes.CRIMSON);
            final boolean hovering = this.rectBounding(Mouse.getX(), Mouse.getY(), y, x, x + width, y + height);
            if (hovering) onHover.ifPresent(Runnable::run);

            final TexAggregation texs = new TexAggregation();
            texs.add(0, 0).add(1, 0).add(1, 0).add(0, 1);

            final GLRenderingData data = GLRenderingData.create().addTexs(texs).addVertices(collection).addColors(hovering ? aggregation : colorsDefault).build();



            return this.combineContexts(new GLRenderingContext.ContextBuilder(GL11.GL_QUADS, data).build(), this.renderChildren(properties));
        } catch (Exception e) {
            System.out.println("Failed to create texture wood");
        }
        return new GLRenderingContext[0];
    }

    private boolean rectBounding(double x, double y, double top, double left, double bottom, double right) {
        return (top < y && bottom > y) && (left < x && right > x);
    }
}
