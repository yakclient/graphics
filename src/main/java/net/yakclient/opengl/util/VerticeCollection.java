package net.yakclient.opengl.util;

import net.yakclient.opengl.IllegalVerticeOperationException;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Collection;

public abstract class VerticeCollection {
    public static final float DEFAULT_ALPHA = 1.0f;
    public static final int COLOR_SIZE = 4; /* R, G, B, ALPHA */

    protected final Collection<Double> vertices;
    protected final Collection<Double> normals;
    protected final Collection<Float> colors;

    private final int verticeIndexSize;

    public VerticeCollection(int verticeIndexSize) {
        this.verticeIndexSize = verticeIndexSize;
        this.vertices = YakGLUtils.defaultCollection();
        this.normals = YakGLUtils.defaultCollection();
        this.colors = YakGLUtils.defaultCollection();
    }

    public DoubleBuffer verticesAsBuffer() {
        if (this.vertices.size() % this.verticeIndexSize != 0)
            throw new IllegalVerticeOperationException("Illegal state! Your vertices are malformed!");

        return BufferUtils.createDoubleBuffer(this.vertices.size())
                .put(YakGLUtils.toPrimitiveArray(this.vertices.toArray(new Double[0])));
    }

    public DoubleBuffer normalsAsBuffer() {
        if (this.normals.size() % this.verticeIndexSize != 0)
            throw new IllegalVerticeOperationException("Illegal state! Your vertices are malformed!");

        return BufferUtils.createDoubleBuffer(this.normals.size())
                .put(YakGLUtils.toPrimitiveArray(this.normals.toArray(new Double[0])));
    }

    public FloatBuffer colorsAsBuffer() {
        if (this.colors.size() % COLOR_SIZE != 0)
            throw new IllegalVerticeOperationException("Illegal state! Your vertices are malformed!");

        return BufferUtils.createFloatBuffer(this.colors.size())
                .put(YakGLUtils.toPrimitiveArray(this.colors.toArray(new Float[0])));
    }

    public int verticesSize() {
        return this.vertices.size();
    }

    public int normalsSize(){
        return this.normals.size();
    }

    public int colorsSize() {
        return this.colors.size();
    }

    public VerticeCollection combine(VerticeCollection collection) {
        if (collection.verticeIndexSize != this.verticeIndexSize) throw new IllegalVerticeOperationException("Combined type must have to same verticeIndex type!");

        this.vertices.addAll(collection.vertices);
        this.colors.addAll(collection.colors);
        this.normals.addAll(collection.normals);
        return this;
    }

//    public abstract VerticeBuilder addVertice();

//    public VerticeCollection addVertice(VerticeBuilder builder) throws IllegalVerticeOperationException {
//        if (this.verticeIndexSize != builder.size)
//            throw new IllegalVerticeOperationException("Attempted to add a vertice with a non-matching index-size!");
//
//        this.addAll(this.vertices, builder.vertice);
//        this.addAll(this.colors, builder.color);
//
//        return this;
//    }

    public VerticeCollection addNormal(double x, double y, double z) {
        this.normals.add(x);
        this.normals.add(y);
        this.normals.add(z);
        return this;
    }

    public VerticeCollection addColor3f(float r, float g, float b) {
        this.colors.add(r);
        this.colors.add(g);
        this.colors.add(b);
        this.colors.add(DEFAULT_ALPHA);
        return this;
    }

    public VerticeCollection addColor4f(float r, float g, float b, float alpha) {
        this.colors.add(r);
        this.colors.add(g);
        this.colors.add(b);
        this.colors.add(alpha);
        return this;
    }

//    private void addAll(Collection<Double> c, double... elements) {
//        for (double element : elements) c.add(element);
//    }
//
//    private void addAll(Collection<Float> c, float... elements) {
//        for (float element : elements) c.add(element);
//    }

}
