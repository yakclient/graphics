package net.yakclient.opengl.util;

import net.yakclient.opengl.IllegalVerticeOperationException;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Collection;

public class TexAggregation implements Aggregation<TexAggregation.TexNode> {
    public static final int VERTICE_SIZE = 2;
    public static final float DEFAULT_R = 1;

    private final Collection<Float> texs;

    public TexAggregation() {
        this.texs = YakGLUtils.defaultCollection();
    }

    @Override
    public @NotNull AggregationIterator<TexNode> iterator() {
        final TexNode[] nodes = new TexNode[this.texs.size() / VERTICE_SIZE];
        int nodesIndex = 0;
        int index = 0;

        final float[] coords = new float[]{0, 0, DEFAULT_R};
        for (Float normal : this.texs) {
            coords[index++] = normal;
            if (index == VERTICE_SIZE) {
                index = 0;
                nodes[nodesIndex++] = new TexNode(coords[0], coords[1]);
            }
        }

        return new AggregationIterator<>(nodes);
    }

    @Override
    public FloatBuffer createBuf() {
        return BufferUtils.createFloatBuffer(this.texs.size());
    }

    @Override
    public <B extends Buffer> B asBuf(B buf) {
        if (!(buf instanceof FloatBuffer))
            throw new IllegalVerticeOperationException("Buffer must be a FloatBuffer!");
        ((FloatBuffer) buf).put(YakGLUtils.toPrimitiveArray(this.texs.toArray(new Float[0])));
        return buf;
    }

    @Override
    public boolean add(TexNode node) {
        this.texs.add(node.getS());
        this.texs.add(node.getT());
        return true;
    }

    public TexAggregation add(float s, float t) {
        this.texs.add(s);
        this.texs.add(t);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Aggregation<TexNode>> A combine(A aggregate) {
        if (!(aggregate instanceof TexAggregation))
            throw new IllegalVerticeOperationException("Aggregates must be of the same type!");
        this.texs.addAll(((TexAggregation) aggregate).texs);
        return (A) this;
    }

    @Override
    public int size() {
        return this.texs.size() / VERTICE_SIZE;
    }

    public static class TexNode implements AggregateNode {
        private final float s;
        private final float t;

        public TexNode(float s, float t) {
            this.s = s;
            this.t = t;
        }

        public float getS() {
            return s;
        }

        public float getT() {
            return t;
        }

    }
}
