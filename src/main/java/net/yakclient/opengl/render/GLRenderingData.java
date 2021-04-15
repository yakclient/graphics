package net.yakclient.opengl.render;

import net.yakclient.opengl.util.*;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public class GLRenderingData {
    private final VerticeAggregation vertices;
    private final ColorAggregation colors;
    private final NormalAggregation normals;
    private final TexAggregation texs;

    private final Texture texture;

    private final int verticeCount;

    public GLRenderingData(VerticeAggregation vertices, ColorAggregation colors, NormalAggregation normals, TexAggregation texs, Texture texture, int verticeCount) {
        this.vertices = vertices;
        this.colors = colors;
        this.normals = normals;
        this.texs = texs;
        this.texture = texture;
        this.verticeCount = verticeCount;
    }

    public GLRenderingData(RenderingDataBuilder builder) {
        this(builder.vertices, builder.colors, builder.normals, builder.texs, builder.texture, builder.verticeCount);
    }

    public DoubleBuffer getVertices() {
        return this.vertices.asBuf(this.vertices.createBuf());
    }

    public DoubleBuffer getNormals() {
        return this.normals.asBuf(this.normals.createBuf());
    }

    public FloatBuffer getColors() {
        return this.colors.asBuf(this.colors.createBuf());
    }

    public FloatBuffer getTexs() {
        return this.texs.asBuf(this.texs.createBuf());
    }

    public Texture getTexture() {
        return texture;
    }

    public int getVerticeCount() {
        return verticeCount;
    }

    public static RenderingDataBuilder create() {
        return new RenderingDataBuilder();
    }

    public static RenderingDataBuilder create(Texture texture) {
        return new RenderingDataBuilder(texture);
    }

    public static class RenderingDataBuilder {
        private final VerticeAggregation vertices;
        private final ColorAggregation colors;
        private final NormalAggregation normals;
        private final TexAggregation texs;

        private final Texture texture;

        private int verticeCount = 0;


        public RenderingDataBuilder() {
            this.vertices = new VerticeAggregation();
            this.colors = new ColorAggregation();
            this.normals = new NormalAggregation();
            this.texs = new TexAggregation();
            this.texture = new VacantTexture();
        }

        public RenderingDataBuilder(Texture texture) {
            this.vertices = new VerticeAggregation();
            this.colors = new ColorAggregation();
            this.normals = new NormalAggregation();
            this.texs = new TexAggregation();
            this.texture = texture;
        }

        public RenderingDataBuilder addColor3f(float r, float g, float b) {
            this.colors.add(r, g, b);
            return this;
        }

        public RenderingDataBuilder addColor4f(float r, float g, float b, float alpha) {
            this.colors.add(r, b, b, alpha);
            return this;
        }

        public RenderingDataBuilder addColors(ColorAggregation aggregation) {
            this.colors.combine(aggregation);
            return this;
        }

        public RenderingDataBuilder addVertice2d(double x, double y) {
            this.verticeCount++;
            this.vertices.add(x, y);
            return this;
        }

        public RenderingDataBuilder addVertice3d(double x, double y, double z) {
            this.verticeCount++;
            this.vertices.add(x, y, z);
            return this;
        }

        public RenderingDataBuilder addVertices(VerticeAggregation aggregation) {
            this.verticeCount += aggregation.size();
            this.vertices.combine(aggregation);
            return this;
        }

        public RenderingDataBuilder addNormal(double x, double y, double z) {
            this.normals.add(x, y, z);
            return this;
        }

        public RenderingDataBuilder addNormals(NormalAggregation aggregation) {
            this.normals.combine(aggregation);
            return this;
        }

        public RenderingDataBuilder addTexVertice(float t, float s) {
            this.texs.add(t, s);
            return this;
        }

        public RenderingDataBuilder addTexs(TexAggregation aggregation) {
            this.texs.combine(aggregation);
            return this;
        }

        public GLRenderingData build() {
            return new GLRenderingData(this);
        }
    }
}
