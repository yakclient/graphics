import net.yakclient.graphics.opengl2.util.YakGL2TextureProvider;
import net.yakclient.graphics.util.YakTextureFactory;

module yakclient.graphics.opengl2.util {
    requires kotlin.stdlib;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires lwjgl.util;
    requires java.desktop;

    uses YakTextureFactory.TextureProvider;
    provides YakTextureFactory.TextureProvider with YakGL2TextureProvider;
}