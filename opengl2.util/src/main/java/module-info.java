import net.yakclient.graphics.opengl2.util.YakGL2FontProvider;
import net.yakclient.graphics.opengl2.util.YakGL2TextureProvider;
import net.yakclient.graphics.util.YakFontFactory;
import net.yakclient.graphics.util.YakTextureFactory;

module yakclient.graphics.opengl2.util {
    requires kotlin.stdlib;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires lwjgl.util;
//    requires io.github.emilyydev.asp;
    requires slick2d.core;
    requires java.desktop;

    uses YakFontFactory.FontProvider;
    uses YakTextureFactory.TextureProvider;
    provides YakFontFactory.FontProvider with YakGL2FontProvider;
    provides YakTextureFactory.TextureProvider with YakGL2TextureProvider;
}