package net.yakclient.opengl.api.render;

public class GLRenderingContext {
    private final int drawType;
    private final GLRenderingData data;
    private final RenderMethod<?> suggestedRenderer;

    public GLRenderingContext(int drawType, GLRenderingData data, RenderMethod<?> suggestedRenderer) {
        this.drawType = drawType;
        this.data = data;
        this.suggestedRenderer = suggestedRenderer;
    }

    public GLRenderingContext(ContextBuilder builder) {
        this.drawType = builder.drawType;
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

    public static class ContextBuilder {
        private final int drawType;
        private final GLRenderingData data;
        private RenderMethod<?> suggestedRenderer;

        public ContextBuilder(int drawType, GLRenderingData data) {
            this.drawType = drawType;
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
