package net.yakclient.opengl.api.gui;

import net.yakclient.opengl.api.render.GLRenderingContext;
import org.jetbrains.annotations.Contract;

public interface GLPropsRenderable {
    @Contract(pure = true)
    GLRenderingContext[] glRender(GUIProperties properties);
}
