package net.yakclient.opengl.api.gui;

import net.yakclient.opengl.api.render.GLRenderingContext;
import net.yakclient.opengl.api.render.VerticeRenderingContext;
import org.jetbrains.annotations.Contract;

public interface ContextPropsRenderable {
    @Contract(pure = true)
    GLRenderingContext[] glRender(GUIProperties properties);
}
