import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.graphics.api.event.*;

module yakclient.graphics.api {
    requires java.base;
    exports net.yakclient.graphics.api;
    exports net.yakclient.graphics.api.state;
    exports net.yakclient.graphics.api.render;
    exports net.yakclient.graphics.api.event;

    requires yakclient.graphics.util;

//    requires lwjgl;
//    requires lwjgl.util;
//    requires slick.util;
    requires kotlin.stdlib;
    requires kotlin.reflect;

    uses EventSubscriber;

    uses DeferredComponentProvider;
//    uses MouseMoveSubscriber;
//    uses MouseButtonEventSubscriber;
//    uses KeyboardActionSubscriber;
}