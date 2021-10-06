module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;
//    requires lwjgl.platform;

    exports net.yakclient.graphics.opengl.render;
}