package net.yakclient.opengl.api.render;

import org.lwjgl.opengl.GL11;

/**
 * Rendering context provides "context" to render. What
 * this means is that it provides needed GL settings, data
 * and a suggested type of rendering. It represents a
 * singular "model" that can be rendered at any point without
 * interfering with any other rendering done synchronously.
 *
 * @author Durgan McBroom
 *
 * @see RenderMethod
 * @see GLRenderingData
 */
public class GLRenderingContext {
    private final int drawType;
    private final int textureType;
    private final GLRenderingData data;
    private final RenderMethod<?> suggestedRenderer;

    public GLRenderingContext(int drawType, int textureType, GLRenderingData data, RenderMethod<?> suggestedRenderer) {
        this.drawType = drawType;
        this.textureType = textureType;
        this.data = data;
        this.suggestedRenderer = suggestedRenderer;
    }

    public GLRenderingContext(ContextBuilder builder) {
        this.drawType = builder.drawType;
        this.textureType = builder.textureType;
        this.data = builder.data;
        this.suggestedRenderer = builder.suggestedRenderer;
    }

    public int getDrawType() {
        return drawType;
    }

    public GLRenderingData getData() {
        return data;
    }

    public GLRenderer useSuggestedRenderer() {
        return this.suggestedRenderer.type(this);
    }

    public int getTextureType() {
        return textureType;
    }

    public static class ContextBuilder {
        public static final int DEFAULT_TEX_TYPE = GL11.GL_TEXTURE_2D;

        private final int drawType;
        public final int textureType;

        private final GLRenderingData data;
        private RenderMethod<?> suggestedRenderer;

        public ContextBuilder(int drawType, int textureType, GLRenderingData data) {
            this.drawType = drawType;
            this.textureType = textureType;
            this.data = data;
            this.suggestedRenderer = RenderMethod.VBO;
        }
        public ContextBuilder(int drawType, GLRenderingData data) {
            this.drawType = drawType;
            this.textureType = DEFAULT_TEX_TYPE;
            this.data = data;
            this.suggestedRenderer = RenderMethod.VBO;
        }


        public ContextBuilder suggestRenderer(RenderMethod<?> method) {
            this.suggestedRenderer = method;
            return this;
        }

        public GLRenderingContext build() {
            return new GLRenderingContext(this);
        }
    }
}
