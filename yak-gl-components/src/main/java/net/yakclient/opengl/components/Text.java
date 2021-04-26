package net.yakclient.opengl.components;

import net.yakclient.opengl.api.gui.BuriedGUIComponent;
import net.yakclient.opengl.api.gui.GUIProperties;
import net.yakclient.opengl.api.render.FontRenderingContext;
import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.util.ColorCodes;
import net.yakclient.opengl.util.RGBColor;
import net.yakclient.opengl.util.YakFont;
import net.yakclient.opengl.util.YakFontFactory;

import java.util.Optional;

public class Text extends BuriedGUIComponent {
    public static final int DEFAULT_TEXT_SIZE = 24;
    public static final String FONT_DEFAULT = "Default";

    @Override
    public GLRenderingContext[] glRender(GUIProperties properties) {
        final var value = this.requireProp(properties.<String>get("value"));
        final var x = this.requireProp(properties.<Double>get("x"));
        final var y = this.requireProp(properties.<Double>get("y"));
        final var color = this.requestProp(properties.<RGBColor>get("color")).or(() -> Optional.of(ColorCodes.WHITE)).get();
        final var font = this.requestProp(properties.<YakFont>get("font")).or(() -> Optional.of(YakFontFactory.create(FONT_DEFAULT, DEFAULT_TEXT_SIZE).buildFont())).get();


        final FontRenderingContext fontContext = new FontRenderingContext(font, value, color, x, y);

        return this.combineContexts(fontContext, this.renderChildren(properties));
    }
}
