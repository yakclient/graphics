import net.yakclient.graphics.util.YakFontFactory;
import net.yakclient.graphics.util.YakTextureFactory;

module yakclient.graphics.util {
    exports net.yakclient.graphics.util;
//    exports net.yakclient.graphics.util.func;

    requires java.desktop;
    requires kotlin.stdlib;

    uses YakTextureFactory.TextureProvider;
    uses YakFontFactory.FontProvider;
}