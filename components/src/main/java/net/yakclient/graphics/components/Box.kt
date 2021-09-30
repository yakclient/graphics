package net.yakclient.graphics.components;


import net.yakclient.graphics.api.gui.NativeGuiComponent;
import net.yakclient.graphics.api.gui.GuiProperties;
import net.yakclient.graphics.api.render.RenderingContext;
import net.yakclient.graphics.api.render.VerticeRenderingContext;
import net.yakclient.graphics.api.render.GLRenderingData;
import net.yakclient.graphics.util.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A {@code Box} represents the closest thing that yak-opengl-api
 * has to the HTML Div tag. It is a box that has all of the same events
 * as a a Div and has some(but not all) of the same styling properties.
 * <p>
 * Note: This class will not wrap children; grow with children; define its
 * top left as its children's origins etc. It purely represents a static box
 * defined by its required properties.
 *
 * @author Durgan McBroom
 * @since 1.0
 */
public class Box extends NativeGuiComponent {
    /**
     * Renders given the passed properties.
     *
     * @param properties Properties to render from
     * @return Context of itself and its children
     */
    @Override
    public RenderingContext[] glRender(GuiProperties properties) {
        //  To manage all properties
        final PropertyManager manager = this.manageProps(properties);

        //  Styling
        final double width = manager.requireProp("width");
        final double height = manager.requireProp("height");
        final double x = manager.requireProp("x");
        final double y = manager.requireProp("y");
        //  Transparency is defined in the background color.
        final Optional<ColorFunction> backgroundColor = manager.requestProp("backgroundcolor");
        final Optional<InputStream> backgroundImage = manager.requestProp("backgroundimage");

        //  Events
        final var onClick = manager.<Runnable>requestProp("onclick");
        final var onDbClick = manager.<Runnable>requestProp("ondbclick");
        final var onMouseDown = manager.<Runnable>requestProp("onmousedown");
        final var onMouseUp = manager.<Runnable>requestProp("onmouseup");
        final var onMouseOver = manager.<Runnable>requestProp("onmouseover");
        final var onMouseMove = manager.<Runnable>requestProp("onmousemove");
        final var onMouseOut = manager.<Runnable>requestProp("onmouseout");
        final var onKeyDown = manager.<Consumer<Integer>>requestProp("onkeydown");
        final var onKeyUp = manager.<Consumer<Integer>>requestProp("onkeyup");
        final var onHover = manager.<Runnable>requestProp("onhover");

        //  State
        final var lastClick = this.useState(System.currentTimeMillis(), 0);
        final var wasLastTickMouseDown = this.useState(false, 1);
        final var lastTickMousePos = this.useState(new Vertice(Mouse.getX(), Mouse.getY()), 2);
        final var focused = this.useState(false, 3);
        final var lastKeyDown = this.useState(YakGLUtils.CHAR_NONE, 4);
        final var wasLastTickKeyDown = this.useState(false, 5);


        //  Event implementation
        final boolean lmbDown = Mouse.isButtonDown(YakGLUtils.MOUSE_LEFT_BUTTON);
        if (wasLastTickMouseDown.get() && !lmbDown) onMouseUp.ifPresent(Runnable::run);

        final boolean isMouseOver = this.rectBounding(Mouse.getX(), Mouse.getY(), y, x, x + width, y + height);
        if (isMouseOver) onMouseOver.ifPresent(Runnable::run);
        if (isMouseOver && lmbDown) onMouseDown.ifPresent(Runnable::run);

        final Vertice ltPos = lastTickMousePos.get();
        if (ltPos.equals(new Vertice(Mouse.getX(), Mouse.getY())) && isMouseOver) onHover.ifPresent(Runnable::run);

        if (!ltPos.equals(new Vertice(Mouse.getX(), Mouse.getY())) && isMouseOver) onMouseMove.ifPresent(Runnable::run);

        if (this.rectBounding(ltPos.getX(), ltPos.getY(), y, x, x + width, y + height) && !isMouseOver)
            onMouseOut.ifPresent(Runnable::run);

        while (Keyboard.next()) {
            final int eventKey = Keyboard.getEventKey();
            if (focused.get() && eventKey != YakGLUtils.CHAR_NONE)
                lastKeyDown.set(eventKey);
        }

        final int key = lastKeyDown.get();
        if (Keyboard.isKeyDown(key)) {
            onKeyDown.ifPresent(consumer -> consumer.accept(key));
            wasLastTickKeyDown.set(true);
        } else if (wasLastTickKeyDown.get()) {
            onKeyUp.ifPresent(consumer -> consumer.accept(key));
            wasLastTickKeyDown.set(false);
        }


        if (isMouseOver) wasLastTickMouseDown.set(lmbDown);
        while (Mouse.next()) {
            final int button = Mouse.getEventButton();
            if (button == YakGLUtils.MOUSE_LEFT_BUTTON && Mouse.getEventButtonState()) {
                if (System.currentTimeMillis() - lastClick.get() <= YakGLUtils.MAX_DOUBLE_CLICK_TIME && isMouseOver) {
                    focused.set(true);
                    onDbClick.ifPresent(Runnable::run);
                    lastClick.set(0L);
                } else {
                    if (!isMouseOver) focused.set(false);
                    else {
                        lastClick.set(System.currentTimeMillis());
                        focused.set(true);
                        onClick.ifPresent(Runnable::run);
                    }
                }
            }
        }

        lastTickMousePos.set(new Vertice(Mouse.getX(), Mouse.getY()));

        //  Rendering
        final VerticeAggregation vertices = new VerticeAggregation()
                .add(x, y)
                .add(x + width, y)
                .add(x + width, y + height)
                .add(x, y + height);

        final ColorFunction colors = backgroundColor.or(() -> Optional.of(new VacantColorFunction())).get();
        final YakTexture texture = backgroundImage.map(this::createTex).or(() -> Optional.of(new VacantTexture())).get();


        final GLRenderingData data = GLRenderingData.create(texture)
                .addVertices(vertices)
                .addColors(colors.toAggregation(vertices))
                .addTexs(texture.generateTexs(vertices))
                .build();


        return this.combineContexts(new VerticeRenderingContext.VerticeContextBuilder(GL11.GL_QUADS, data).build(), this.renderChildren(properties));
    }

    private boolean rectBounding(double x, double y, double top, double left, double bottom, double right) {
        return (top < y && bottom > y) && (left < x && right > x);
    }

    @NotNull
    private YakTexture createTex(@NotNull InputStream in) {
        try {
            return YakTextureFactory.createTex(in);
        } catch (IOException e) {
            return new VacantTexture();
        }
    }
}
