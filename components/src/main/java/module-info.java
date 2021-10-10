module yakclient.graphics.components {
    exports net.yakclient.graphics.components;

    requires yakclient.graphics.util;
    requires yakclient.graphics.api;

    requires yakclient.graphics.open2gl;
    requires lwjgl;
    requires kotlin.stdlib;

    uses net.yakclient.graphics.components.Box;
}