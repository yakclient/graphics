module yak.opengl.components {
    exports net.yakclient.graphics.components;

    requires yakclient.graphics.util;
    requires yakclient.graphics.api;
    requires yakclient.graphics.opengl2;
    requires lwjgl;
    requires kotlin.stdlib;
}