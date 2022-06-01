import net.yakclient.graphics.lwjgl.util.LwjglScreenAccess;
import net.yakclient.graphics.lwjgl.util.LwjglTextureProvider;
import net.yakclient.graphics.lwjgl.util.buffer.LwjglSafeFloatBufferProvider;
import net.yakclient.graphics.util.ScreenAccess;
import net.yakclient.graphics.util.TextureFactory;
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider;

module yakclient.graphics.lwjgl.util {
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires org.lwjgl.opengl;
    requires java.desktop;
    requires org.lwjgl.glfw;
    requires yakclient.graphics.util;
    requires org.lwjgl;

    exports net.yakclient.graphics.lwjgl.util;

    provides SafeFloatBufferProvider with LwjglSafeFloatBufferProvider;
    provides TextureFactory.TextureProvider with LwjglTextureProvider;
    provides ScreenAccess with LwjglScreenAccess;
}