package net.yakclient.opengl.gui;

import net.yakclient.opengl.render.GLRenderingContext;
import org.jetbrains.annotations.Contract;

public interface GLPropsRenderable {
    @Contract(pure = true)
    GLRenderingContext[] glRender(GUIProperties properties);
}
