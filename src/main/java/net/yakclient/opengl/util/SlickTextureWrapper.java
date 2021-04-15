package net.yakclient.opengl.util;

import org.lwjgl.opengl.GL11;

public class SlickTextureWrapper implements Texture {
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
}
