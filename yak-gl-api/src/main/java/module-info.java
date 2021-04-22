module yak.gl.api {
    exports net.yakclient.opengl.api.gui;
    exports net.yakclient.opengl.api.gui.state;
    exports net.yakclient.opengl.api.render;

    requires org.jetbrains.annotations;
    requires yak.gl.util;

    requires lwjgl;
    requires lwjgl.util;
    requires slick.util;
}