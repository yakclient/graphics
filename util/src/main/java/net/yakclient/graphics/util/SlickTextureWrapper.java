package net.yakclient.graphics.util;

public class SlickTextureWrapper implements YakTexture {
    private final org.newdawn.slick.opengl.Texture tex;

    public SlickTextureWrapper(org.newdawn.slick.opengl.Texture tex) {
        this.tex = tex;
    }

    @Override
    public boolean hasAlpha() {
        return this.tex.hasAlpha();
    }

    @Override
    public void bind() {
        this.tex.bind();
    }

    @Override
    public int getImageHeight() {
        return this.tex.getImageHeight();
    }

    @Override
    public int getImageWidth() {
        return this.tex.getImageWidth();
    }

    @Override
    public float getHeight() {
        return this.tex.getHeight();
    }

    @Override
    public float getWidth() {
        return this.tex.getWidth();
    }

    @Override
    public int getTextureHeight() {
        return this.tex.getTextureHeight();
    }

    @Override
    public int getTextureWidth() {
        return this.tex.getTextureWidth();
    }

    @Override
    public void release() {
        this.tex.release();
    }

    //Currently all generated textures will be a stretch fit however this most likely will be refactored to en-compass more stretching types.

    @Override
    public TexAggregation generateTexs(VerticeAggregation vertices) {
        if (vertices.isEmpty()) return new TexAggregation();
        double maxX = vertices.get(0).getX();
        double maxY = vertices.get(0).getY();
        double maxZ = vertices.get(0).getZ();

        double minX = vertices.get(0).getX();
        double minY = vertices.get(0).getY();
        double minZ = vertices.get(0).getZ();


        for (Vertice vertex : vertices) {
            if (maxX < vertex.getX()) maxX = vertex.getX();
            if (maxY < vertex.getY()) maxY = vertex.getY();
            if (maxZ < vertex.getZ()) maxZ = vertex.getZ();

            if (minX > vertex.getX()) minX = vertex.getX();
            if (minY > vertex.getY()) minY = vertex.getY();
            if (minZ > vertex.getZ()) minZ = vertex.getZ();
        }

        final TexAggregation aggregation = new TexAggregation();

        for (Vertice vertex : vertices) {
            final double xDif = maxX - minX;
            final double yDif = maxY - minY;
            final double zDif = maxZ - minZ;
            aggregation.add(
                    (float) (xDif == 0 ? 0 : (vertex.getX() - minX) / xDif),
                    (float) (yDif == 0 ? 0 : (vertex.getY() - minY) / yDif),
                    (float) (zDif == 0 ? 0 : (vertex.getZ() - minZ) / zDif));
        }

        return aggregation;
    }
}
