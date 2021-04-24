package net.yakclient.opengl.components;


import net.yakclient.opengl.api.gui.BuriedGUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.api.render.GLRenderingData;
import net.yakclient.opengl.util.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;


public class Divider extends BuriedGUIComponent {
    @Override
    public GLRenderingContext[] glRender(GUIProperties properties) {
        try {
            final PropertyManager manager = this.manageProps(properties);

            //  Styling
            final double width = manager.requireProp("width");
            final double height = manager.requireProp("height");
            final double x = manager.requireProp("x");
            final double y = manager.requireProp("y");
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


            final boolean lmbDown = Mouse.isButtonDown(YakGLUtils.MOUSE_LEFT_BUTTON);
            if (wasLastTickMouseDown.get() && !lmbDown) onMouseUp.ifPresent(Runnable::run);

            final boolean isMouseOver = this.rectBounding(Mouse.getX(), Mouse.getY(), y, x, x + width, y + height);
            if (isMouseOver) onMouseOver.ifPresent(Runnable::run);
            if (isMouseOver && lmbDown) onMouseDown.ifPresent(Runnable::run);

            final Vertice ltPos = lastTickMousePos.get();
            if (ltPos.equals(new Vertice(Mouse.getX(), Mouse.getY())) && isMouseOver) onHover.ifPresent(Runnable::run);

            if (!ltPos.equals(new Vertice(Mouse.getX(), Mouse.getY())) && isMouseOver)
                onMouseMove.ifPresent(Runnable::run);

            if (this.rectBounding(ltPos.getX(), ltPos.getY(), y, x, x + width, y + height) && !isMouseOver)
                onMouseOut.ifPresent(Runnable::run);

            lastKeyDown.set(YakGLUtils.CHAR_NONE);
            while (Keyboard.next()) if (focused.get()) lastKeyDown.set(Keyboard.getEventKey());

            //TODO the keys dont down and up dont currently work
            final int key = lastKeyDown.get();
            if (Keyboard.isKeyDown(key))
                onKeyDown.ifPresent(consumer -> consumer.accept(key));
            else {
                onKeyUp.ifPresent(consumer -> consumer.accept(key));
                lastKeyDown.set(YakGLUtils.CHAR_NONE);
            }


            wasLastTickMouseDown.set(lmbDown);
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

//                    if (isMouseOver) wasLastTickMouseDown.set(true);
                }
            }

            lastTickMousePos.set(new Vertice(Mouse.getX(), Mouse.getY()));


            //Rendering
            final VerticeAggregation vertices = new VerticeAggregation()
                    .add(x, y)
                    .add(x + width, y)
                    .add(x + width, y + height)
                    .add(x, y + height);

            final ColorFunction colors = backgroundColor.or(() -> Optional.of(new VacantColorFunction())).get();
            final Texture texture = backgroundImage.map(this::createTex).or(() -> Optional.of(new VacantTexture())).get();


            final GLRenderingData data = GLRenderingData.create(texture)
                    .addVertices(vertices)
                    .addColors(colors.toAggregation(vertices))
                    .addTexs(texture.generateTexs(vertices))
                    .build();


            return this.combineContexts(new GLRenderingContext.ContextBuilder(GL11.GL_QUADS, data).build(), this.renderChildren(properties));

        } catch (Exception e) {
            System.out.println("Failed to create texture wood");
        }
        return new GLRenderingContext[0];
    }

    private boolean rectBounding(double x, double y, double top, double left, double bottom, double right) {
        return (top < y && bottom > y) && (left < x && right > x);
    }

    @NotNull
    private Texture createTex(@NotNull InputStream in) {
        try {
            return TextureFactory.createTex(in);
        } catch (IOException e) {
            return new VacantTexture();
        }
    }
}
