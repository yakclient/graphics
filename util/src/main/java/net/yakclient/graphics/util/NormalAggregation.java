package net.yakclient.graphics.util;

import net.yakclient.graphics.util.NormalAggregation.NormalNode;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.util.Collection;

public class NormalAggregation implements Aggregation<NormalNode> {
    public static final int NORMAL_INDEX_SIZE = 3;

    protected final Collection<Double> normals;

    public NormalAggregation() {
        this.normals = YakGLUtils.defaultCollection();
    }

    @Override
    public @NotNull AggregationIterator<NormalNode> iterator() {
        final var nodes = new NormalNode[this.normals.size() / NORMAL_INDEX_SIZE];
        int nodesIndex = 0;
        int index = 0;

        final var coords = new double[]{0, 0, 0};
        for (Double normal : this.normals) {
            coords[index++] = normal;
            if (index == NORMAL_INDEX_SIZE) {
                index = 0;
                nodes[nodesIndex++] = new NormalNode(coords[0], coords[1], coords[2]);
            }
        }

        return new AggregationIterator<>(nodes);
    }

    @Override
    public DoubleBuffer createBuf() {
        return BufferUtils.createDoubleBuffer(normals.size());
    }

    @Override
    public <B extends Buffer> B asBuf(B buf) {
        if (!(buf instanceof DoubleBuffer))
            throw new IllegalVerticeOperationException("Buffer must be a DoubleBuffer!");
        ((DoubleBuffer) buf).put(YakGLUtils.toPrimitiveArray(this.normals.toArray(new Double[0])));
        return buf;
    }

    @Override
    public boolean add(NormalNode node) {
        normals.add(node.getX());
        normals.add(node.getY());
        normals.add(node.getZ());
        return true;
    }

    public NormalAggregation add(double x, double y, double z) {
        this.normals.add(x);
        this.normals.add(y);
        this.normals.add(z);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Aggregation<NormalNode>> A combine(A aggregate) {
        if (!(aggregate instanceof NormalAggregation)) throw new IllegalVerticeOperationException("Aggregates must be of the same type!");
        this.normals.addAll(((NormalAggregation) aggregate).normals);
        return (A) this;
    }

    @Override
    public int size() {
        return this.normals.size() / NORMAL_INDEX_SIZE;
    }

    public static class NormalNode implements AggregateNode{
        private final double x;
        private final double y;
        private final double z;

        private NormalNode(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
