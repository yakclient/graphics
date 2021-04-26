package net.yakclient.opengl.util;

public class VacantTexture implements YakTexture {
    @Override
    public boolean hasAlpha() {
         return false;
    }

    @Override
    public void bind() {
        //Nothing to do
    }

    @Override
    public int getImageHeight() {
        return 0;
    }

    @Override
    public int getImageWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public int getTextureHeight() {
        return 0;
    }

    @Override
    public int getTextureWidth() {
        return 0;
    }

    @Override
    public void release() {
        //Nothing to do
    }

    @Override
    public TexAggregation generateTexs(VerticeAggregation vertices) {
        return new TexAggregation();
    }

}
