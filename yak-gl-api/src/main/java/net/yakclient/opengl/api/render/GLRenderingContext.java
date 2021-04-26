package net.yakclient.opengl.api.render;

public interface GLRenderingContext {
    Renderer useRenderer(RenderingType type);

    Renderer useRenderer();
}
