module yakclient.graphics.api {
    exports net.yakclient.graphics.api.gui;
    exports net.yakclient.graphics.api.gui.state;
    exports net.yakclient.graphics.api.render;

    requires yakclient.graphics.util;

    requires lwjgl;
    requires lwjgl.util;
    requires slick.util;
    requires kotlin.stdlib;
}