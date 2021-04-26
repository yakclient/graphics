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
 * @see RenderingType
 * @see GLRenderingData
 */
public class VerticeRenderingContext implements GLRenderingContext {
    private final int drawType;
    private final int textureType;
    private final GLRenderingData data;

    public VerticeRenderingContext(int drawType, int textureType, GLRenderingData data) {
        this.drawType = drawType;
        this.textureType = textureType;
        this.data = data;
    }

    public VerticeRenderingContext(VerticeContextBuilder builder) {
        this.drawType = builder.drawType;
        this.textureType = builder.textureType;
        this.data = builder.data;
    }

    public int getDrawType() {
        return drawType;
    }

    public int getTextureType() {
        return textureType;
    }

    public GLRenderingData getData() {
        return data;
    }

    @Override
    public Renderer useRenderer(RenderingType type) {
        switch (type) {
            case VBO:
                return new VerticeVBORenderer(this);
            case VAO:
                return new VerticeVAORenderer(this);
            default:
                throw new UnsupportedRenderingTypeException(type, this.getClass().getName());
        }
    }

    @Override
    public Renderer useRenderer() {
        return this.useRenderer(RenderingType.VBO);
    }

    public static class VerticeContextBuilder {
        public static final int DEFAULT_TEX_TYPE = GL11.GL_TEXTURE_2D;

        private final int drawType;
        public final int textureType;

        private final GLRenderingData data;

        public VerticeContextBuilder(int drawType, int textureType, GLRenderingData data) {
            this.drawType = drawType;
            this.textureType = textureType;
            this.data = data;
        }
        public VerticeContextBuilder(int drawType, GLRenderingData data) {
            this.drawType = drawType;
            this.textureType = DEFAULT_TEX_TYPE;
            this.data = data;
        }

        public VerticeRenderingContext build() {
            return new VerticeRenderingContext(this);
        }
    }
}
