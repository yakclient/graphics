import net.yakclient.graphics.lwjgl.util.GL3ScreenAccess;
import net.yakclient.graphics.lwjgl.util.YakGL3TextureProvider;
import net.yakclient.graphics.lwjgl.util.buffer.OpenGL3SafeFloatBufferProvider;
import net.yakclient.graphics.util.ScreenAccess;
import net.yakclient.graphics.util.YakTextureFactory;
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider;

module yakclient.graphics.lwjgl.util {
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires org.lwjgl.opengl;
    requires java.desktop;
    requires org.lwjgl.glfw;
    requires yakclient.graphics.util;
    requires org.lwjgl;

    exports net.yakclient.graphics.lwjgl.util to yakclient.graphics.lwjgl;

    provides SafeFloatBufferProvider with OpenGL3SafeFloatBufferProvider;
    provides YakTextureFactory.TextureProvider with YakGL3TextureProvider;
    provides ScreenAccess with GL3ScreenAccess;
}