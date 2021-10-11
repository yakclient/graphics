module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires java.base;

    exports net.yakclient.graphics.opengl2.render;
}