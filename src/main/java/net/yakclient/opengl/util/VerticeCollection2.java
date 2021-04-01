package net.yakclient.opengl.util;

import net.yakclient.opengl.IllegalVerticeOperationException;

public class VerticeCollection2 extends VerticeCollection {

    public VerticeCollection2() {
        super(2);
    }

//    @Override
//    public VerticeCollection2 addVertice(VerticeBuilder builder) throws IllegalVerticeOperationException {
//        return (VerticeCollection2) super.addVertice(builder);
//    }

    @Override
    public VerticeCollection2 addColor3f(float r, float g, float b) {
        return (VerticeCollection2) super.addColor3f(r, g, b);
    }

    @Override
    public VerticeCollection2 addColor4f(float r, float g, float b, float alpha) {
        return (VerticeCollection2) super.addColor4f(r, g, b, alpha);
    }



    public VerticeCollection2 addVertice(double x, double y) {
        this.vertices.add(x);
        this.vertices.add(y);
        return this;
    }

    @Override
    public VerticeCollection2 addNormal(double x, double y, double z) {
        return (VerticeCollection2) super.addNormal(x, y, z);
    }

    @Override
    public VerticeCollection2 combine(VerticeCollection collection) {
        return (VerticeCollection2) super.combine(collection);
    }

    @Override
    public String toString() {
        return "VerticeCollection2{" +
                "vertices=" + vertices +
                ", normals=" + normals +
                ", colors=" + colors +
                '}';
    }


}
