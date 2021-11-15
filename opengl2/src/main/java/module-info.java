import net.yakclient.graphics.api.event.*;
import net.yakclient.graphics.opengl2.event.*;

module yakclient.graphics.open2gl {
    requires kotlin.stdlib;
    requires yakclient.graphics.api;
    requires yakclient.graphics.util;
    requires lwjgl;
    requires java.base;
    requires io.github.emilyydev.asp;

    provides MouseMoveDispatcher with GL2MouseMoveDispatcher;
    provides MouseButtonEventDispatcher with GL2MouseButtonEventDispatcher;
    provides KeyboardActionDispatcher with GL2KeyboardActionDispatcher;

    exports net.yakclient.graphics.opengl2.render;
}