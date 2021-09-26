module yak.opengl.components {
    exports net.yakclient.graphics.components;

    requires yakclient.graphics.util;
    requires yakclient.graphics.api;

    requires org.jetbrains.annotations;
    requires lwjgl;
    requires lwjgl.util;
    requires slick.util;
    requires kotlin.stdlib;
}