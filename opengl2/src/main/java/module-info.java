import net.yakclient.graphics.api.event.*;
import net.yakclient.graphics.opengl2.event.*;

module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires java.base;
    requires io.github.emilyydev.asp;

    provides MouseMoveSubscriber with GL2MouseMoveSubscriber;
    provides MouseButtonEventSubscriber with GL2MouseButtonEventSubscriber;
    provides KeyboardActionSubscriber with GL2KeyboardActionSubscriber;

    exports net.yakclient.graphics.opengl2.render;
}