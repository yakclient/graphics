package net.yakclient.graphics.util;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class YakFontFactory {
    public static final int DEFAULT_TEXT_SIZE = 24;
    public static final String FONT_DEFAULT = "Default";

    private static final Map<YakFont.FontMetaData, YakFont> fonts;

    static {
        fonts = new ConcurrentHashMap<>();
    }

    private YakFontFactory() {
    }

    public abstract YakFont buildFont(boolean isAliased);

    public abstract YakFontFactory addStyle(int style);

    public YakFont buildFont() {
        return this.buildFont(true);
    }


    public static YakFontFactory create(InputStream fontIn) {
        return new YakCustomFontFactory(fontIn);
    }

    public static YakFontFactory create(String name, int fontSize) {
        return new YakNamedFontFactory(name, fontSize);
    }

    public static YakFontFactory create() {
        return new YakNamedFontFactory(FONT_DEFAULT, DEFAULT_TEXT_SIZE);
    }

    public static final class YakCustomFontFactory extends YakFontFactory {
        private final InputStream fontIn;

        private YakCustomFontFactory(InputStream fontIn) {
            this.fontIn = fontIn;
        }

        @Override
        public YakFont buildFont(boolean isAliased) {
            final Font font = this.createFont();
            final YakFont.FontMetaData meta = new YakFont.FontMetaData(font.getName(), font.getStyle(), font.getSize(), isAliased);
            if (fonts.containsKey(meta)) return fonts.get(meta);

            final SlickFontWrapper yakFont = new SlickFontWrapper(font, new TrueTypeFont(font, isAliased), meta);
            fonts.put(meta, yakFont);
            return yakFont;
        }

        private Font createFont() {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, this.fontIn);
            } catch (FontFormatException | IOException e) {
                return new Font(null, Font.PLAIN, 12);
            }
        }

        @Override
        public YakFontFactory addStyle(int style) {
            //Nothing to do
            return this;
        }
    }


    public static final class YakNamedFontFactory extends YakFontFactory {
        private final String name;
        private final int size;
        private int style;

        private YakNamedFontFactory(String name, int size) {
            this.name = name;
            this.size = size;
            this.style = Font.PLAIN;
        }

        @Override
        public YakFont buildFont(boolean isAliased) {
            final YakFont.FontMetaData metaData = new YakFont.FontMetaData(this.name, this.style, this.size, isAliased);
            if (fonts.containsKey(metaData)) return fonts.get(metaData);

            final Font font = new Font(this.name, this.style, this.size);

            final SlickFontWrapper yakFont = new SlickFontWrapper(font, new TrueTypeFont(font, isAliased), metaData);
            fonts.put(metaData, yakFont);
            return yakFont;
        }

        @Override
        public YakFontFactory addStyle(int style) {
            this.style = this.style | style;
            return this;
        }
    }


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    private final boolean isAliased;
//    private FontBuilder builder;
//
//    private YakFontFactory(boolean isAliased, FontBuilder builder) {
//        this.isAliased = isAliased;
//        this.builder = builder;
//    }
//
//    public YakFontFactory addStyle(int style) {
//        this.builder.addStyle(style);
//        return this;
//    }
//
//    public Font createFont() {
//
//    }
//
//    public static YakFontFactory create(InputStream fontIn) {
//        return new YakFontFactory(true, new CustomFontBuilder(fontIn));
//    }
//
//
//    public static YakFontFactory create(String name, int fontSize) {
//        return new YakFontFactory(true, new NamedFontBuilder(name, fontSize));
//    }
//
//    private interface FontBuilder {
//        void addStyle(int style);
//
//        Font createFont();
//    }
//
//    private static class CustomFontBuilder implements FontBuilder {
//        private final InputStream fontIn;
//
//        private CustomFontBuilder(InputStream fontIn) {
//            this.fontIn = fontIn;
//        }
//
//        @Override
//        public void addStyle(int style) { /*  Nothing to do */ }
//
//        @Override
//        public Font createFont() {
//            try {
//                return Font.createFont(Font.TRUETYPE_FONT, this.fontIn);
//            } catch (FontFormatException | IOException e) {
//                return new NamedFontBuilder(null, YakFontFactory.DEFAULT_FONT_SIZE).createFont();
//            }
//        }
//    }
//
//    private static class NamedFontBuilder implements FontBuilder {
//        private final String name;
//        private final int size;
//        private int style;
//
//        private NamedFontBuilder(String name, int size) {
//            this.size = size;
//            this.name = name;
//        }
//
//        public void addStyle(int style) {
//            this.style = this.style | style;
//        }
//
//        @Override
//        public Font createFont() {
//            return new Font(name, style, size);
//        }
//    }
}
