package net.yakclient.opengl.util;

public interface Texture {
    boolean hasAlpha();

    void bind();

    int getImageHeight();

    int getImageWidth();

    float getHeight();

    float getWidth();

    int getTextureHeight();

    int getTextureWidth();

    void release();

    TexAggregation generateTexs(VerticeAggregation vertices);

}
