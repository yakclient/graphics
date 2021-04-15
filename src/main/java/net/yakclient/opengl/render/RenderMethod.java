package net.yakclient.opengl.render;

public interface RenderMethod<T extends GLRenderer> {
    RenderMethod<VBORenderer> VBO = VBORenderer::new;

    RenderMethod<VAORenderer> VAO = VAORenderer::new;

    T type(GLRenderingContext context);
}
