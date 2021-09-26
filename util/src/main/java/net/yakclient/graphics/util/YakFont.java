package net.yakclient.graphics.util;

import java.util.Objects;

public interface YakFont {
    java.awt.Font getType();

    String getName();

    FontMetaData getMeta();

    double getWidth(String value);

    double getLineHeight();

    double getHeight(String value);

    void drawString(String value, RGBColor color, double x, double y);

    void drawString(String value, double x, double y);

    class FontMetaData {
        private final String name;

        private final int style;
        private final int size;
        private final boolean isAliased;

        public FontMetaData(String name, int style, int size, boolean isAliased) {
            this.name = name;
            this.style = style;
            this.size = size;
            this.isAliased = isAliased;
        }

        public String getName() {
            return name;
        }

        public int getStyle() {
            return style;
        }

        public int getSize() {
            return size;
        }

        public boolean isAliased() {
            return isAliased;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FontMetaData that = (FontMetaData) o;
            return style == that.style && size == that.size && isAliased == that.isAliased && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, style, size, isAliased);
        }
    }
}
