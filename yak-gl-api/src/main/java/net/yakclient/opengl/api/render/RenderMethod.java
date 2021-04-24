package net.yakclient.opengl.api.render;

@FunctionalInterface
public interface RenderMethod<T extends GLRenderer> {
    RenderMethod<VBORenderer> VBO = VBORenderer::new;

    RenderMethod<VAORenderer> VAO = VAORenderer::new;

    T type(GLRenderingContext context);
}
