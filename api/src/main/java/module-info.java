import net.yakclient.graphics.api.DeferredComponentProvider;
import net.yakclient.event.api.*;

module yakclient.graphics.api {
    requires java.base;
    exports net.yakclient.graphics.api;
    exports net.yakclient.graphics.api.state;
    exports net.yakclient.graphics.api.render;
    exports net.yakclient.graphics.api.event;

    requires transitive yakclient.event.api;
    requires transitive yakclient.graphics.util;
    requires kotlinx.coroutines.core.jvm;

    requires kotlin.stdlib;
    requires kotlin.reflect;
    requires java.logging;

    uses EventDispatcher;
    uses DeferredComponentProvider;
}