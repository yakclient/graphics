package net.yakclient.graphics.util;

import org.newdawn.slick.TrueTypeFont;

public class SlickFontWrapper implements YakFont {
    private final java.awt.Font type;
    private final TrueTypeFont trueType;
    private final FontMetaData metaData;

    public SlickFontWrapper(java.awt.Font type, TrueTypeFont trueType, FontMetaData metaData) {
        this.type = type;
        this.trueType = trueType;
        this.metaData = metaData;
    }

    @Override
    public java.awt.Font getType() {
        return this.type;
    }



    @Override
    public String getName() {
        return this.type.getName();
    }

    @Override
    public FontMetaData getMeta() {
        return this.metaData;
    }

    @Override
    public double getWidth(String value) {
        return this.trueType.getWidth(value);
    }

    @Override
    public double getLineHeight() {
        return this.trueType.getLineHeight();
    }

    @Override
    public double getHeight(String value) {
        return this.trueType.getHeight(value);
    }

    @Override
    public void drawString(String value, RGBColor color, double x, double y) {
        this.trueType.drawString((float) x, (float) y, value, SlickTranslator.toColor(color));
    }

    @Override
    public void drawString(String value, double x, double y) {
        this.drawString(value, ColorCodes.WHITE, x, y);
    }
}
