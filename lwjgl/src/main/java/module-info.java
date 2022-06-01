module yakclient.graphics.lwjgl {
    requires kotlin.stdlib;
    requires transitive yakclient.graphics.api;
    requires org.lwjgl.opengl;
    requires org.lwjgl.glfw;
    requires yakclient.graphics.lwjgl.util;

    exports net.yakclient.graphics.lwjgl.render;
    exports net.yakclient.graphics.lwjgl;
}