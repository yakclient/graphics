package net.yakclient.opengl.util;

import net.yakclient.opengl.util.ColorAggregation.ColorNode;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Collection;

public class ColorAggregation implements Aggregation<ColorNode> {
    public static final float DEFAULT_ALPHA = 1.0f;
    public static final int COLOR_INDEX_SIZE = 4;

    private final Collection<Float> colors;

    public ColorAggregation() {
        this.colors = YakGLUtils.defaultCollection();
    }

    @Override
    public @NotNull AggregationIterator<ColorNode> iterator() {
        final var nodes = new ColorNode[this.colors.size() / COLOR_INDEX_SIZE];
        int nodesIndex = 0;
        int index = 0;

        final var coords = new float[]{0, 0, 0, DEFAULT_ALPHA};
        for (Float normal : this.colors) {
            coords[index++] = normal;
            if (index == COLOR_INDEX_SIZE) {
                index = 0;
                nodes[nodesIndex++] = new ColorNode(coords[0], coords[1], coords[2], coords[3]);
            }
        }

        return new AggregationIterator<>(nodes);
    }

    @Override
    public FloatBuffer createBuf() {
        return BufferUtils.createFloatBuffer(colors.size());
    }

    @Override
    public <B extends Buffer> B asBuf(B buf) {
        if (!(buf instanceof FloatBuffer))
            throw new IllegalVerticeOperationException("Buffer must be a DoubleBuffer!");
        ((FloatBuffer) buf).put(YakGLUtils.toPrimitiveArray(this.colors.toArray(new Float[0])));
        return buf;
    }

    @Override
    public boolean add(ColorNode node) {
        this.colors.add(node.getR());
        this.colors.add(node.getG());
        this.colors.add(node.getB());
        this.colors.add(node.getAlpha());
        return true;
    }

    public ColorAggregation add(float r, float g, float b, float alpha) {
        this.colors.add(r);
        this.colors.add(g);
        this.colors.add(b);
        this.colors.add(alpha);
        return this;
    }

    public ColorAggregation add(float r, float g, float b) {
        this.colors.add(r);
        this.colors.add(g);
        this.colors.add(b);
        this.colors.add(DEFAULT_ALPHA);
        return this;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <A extends Aggregation<ColorNode>> A combine(A aggregate) {
        if (!(aggregate instanceof ColorAggregation))
            throw new IllegalVerticeOperationException("Aggregates must be of the same type!");
        this.colors.addAll(((ColorAggregation) aggregate).colors);
        return (A) this;
    }

    @Override
    public int size() {
        return this.colors.size() / COLOR_INDEX_SIZE;
    }

    public static class ColorNode implements AggregateNode{
        private final float r;
        private final float g;
        private final float b;
        private final float alpha;

        public ColorNode(float r, float g, float b, float alpha) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.alpha = alpha;
        }

        public float getR() {
            return r;
        }

        public float getG() {
            return g;
        }

        public float getB() {
            return b;
        }

        public float getAlpha() {
            return alpha;
        }
    }
}
