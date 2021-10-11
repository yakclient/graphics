module yakclient.graphics.components {
    exports net.yakclient.graphics.components;

    requires yakclient.graphics.util;
    requires yakclient.graphics.api;

    requires yakclient.graphics.open2gl;
    requires kotlin.stdlib;

    uses net.yakclient.graphics.components.Box;
    uses net.yakclient.graphics.components.Text;
}