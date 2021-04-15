package net.yakclient.opengl.util;

import net.yakclient.opengl.IllegalVerticeOperationException;
import net.yakclient.opengl.util.VerticeAggregation.Vertice;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.util.Collection;

public class VerticeAggregation implements Aggregation<Vertice> {
    public static final int VERTICE_SIZE = 3;
    public static final double Z_INDEX_2D = 0;

    protected final Collection<Double> vertices;

    public VerticeAggregation() {
        this.vertices = YakGLUtils.defaultCollection();
    }

    public VerticeAggregation add(double x, double y) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(Z_INDEX_2D);
        return this;
    }

    public VerticeAggregation add(double x, double y, double z) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(z);
        return this;
    }

    @Override
    public @NotNull AggregationIterator<Vertice> iterator() {
        final Vertice[] nodes = new Vertice[this.vertices.size() / VERTICE_SIZE];
        int nodesIndex = 0;
        int index = 0;

        final double[] coords = new double[]{0, 0, Z_INDEX_2D};
        for (Double normal : this.vertices) {
            coords[index++] = normal;
            if (index == VERTICE_SIZE) {
                index = 0;
                nodes[nodesIndex++] = new Vertice(coords[0], coords[1], coords[2]);
            }
        }

        return new AggregationIterator<>(nodes);
    }

    @Override
    public DoubleBuffer createBuf() {
        return BufferUtils.createDoubleBuffer(vertices.size());
    }

    @Override
    public <B extends Buffer> B asBuf(B buf) {
        if (!(buf instanceof DoubleBuffer))
            throw new IllegalVerticeOperationException("Buffer must be a DoubleBuffer!");
        ((DoubleBuffer) buf).put(YakGLUtils.toPrimitiveArray(this.vertices.toArray(new Double[0])));
        return buf;
    }

    @Override
    public boolean add(Vertice node) {
        this.vertices.add(node.getX());
        this.vertices.add(node.getY());
        this.vertices.add(node.getZ());
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Aggregation<Vertice>> A combine(A aggregate) {
        if (!(aggregate instanceof VerticeAggregation))
            throw new IllegalVerticeOperationException("Aggregates must be of the same type!");
        this.vertices.addAll(((VerticeAggregation) aggregate).vertices);
        return (A) this;
    }

    @Override
    public int size() {
        return this.vertices.size() / VERTICE_SIZE;
    }

    public static class Vertice implements AggregateNode {
        private final double x;
        private final double y;
        private final double z;

        private Vertice(double x, double y, double z) {
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
