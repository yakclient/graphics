package net.yakclient.opengl.util;

import java.util.Objects;

public class Vertice implements AggregateNode {
    private final double x;
    private final double y;
    private final double z;

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = VerticeAggregation.Z_INDEX_2D;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Double.compare(vertice.x, x) == 0 && Double.compare(vertice.y, y) == 0 && Double.compare(vertice.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
