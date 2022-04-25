import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.graphics.lwjgl.components.OpenGL3ComponentProvider;

module yakclient.graphics.lwjgl.components {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.components;
    requires yakclient.graphics.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl;
    requires org.lwjgl.opengl;

    provides DeferredComponentProvider with OpenGL3ComponentProvider;
    exports net.yakclient.graphics.lwjgl.components to yakclient.graphics.lwjgl, yakclient.graphics.api;
}