import net.yakclient.graphics.util.FontFactory;
import net.yakclient.graphics.util.impl.YakFontProviderImpl;

module yakclient.graphics.util {
    requires java.desktop;
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires kotlinx.coroutines.core.jvm;
    requires java.logging;
    requires yakclient.common.util;

    exports net.yakclient.graphics.util;
    exports net.yakclient.graphics.util.buffer;
    exports net.yakclient.graphics.util.unit;

//    uses YakTextureFactory.TextureProvider;
//    uses YakFontFactory.FontProvider;
//    uses SafeFloatBufferProvider;
//    uses ScreenAccess;

    provides FontFactory.FontProvider with YakFontProviderImpl;
}