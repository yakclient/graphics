package net.yakclient.opengl.util;

public class VerticeCollection3 extends VerticeCollection {
    public VerticeCollection3() {
        super(3);
    }

    @Override
    public VerticeCollection3 addColor3f(float r, float g, float b) {
        return (VerticeCollection3) super.addColor3f(r, g, b);
    }

    @Override
    public VerticeCollection3 addColor4f(float r, float g, float b, float alpha) {
        return (VerticeCollection3) super.addColor4f(r, g, b, alpha);
    }

//    @Override
//    public VerticeBuilder addVertice() {
//        return new Vertice3Builder(this);
//    }

    public VerticeCollection3 addVertice(double x, double y, double z) {
        this.vertices.add(x);
        this.vertices.add(y);
        this.vertices.add(z);
        return this;
    }

    @Override
    public VerticeCollection3 addNormal(double x, double y, double z) {
        return (VerticeCollection3) super.addNormal(x, y, z);
    }

    @Override
    public VerticeCollection3 combine(VerticeCollection collection) {
        return (VerticeCollection3) super.combine(collection);
    }

//    public static class Vertice3Builder extends VerticeBuilder {
//        Vertice3Builder(VerticeCollection vc) {
//            super(3, vc);
//        }
//
//        public Vertice3Builder vertice(double x, double y, double z) {
//            this.vertice[0] = x;
//            this.vertice[1] = y;
//            this.vertice[2] = z;
//            return this;
//        }
//
//        public Vertice3Builder vertice(double[] vertice) {
//            this.vertice[0] = vertice[0];
//            this.vertice[1] = vertice[1];
//            this.vertice[2] = vertice[2];
//            return this;
//        }
//
//        @Override
//        public Vertice3Builder addColor3f(float r, float g, float b) {
//            return (Vertice3Builder) super.addColor3f(r, g, b);
//        }
//
//        @Override
//        public Vertice3Builder addColor4f(float r, float g, float b, float alpha) {
//            return (Vertice3Builder) super.addColor4f(r, g, b, alpha);
//        }
//
//        @Override
//        public VerticeCollection2 build() {
//            return (VerticeCollection2) super.build();
//        }
//    }
}
