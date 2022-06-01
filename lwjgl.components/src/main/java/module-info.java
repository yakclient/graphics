open module yakclient.graphics.lwjgl.components {
    requires kotlin.stdlib;
    requires transitive yakclient.graphics.api;
    requires yakclient.graphics.components;
    requires yakclient.graphics.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl;
    requires org.lwjgl.opengl;

    exports net.yakclient.graphics.lwjgl.components;
}