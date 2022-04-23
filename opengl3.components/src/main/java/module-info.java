import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.graphics.opengl3.components.OpenGL3ComponentProvider;

module yakclient.graphics.opengl3.components {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.components;
    requires yakclient.graphics.opengl3;
    requires org.lwjgl.glfw;
    requires org.lwjgl;
    requires org.lwjgl.opengl;

    provides DeferredComponentProvider with OpenGL3ComponentProvider;
    exports net.yakclient.graphics.opengl3.components to yakclient.graphics.opengl3, yakclient.graphics.api;
}