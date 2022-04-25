import net.yakclient.graphics.lwjgl.legacy.util.YakGL2TextureProvider;
import net.yakclient.graphics.util.YakTextureFactory;

module yakclient.graphics.lwjgl.legacy.util {
    requires kotlin.stdlib;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires lwjgl.util;
    requires java.desktop;

    uses YakTextureFactory.TextureProvider;
    provides YakTextureFactory.TextureProvider with YakGL2TextureProvider;
}