import net.yakclient.graphics.util.YakFontFactory;
import net.yakclient.graphics.util.YakTextureFactory;
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider;
import net.yakclient.graphics.util.impl.YakFontProviderImpl;

module yakclient.graphics.util {
    requires java.desktop;
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires kotlinx.coroutines.core.jvm;

    exports net.yakclient.graphics.util;
    exports net.yakclient.graphics.util.buffer;

    uses YakTextureFactory.TextureProvider;
    uses YakFontFactory.FontProvider;
    uses SafeFloatBufferProvider;

    provides YakFontFactory.FontProvider with YakFontProviderImpl;
}