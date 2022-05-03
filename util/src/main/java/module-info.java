import net.yakclient.graphics.util.ScreenAccess;
import net.yakclient.graphics.util.YakFontFactory;
import net.yakclient.graphics.util.YakTextureFactory;
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider;
import net.yakclient.graphics.util.impl.YakFontProviderImpl;

module yakclient.graphics.util {
    requires java.desktop;
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires kotlinx.coroutines.core.jvm;
    requires java.logging;

    exports net.yakclient.graphics.util;
    exports net.yakclient.graphics.util.buffer;
    exports net.yakclient.graphics.util.unit;

    uses YakTextureFactory.TextureProvider;
    uses YakFontFactory.FontProvider;
    uses SafeFloatBufferProvider;
    uses ScreenAccess;

    provides YakFontFactory.FontProvider with YakFontProviderImpl;
}