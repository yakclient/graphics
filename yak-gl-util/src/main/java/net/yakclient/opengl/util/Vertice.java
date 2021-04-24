package net.yakclient.opengl.util;

import java.util.Objects;

public class Vertice implements AggregateNode {
    private final double x;
    private final double y;
    private final double z;
    private final double a;

    public Vertice(double x, double y, double z, double a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = VerticeAggregation.DEFAULT_ALPHA;
    }

    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = VerticeAggregation.Z_INDEX_2D;
        this.a = VerticeAggregation.DEFAULT_ALPHA;
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

    public double getA() {
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Double.compare(vertice.x, x) == 0 && Double.compare(vertice.y, y) == 0 && Double.compare(vertice.z, z) == 0 && Double.compare(vertice.a, a) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, a);
    }
}
