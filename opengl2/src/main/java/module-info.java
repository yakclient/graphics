module yakclient.graphics.opengl2 {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;

    exports net.yakclient.graphics.opengl.render;
}