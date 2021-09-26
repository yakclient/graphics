package net.yakclient.graphics.util;

public interface YakTexture {
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
