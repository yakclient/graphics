package net.yakclient.opengl.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class TextureFactory {
    private static final char EXTENSION_SEPARATOR = '.';

    private TextureFactory() {
    }

    public static Texture createTex() {
        return new VacantTexture();
    }

    private static TextureType getType(String path) {
        final TextureType type = TextureType.extensionsType(path.substring(path.indexOf(EXTENSION_SEPARATOR)));
        if (type == null) return TextureType.PNG;
        return type;
    }

    public static Texture createTex(String path) throws IOException {
        return new SlickTextureWrapper(TextureLoader.getTexture(getType(path).extensionsType(), ResourceLoader.getResourceAsStream(path)));
    }

    public static Texture createTex(InputStream in) throws IOException {
        return createTex(TextureType.PNG, in);
    }

    public static Texture createTex(TextureType type, InputStream in) throws IOException {
        return new SlickTextureWrapper(TextureLoader.getTexture(type.extensionsType(), in));
    }

    public enum TextureType {
        PNG("PNG"),
        JPEG("JPEG"),
        GIF("GIF"),
        TIFF("TIFF"),
        RAW("RAW"),
        BITMAP("BMP");
        private final String extensionsType;

        TextureType(String type) {
            this.extensionsType = type;
        }

        public @NotNull String extensionsType() {
            return extensionsType;
        }

        @Nullable
        public static TextureType extensionsType(@NotNull String type) {
            for (TextureType value : values()) {
                if (value.extensionsType().equals(type)) return value;
            }
            return null;
        }

    }
}
