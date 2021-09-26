package net.yakclient.graphics.util;


import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.util.Collection;

public class VerticeAggregation implements Aggregation<Vertice> {
    public static final int VERTICE_SIZE = 4;
    public static final double Z_INDEX_2D = 0;
    public static final double DEFAULT_R = 1;

    protected final Collection<Double> vertices;

    public VerticeAggregation() {
        this.vertices = YakGLUtils.defaultCollection();
    }

    public VerticeAggregation add(double x, double y) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(Z_INDEX_2D);
        this.vertices.add(DEFAULT_R);
        return this;
    }
    public VerticeAggregation addR(double x, double y, double r) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(r);
        this.vertices.add(DEFAULT_R);
        return this;
    }


    public VerticeAggregation add(double x, double y, double z) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(z);
        this.vertices.add(DEFAULT_R);
        return this;
    }

    public VerticeAggregation addR(double x, double y, double z, double r) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(z);
        this.vertices.add(r);
        return this;
    }


    @Override
    public
    AggregationIterator<Vertice> iterator() {
        final var nodes = new Vertice[this.vertices.size() / VERTICE_SIZE];
        int nodesIndex = 0;
        int index = 0;

        final var coords = new double[]{0, 0, Z_INDEX_2D, DEFAULT_R};
        for (Double normal : this.vertices) {
            coords[index++] = normal;
            if (index == VERTICE_SIZE) {
                index = 0;
                nodes[nodesIndex++] = new Vertice(coords[0], coords[1], coords[2], coords[3]);
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
        this.vertices.add(node.getR());
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
}
