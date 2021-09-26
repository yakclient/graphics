package net.yakclient.graphics.util;

import java.util.Objects;

public class Vertice implements AggregateNode {
    private final double x;
    private final double y;
    private final double z;
    private final double r;

    public Vertice(double x, double y, double z, double r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = VerticeAggregation.DEFAULT_R;
    }

    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = VerticeAggregation.Z_INDEX_2D;
        this.r = VerticeAggregation.DEFAULT_R;
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

    public double getR() {
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Double.compare(vertice.x, x) == 0 && Double.compare(vertice.y, y) == 0 && Double.compare(vertice.z, z) == 0 && Double.compare(vertice.r, r) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, r);
    }
}
