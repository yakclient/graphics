import net.yakclient.graphics.opengl3.util.YakGL3TextureProvider;
import net.yakclient.graphics.opengl3.util.buffer.OpenGL3SafeFloatBufferProvider;
import net.yakclient.graphics.util.YakTextureFactory;
import net.yakclient.graphics.util.buffer.BufferProvider;
import net.yakclient.graphics.util.buffer.SafeFloatBufferProvider;

module yakclient.graphics.opengl3.util {
    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires org.lwjgl.opengl;
    requires java.desktop;
    requires org.lwjgl.glfw;
    requires yakclient.graphics.util;
    requires org.lwjgl;

    provides SafeFloatBufferProvider with OpenGL3SafeFloatBufferProvider;
    provides YakTextureFactory.TextureProvider with YakGL3TextureProvider;
}